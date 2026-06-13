document.addEventListener("DOMContentLoaded", function () {
    const addBtn = document.getElementById("addStockBtn");

    if (addBtn) {
        addBtn.addEventListener("click", addStock);
    }

    // 저장 버튼은 이벤트 위임 방식으로 처리
    document.addEventListener("click", function (e) {
        const saveBtn = e.target.closest(".st-save");

        if (!saveBtn) {
            return;
        }

        onUpdateStock(saveBtn);
    });

    // 숫자칸 음수·문자 차단
    document.querySelectorAll("#publicationBalance, #publicationPoint, .st-point, .st-balance")
        .forEach(function (input) {
            input.addEventListener("input", function () {
                this.value = this.value.replace(/[^0-9]/g, "");
            });
        });
});

function csrf() {
    return {
        token: document.querySelector("meta[name='_csrf']").getAttribute("content"),
        header: document.querySelector("meta[name='_csrf_header']").getAttribute("content")
    };
}

async function addStock() {
    const name = document.getElementById("stockName").value.trim();
    const content = document.getElementById("stockContent").value.trim();
    const balance = document.getElementById("publicationBalance").value;
    const point = document.getElementById("publicationPoint").value;

    if (!name) {
        alert("주식명을 입력해주세요.");
        return;
    }

    if (!content) {
        alert("주식 설명을 입력해주세요.");
        return;
    }

    if (Number(balance) <= 0) {
        alert("발행 수량은 0보다 커야 합니다.");
        return;
    }

    if (Number(point) <= 0) {
        alert("발행가는 0보다 커야 합니다.");
        return;
    }

    const c = csrf();

    try {
        const res = await fetch("/schoolstock/t/stocks", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                [c.header]: c.token
            },
            body: JSON.stringify({
                name: name,
                stockContent: content,
                publicationBalance: Number(balance),
                publicationPoint: Number(point)
            })
        });

        const result = await res.json();

        alert(result.message);

        if (res.ok) {
            location.reload();
        }

    } catch (error) {
        console.error(error);
        alert("주식 등록 요청 중 오류가 발생했습니다.");
    }
}

async function onUpdateStock(saveBtn) {
    const row = saveBtn.closest(".stock-row");

    if (!row) {
        alert("주식 행 정보를 찾을 수 없습니다.");
        return;
    }

    const stockNo = Number(row.dataset.stockNo);

    const nameInput = row.querySelector(".st-name");
    const contentInput = row.querySelector(".st-content");
    const balanceInput = row.querySelector(".st-balance");
    const pointInput = row.querySelector(".st-point");

    if (!nameInput || !contentInput || !balanceInput || !pointInput) {
        alert("수정에 필요한 입력칸을 찾을 수 없습니다.");
        return;
    }

    const name = nameInput.value.trim();
    const content = contentInput.value.trim();
    const balance = balanceInput.value;
    const point = pointInput.value;

    if (!stockNo) {
        alert("주식 번호를 찾을 수 없습니다.");
        return;
    }

    if (!name) {
        alert("주식명을 입력해주세요.");
        return;
    }

    if (!content) {
        alert("주식 설명을 입력해주세요.");
        return;
    }

    if (Number(balance) < 0) {
        alert("발행 수량은 0 이상이어야 합니다.");
        return;
    }

    if (Number(point) <= 0) {
        alert("발행가는 0보다 커야 합니다.");
        return;
    }

    const c = csrf();

    try {
        const res = await fetch("/schoolstock/t/stocks/edit", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                [c.header]: c.token
            },
            body: JSON.stringify({
                stockNo: stockNo,
                name: name,
                stockContent: content,
                publicationBalance: Number(balance),
                publicationPoint: Number(point)
            })
        });

        const result = await res.json();

        alert(result.message);

        if (res.ok) {
            location.reload();
        }

    } catch (error) {
        console.error(error);
        alert("주식 수정 중 오류가 발생했습니다.");
    }
}