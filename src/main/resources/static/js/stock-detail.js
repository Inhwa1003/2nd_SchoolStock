document.addEventListener("DOMContentLoaded", function () {
    const orderTypeSelect = document.getElementById("orderTypeSelect");
    const backBtn = document.getElementById("backBtn");
    // 매도 버튼
    const sellBtn = document.getElementById("sellBtn");
    // 매수 버튼
    const buyBtn = document.getElementById("buyBtn");
    // 내 요청 주문 '조회' 버튼
    const refreshBtn = document.getElementById("refreshBtn");

    // 처음 화면 진입 시 기본값은 매도
    loadOrderList("sell");
    // 처음 화면 진입 시 내 주문요청 조회
    loadMyOrderList();


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

    //내 주문 목록 불러오기
    if (refreshBtn) {
        refreshBtn.addEventListener("click", function () {
            loadMyOrderList();
        });
    }

    // 내 주문 취소
    const myOrderBody = document.getElementById("myOrderListBody");
    if (myOrderBody) {
        myOrderBody.addEventListener("click", function (e) {
            const btn = e.target.closest(".cancel-btn");  // 취소버튼(또는 그 안쪽) 클릭인지
            if (!btn) return;                              // 아니면 무시
            cancelMyOrder(btn.dataset.orderNo);            // data-order-no 값 넘김
        });
    }

    startPricePolling();
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
async function refreshPrice() {
    try {
        const res = await fetch(`/schoolstock/s/stocks/${stockNo}/price`);
        if (!res.ok) return;
        const p = await res.json();                 // {nowPoint, priceChange, changeRate}
        const prev = p.nowPoint - p.priceChange;

        const priceNow    = document.querySelector(".price-now");
        const priceBase   = document.querySelector(".price-base");
        const priceChange = document.querySelector(".price-change");

        if (priceNow)  priceNow.textContent  = p.nowPoint + "P";
        if (priceBase) priceBase.textContent = prev + "P";

        if (priceChange) {
            priceChange.classList.remove("up", "down");
            if (p.priceChange > 0) {
                priceChange.textContent = `+${p.priceChange}P(+${p.changeRate.toFixed(2)}%)`;
                priceChange.classList.add("up");
            } else if (p.priceChange < 0) {
                priceChange.textContent = `${p.priceChange}P(${p.changeRate.toFixed(2)}%)`;
                priceChange.classList.add("down");
            } else {
                priceChange.textContent = `0P(0.00%)`;
            }
        }
    } catch (e) {
        console.error("시세 폴링 실패:", e);
    }
}

/**
 * 내 요청 주문 조회 → #myOrderListBody 채우기
 */
async function loadMyOrderList() {
    const body = document.getElementById("myOrderListBody");

    try {
        const res = await fetch(`/schoolstock/s/me/students/${stockNo}/orders`);
        if (!res.ok) throw new Error("status " + res.status);

        const orders = await res.json();

        body.innerHTML = "";

        if (orders.length === 0) {
            body.innerHTML = `<tr><td colspan="5">내 주문이 없습니다.</td></tr>`;
            return;
        }

        orders.forEach(function (o) {
            const row = `
                <tr>
                    <td>${convertOrderContent(o.orderContent)}</td>
                    <td>${o.orderPoint}</td>
                    <td>${o.amount}</td>
                    <td>${o.orderDate ? o.orderDate.substring(0, 10) : ''}</td>
                    <td>
                        <button type="button" class="cancel-btn" data-order-no="${o.orderNo}">취소</button>
                    </td>
                </tr>
            `;
            body.insertAdjacentHTML("beforeend", row);
        });

    } catch (error) {
        console.error("내 주문 조회 실패:", error);
        body.innerHTML = `<tr><td colspan="5">내 주문 조회 중 오류가 발생했습니다.</td></tr>`;
    }
}

/**
 * 내 주문 취소 요청
 */
async function cancelMyOrder(orderNo) {
    if (!confirm("이 주문을 취소할까요?")) return;   // 사용자 확인

    const headers = { "Content-Type": "application/json" };
    const csrfTokenMeta = document.querySelector("meta[name='_csrf']");
    const csrfHeaderMeta = document.querySelector("meta[name='_csrf_header']");
    if (csrfTokenMeta && csrfHeaderMeta) {
        headers[csrfHeaderMeta.getAttribute("content")] = csrfTokenMeta.getAttribute("content");
    }

    try {
        const res = await fetch(`/schoolstock/s/me/students/${orderNo}/orders/cancel`, {
            method: "POST",
            headers: headers
        });
        const result = await res.json();
        alert(result.message);

        if (res.ok) {
            loadMyOrderList();   // 내 주문 목록 새로고침(취소 반영)
            loadOrderList(document.getElementById("orderTypeSelect").value); // 대기 주문판도 갱신
        }
    } catch (error) {
        console.error("주문 취소 실패:", error);
        alert("주문 취소 중 오류가 발생했습니다.");
    }
}

let priceTimer = null;

function startPricePolling() {
    refreshPrice();                                 // 즉시 1번 (다시 보일 때 바로 최신화)
    priceTimer = setInterval(refreshPrice, 5000);
}
function stopPricePolling() {
    clearInterval(priceTimer);
    priceTimer = null;
}

// 탭 보임/숨김에 따라 폴링 on/off
document.addEventListener("visibilitychange", function () {
    if (document.hidden) {
        stopPricePolling();                          // 숨겨지면 멈춤
    } else if (priceTimer === null) {
        startPricePolling();                         // 다시 보이면 재개
    }
});