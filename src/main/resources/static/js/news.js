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

            console.log("선택한 뉴스:", newsText.textContent);
        });
    });
});