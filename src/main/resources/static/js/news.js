document.addEventListener("DOMContentLoaded", function () {
    const newsGrid = document.getElementById("newsGrid");
    const newsCards = document.querySelectorAll(".news-card-item");

    if (!newsGrid) {
        return;
    }

    if (newsCards.length === 0) {
        return;
    }

    newsCards.forEach(function (card) {
        card.addEventListener("click", function () {
            const newsText = card.querySelector(".news-text-main");

            if (!newsText) {
                return;
            }

        });
    });
});


document.addEventListener("DOMContentLoaded", function () {
    const addBtn = document.getElementById("addNewsBtn");
    if (!addBtn) return;

    const csrfToken  = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    // 공통 요청
    async function sendNews(method, url, body) {
        try {
            const res = await fetch(url, {
                method: method,
                headers: { "Content-Type": "application/json", [csrfHeader]: csrfToken },
                body: JSON.stringify(body)
            });
            const result = await res.json();
            alert(result.message);
            if (res.ok) location.reload();
        } catch (err) {
            alert("요청 중 오류가 발생했습니다.");
        }
    }

    // 등록
    addBtn.addEventListener("click", function () {
        const content = document.getElementById("newNewsContent").value;
        sendNews("POST", "/schoolstock/t/news", { newsContent: content });
    });

    // 수정/삭제 = 이벤트 위임
    document.getElementById("newsAdminList").addEventListener("click", function (e) {
        const item = e.target.closest(".news-admin-item");
        if (!item) return;
        const newsNo = Number(item.dataset.newsNo);

        if (e.target.classList.contains("news-update-btn")) {
            const content = item.querySelector(".news-edit-content").value;
            sendNews("POST", "/schoolstock/t/news/edit", { newsNo: newsNo, newsContent: content });
        } else if (e.target.classList.contains("news-delete-btn")) {
            if (!confirm("이 뉴스를 삭제할까요?")) return;
            sendNews("DELETE", "/schoolstock/t/news", { newsNo: newsNo });
        }
    });
});