document.addEventListener("DOMContentLoaded", function () {

    const studentItems = document.querySelectorAll(".student-item");

    studentItems.forEach(function (item) {
        item.addEventListener("click", function () {
            const studentNumber = item.dataset.studentNumber;

            location.href = `/schoolstock/t/me/teachers/my-students/${studentNumber}/assets`;
        });
    });
});