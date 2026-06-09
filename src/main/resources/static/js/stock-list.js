document.addEventListener("DOMContentLoaded", function () {
    const rows = document.querySelectorAll(".stock-table tbody tr[data-stock-no]");

    rows.forEach(function (row) {
        row.style.cursor = "pointer";

        row.addEventListener("click", function () {
            const stockNo = row.dataset.stockNo;
            location.href = "/schoolstock/s/orders/" + stockNo;
        });
    });
});