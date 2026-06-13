document.addEventListener("DOMContentLoaded", function () {
    // 등록
    const addBtn = document.getElementById("addStockBtn");
    if (addBtn) addBtn.addEventListener("click", addStock);

    // 수정 저장
    document.querySelectorAll(".st-save").forEach(b => b.addEventListener("click", onUpdateStock));

    // 숫자칸(등록폼 + 수정칸) 음수·문자 차단
    document.querySelectorAll("#publicationBalance, #publicationPoint, .st-point, .st-balance")
        .forEach(function (input) {
            input.addEventListener("input", function () {
                this.value = this.value.replace(/[^0-9]/g, "");
            });
        });
});

function csrf() {
    return {
        token:  document.querySelector("meta[name='_csrf']").getAttribute("content"),
        header: document.querySelector("meta[name='_csrf_header']").getAttribute("content")
    };
}

async function addStock() {
    const name    = document.getElementById("stockName").value;
    const content = document.getElementById("stockContent").value;
    const balance = document.getElementById("publicationBalance").value;
    const point   = document.getElementById("publicationPoint").value;

    if (!name.trim())         { alert("주식명을 입력해주세요."); return; }
    if (Number(balance) <= 0) { alert("발행 수량은 0보다 커야 합니다."); return; }
    if (Number(point) <= 0)   { alert("발행가는 0보다 커야 합니다."); return; }

    const c = csrf();
    try {
        const res = await fetch("/schoolstock/t/stocks", {
            method: "POST",
            headers: { "Content-Type": "application/json", [c.header]: c.token },
            body: JSON.stringify({
                name: name, stockContent: content,
                publicationBalance: Number(balance), publicationPoint: Number(point)
            })
        });
        const result = await res.json();
        alert(result.message);
        if (res.ok) location.reload();
    } catch { alert("주식 등록 요청 중 오류가 발생했습니다."); }
}

async function onUpdateStock(e) {
    const row = e.target.closest("tr");
    const stockNo = Number(row.dataset.stockNo);
    const name    = row.querySelector(".st-name").value;
    const content = row.querySelector(".st-content").value;
    const balance = row.querySelector(".st-balance").value;
    const point   = row.querySelector(".st-point").value;

    if (!name.trim())        { alert("주식명을 입력해주세요."); return; }
    if (Number(balance) < 0) { alert("발행 수량은 0 이상이어야 합니다."); return; }
    if (Number(point) <= 0)  { alert("발행가는 0보다 커야 합니다."); return; }

    const c = csrf();
    try {
        const res = await fetch("/schoolstock/t/stocks/edit", {
            method: "POST",
            headers: { "Content-Type": "application/json", [c.header]: c.token },
            body: JSON.stringify({
                stockNo: stockNo, name: name, stockContent: content,
                publicationBalance: Number(balance), publicationPoint: Number(point)
            })
        });
        const result = await res.json();
        alert(result.message);
        if (res.ok) location.reload();
    } catch { alert("주식 수정 중 오류가 발생했습니다."); }
}