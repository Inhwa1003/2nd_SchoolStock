const PRICE_POLL_MS = 5000;

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".stock-table tbody tr[data-stock-no]").forEach(function (row) {
        row.style.cursor = "pointer";
        row.addEventListener("click", function () {
            location.href = "/schoolstock/s/orders/" + row.dataset.stockNo;
        });
    });

    startPricePolling();
});

async function refreshPrices() {
        const res = await fetch("/schoolstock/s/stocks/prices");
        if (!res.ok) return;
        const list = await res.json();

        list.forEach(function (s) {
            const row = document.querySelector(`.stock-table tbody tr[data-stock-no='${s.stockNo}']`);
            if (!row) return;

            const prev = s.nowPoint - s.priceChange;
            setText(row.querySelector(".current-price"), s.nowPoint + "P");
            setText(row.querySelector(".prev-price"), prev + "P");

            const changeTd = row.querySelector(".price-change");
            changeTd.textContent = (s.priceChange >= 0 ? "+" : "") + s.priceChange + "P";
            setUpDown(changeTd, s.priceChange);

            const rateTd = row.querySelector(".change-rate");
            rateTd.textContent = (s.changeRate >= 0 ? "+" : "") + s.changeRate.toFixed(2) + "%";
            setUpDown(rateTd, s.changeRate);
        });
}

function setText(el, text) { if (el) el.textContent = text; }
function setUpDown(el, value) {
    el.classList.remove("up", "down");
    el.classList.add(value >= 0 ? "up" : "down");
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