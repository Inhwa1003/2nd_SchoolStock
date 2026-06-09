document.addEventListener("DOMContentLoaded", function () {
    const orderTypeSelect = document.getElementById("orderTypeSelect");
    const backBtn = document.getElementById("backBtn");
    // 매도 버튼
    const sellBtn = document.getElementById("sellBtn");
    // 매수 버튼
    const buyBtn = document.getElementById("buyBtn");

    console.log("stock-detail.js 로딩됨");
    console.log("sellBtn:", sellBtn);

    // 처음 화면 진입 시 기본값은 매도
    loadOrderList("sell");

    // 매수/매도 선택 변경 시 주문 목록 다시 조회
    if (orderTypeSelect) {
        orderTypeSelect.addEventListener("change", function () {
            const orderType = orderTypeSelect.value;
            loadOrderList(orderType);
        });
    }

    // 뒤로가기 버튼
    if (backBtn) {
        backBtn.addEventListener("click", function () {
            history.back();
        });
    }

    // 매도 버튼 클릭 시 매도 주문 요청
    if (sellBtn) {
        sellBtn.addEventListener("click", function () {
            console.log("매도 버튼 클릭됨");
            requestSellOrder();
        });
    } else {
        console.error("sellBtn을 찾을 수 없습니다.");
    }

    // 매수 버튼 클릭 시 매수 주문 요청
    if (buyBtn) {
        buyBtn.addEventListener("click", function () {
            console.log("매수 버튼 클릭됨");
            requestBuyOrder();
        });
    } else {
        console.error("buyBtn을 찾을 수 없습니다.");
    }

    // 주가 변동 표시
    setPriceChange();
});

/**
 * 매수/매도 주문 목록 조회
 * @param orderType buy 또는 sell
 */
async function loadOrderList(orderType) {
    const orderListBody = document.getElementById("orderListBody");

    try {
        const response = await fetch(`/schoolstock/s/orders/${stockNo}/${orderType}`);
        const result = await response.json();

        orderListBody.innerHTML = "";

        if (result.code !== 200 || result.data === null || result.data.length === 0) {
            orderListBody.innerHTML = `
                <tr>
                    <td colspan="3">등록된 주문이 없습니다.</td>
                </tr>
            `;
            return;
        }

        result.data.forEach(function (order) {
            const row = `
                <tr>
                    <td>${convertOrderContent(order.orderContent)}</td>
                    <td>${order.orderPoint}</td>
                    <td>${order.orderAmount}</td>
                </tr>
            `;

            orderListBody.insertAdjacentHTML("beforeend", row);
        });

    } catch (error) {
        console.error("주문 목록 조회 실패:", error);

        orderListBody.innerHTML = `
            <tr>
                <td colspan="3">주문 목록 조회 중 오류가 발생했습니다.</td>
            </tr>
        `;
    }
}

/**
 * 매도 주문 요청
 */
async function requestSellOrder() {
    const sellPrice = document.getElementById("sellPrice");
    const sellAmount = document.getElementById("sellAmount");

    const orderPoint = Number(sellPrice.value);
    const orderAmount = Number(sellAmount.value);

    if (orderPoint <= 0 || orderAmount <= 0) {
        alert("가격과 수량을 올바르게 입력해주세요.");
        return;
    }

    const headers = {
        "Content-Type": "application/json"
    };

    const csrfTokenMeta = document.querySelector("meta[name='_csrf']");
    const csrfHeaderMeta = document.querySelector("meta[name='_csrf_header']");

    if (csrfTokenMeta && csrfHeaderMeta) {
        const csrfToken = csrfTokenMeta.getAttribute("content");
        const csrfHeader = csrfHeaderMeta.getAttribute("content");
        headers[csrfHeader] = csrfToken;
    }

    try {
        console.log("매도 요청 전송:", `/schoolstock/s/me/stocks/${stockNo}/sell`);

        const response = await fetch(`/schoolstock/s/me/stocks/${stockNo}/sell`, {
            method: "POST",
            headers: headers,
            body: JSON.stringify({
                orderPoint: orderPoint,
                orderAmount: orderAmount
            })
        });

        const result = await response.json();

        alert(result.message);

        if (response.ok) {
            document.getElementById("orderTypeSelect").value = "sell";
            loadOrderList("sell");
        }

    } catch (error) {
        console.error("매도 주문 요청 실패:", error);
        alert("매도 주문 요청 중 오류가 발생했습니다.");
    }
}

/**
 * 매수 주문 요청
 */
async function requestBuyOrder() {
    const buyPrice = document.getElementById("buyPrice");
    const buyAmount = document.getElementById("buyAmount");

    const orderPoint = Number(buyPrice.value);
    const orderAmount = Number(buyAmount.value);

    if (orderPoint <= 0 || orderAmount <= 0) {
        alert("가격과 수량을 올바르게 입력해주세요.");
        return;
    }

    const headers = {
        "Content-Type": "application/json"
    };

    const csrfTokenMeta = document.querySelector("meta[name='_csrf']");
    const csrfHeaderMeta = document.querySelector("meta[name='_csrf_header']");

    if (csrfTokenMeta && csrfHeaderMeta) {
        const csrfToken = csrfTokenMeta.getAttribute("content");
        const csrfHeader = csrfHeaderMeta.getAttribute("content");
        headers[csrfHeader] = csrfToken;
    }

    try {
        console.log("매수 요청 전송:", `/schoolstock/s/me/stocks/${stockNo}/buy`);

        const response = await fetch(`/schoolstock/s/me/stocks/${stockNo}/buy`, {
            method: "POST",
            headers: headers,
            body: JSON.stringify({
                orderPoint: orderPoint,
                orderAmount: orderAmount
            })
        });

        const result = await response.json();

        alert(result.message);

        if (response.ok) {
            document.getElementById("orderTypeSelect").value = "buy";
            loadOrderList("buy");
        }

    } catch (error) {
        console.error("매수 주문 요청 실패:", error);
        alert("매수 주문 요청 중 오류가 발생했습니다.");
    }
}

/**
 * BUY / SELL 한글 변환
 */
function convertOrderContent(orderContent) {
    if (orderContent === "BUY") {
        return "매수";
    }

    if (orderContent === "SELL") {
        return "매도";
    }

    return orderContent;
}

/**
 * 현재가와 이전가 비교해서 상승/하락 표시
 */
function setPriceChange() {
    const stockPrice = document.querySelector(".stock-price");
    const priceNow = document.querySelector(".price-now");
    const priceChange = document.querySelector(".price-change");

    if (!stockPrice || !priceNow || !priceChange) {
        return;
    }

    const prevPrice = Number(stockPrice.dataset.prevPrice);
    const nowPrice = Number(priceNow.textContent.replace("P", "").trim());

    if (isNaN(prevPrice) || isNaN(nowPrice)) {
        return;
    }

    const diff = nowPrice - prevPrice;
    const changeRate = prevPrice == 0 ? 0 : (diff * 100) / prevPrice;

    priceChange.classList.remove("up", "down");

    if (diff > 0) {
        priceChange.textContent = ` ${diff}P`;
        priceChange.classList.add("up");
    } else if (diff < 0) {
        priceChange.textContent = `▼ ${Math.abs(diff)}P`;
        priceChange.classList.add("down");
    } else {
        priceChange.textContent = `0P(0.00%)`;
    }
}