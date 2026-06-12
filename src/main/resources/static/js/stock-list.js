const PRICE_POLL_MS = 5000;

// 현재 경로로 역할 판별 → 선생님이면 /t, 학생이면 /s 엔드포인트
const IS_TEACHER = location.pathname.startsWith("/schoolstock/t/");
const PRICE_URL  = IS_TEACHER ? "/schoolstock/t/stocks/prices"
    : "/schoolstock/s/stocks/prices";

document.addEventListener("DOMContentLoaded", function () {
    // 학생만 행 클릭 시 주문 화면으로 이동 (선생님은 주문 화면이 없음)
    if (!IS_TEACHER) {
        document.querySelectorAll(".stock-table tbody tr[data-stock-no]").forEach(function (row) {
            row.style.cursor = "pointer";
            row.addEventListener("click", function () {
                location.href = "/schoolstock/s/orders/" + row.dataset.stockNo;
            });
        });
    }
    startPricePolling();
});

async function refreshPrices() {
    const res = await fetch(PRICE_URL);
    if (!res.ok) return;
    const list = await res.json();

    list.forEach(function (s) {
        const row = document.querySelector(`.stock-table tbody tr[data-stock-no='${s.stockNo}']`);
        if (!row) return;

        const prev = s.nowPoint - s.priceChange;
        setText(row.querySelector(".current-price"), s.nowPoint + "P");
        setText(row.querySelector(".prev-price"), prev + "P");

        const changeTd = row.querySelector(".price-change");
        changeTd.textContent = (s.priceChange > 0 ? "+" : "") + s.priceChange + "P";
        setUpDown(changeTd, s.priceChange);

        const rateTd = row.querySelector(".change-rate");
        rateTd.textContent = s.changeRate === 0
            ? "0%"
            : (s.changeRate > 0 ? "+" : "") + s.changeRate.toFixed(2) + "%";
        setUpDown(rateTd, s.changeRate);
    });
}

function setText(el, text) { if (el) el.textContent = text; }
function setUpDown(el, value) {
    el.classList.remove("up", "down", "flat");
    el.classList.add(value > 0 ? "up" : (value < 0 ? "down" : "flat"));
}

let priceTimer = null;

function startPricePolling() {
    refreshPrices();
    priceTimer = setInterval(refreshPrices, PRICE_POLL_MS);
}
function stopPricePolling() {
    clearInterval(priceTimer);
    priceTimer = null;
}

document.addEventListener("visibilitychange", function () {
    if (document.hidden) {
        stopPricePolling();
    } else if (priceTimer === null) {
        startPricePolling();
    }
});