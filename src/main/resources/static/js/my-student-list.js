document.addEventListener("DOMContentLoaded", function () {
    console.log("my-student-list.js 로딩됨");

    const studentItems = document.querySelectorAll(".student-item");

    studentItems.forEach(function (item) {
        item.addEventListener("click", function () {
            const studentNumber = item.dataset.studentNumber;

            console.log("클릭한 학생 번호:", studentNumber);

            // 아직 상세 페이지가 없으니까 일단 로그만 찍음.
            // 나중에 상세 페이지 만들면 아래처럼 이동시키면 됨.
            // location.href = "/schoolstock/t/me/teachers/my-students/" + studentNumber;
        });
    });
});