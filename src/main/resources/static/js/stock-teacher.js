async function addStock() {
    const name    = document.getElementById("stockName").value;
    const content = document.getElementById("stockContent").value;
    const balance = document.getElementById("publicationBalance").value;
    const point   = document.getElementById("publicationPoint").value;

    if (!name.trim()) { alert("주식명을 입력해주세요."); return; }
    if (Number(balance) <= 0) { alert("발행 수량은 0보다 커야 합니다."); return; }
    if (Number(point)   <= 0) { alert("발행가는 0보다 커야 합니다."); return; }

    const csrfToken  = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    try {
        const response = await fetch("/schoolstock/t/stocks", {
            method: "POST",
            headers: { "Content-Type": "application/json", [csrfHeader]: csrfToken },
            body: JSON.stringify({
                name: name,
                stockContent: content,
                publicationBalance: Number(balance),
                publicationPoint: Number(point)
            })
        });

        const result = await response.json();
        alert(result.message);
        if (response.ok) location.reload();   // 등록 성공 -> 목록 새로고침
    } catch (error) {
        alert("주식 등록 요청 중 오류가 발생했습니다.");
    }
}