document.addEventListener("DOMContentLoaded", function () {
    const orderTypeSelect = document.getElementById("orderTypeSelect");
    const orderListBody = document.getElementById("orderListBody");
    const backBtn = document.getElementById("backBtn");

    // 처음 화면 진입 시 기본값은 매도
    loadOrderList("sell");

    // 매수/매도 선택 변경 시 주문 목록 다시 조회
    orderTypeSelect.addEventListener("change", function () {
        const orderType = orderTypeSelect.value;
        loadOrderList(orderType);
    });

    // 뒤로가기 버튼
    backBtn.addEventListener("click", function () {
        history.back();
    });

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
                    <td>${order.amount}</td>
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
    const nowPrice = Number(priceNow.textContent.replace("P", ""));

    if (isNaN(prevPrice) || isNaN(nowPrice)) {
        return;
    }

    const diff = nowPrice - prevPrice;

    if (diff > 0) {
        priceChange.textContent = `▲ ${diff}P`;
        priceChange.classList.add("up");
    } else if (diff < 0) {
        priceChange.textContent = `▼ ${Math.abs(diff)}P`;
        priceChange.classList.add("down");
    } else {
        priceChange.textContent = "-";
    }
}