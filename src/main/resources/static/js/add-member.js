window.onload = function () {
    const form = document.querySelector("form");
    const loginIdInput  = document.querySelector("input[name='loginId']");      // studentId → loginId
    const password      = document.querySelector("#password");
    const passwordCheck = document.querySelector("#passwordCheck");
    const nameInput     = document.querySelector("input[name='name']");
    const classInput    = document.querySelector("input[name='className']");
    const numberInput   = document.querySelector("input[name='studentNumber']"); // classNumber → studentNumber
    const gradeSelect   = document.querySelector("select[name='grade']");

    const pwMsg      = document.querySelector("#pwd-msg");        // 비번 길이 메시지
    const pwdCheckMsg = document.querySelector("#pwd-check-msg"); // 비번 일치 메시지

    // 비밀번호 길이 (입력할 때마다 -> 메시지 있을 때만 보임)
    password.addEventListener("input", function () {
        if (password.value.length === 0) {
            pwMsg.textContent = "";
        } else if (password.value.length < 8) {
            pwMsg.textContent = "비밀번호는 8자리 이상이어야 합니다.";
            pwMsg.className = "message error";
        } else {
            pwMsg.textContent = "사용 가능한 비밀번호입니다.";
            pwMsg.className = "message success";
        }
    });

    // 비밀번호 일치 (확인란/비번 둘 다 입력 때)
    function checkMatch() {
        if (passwordCheck.value.length === 0) { pwdCheckMsg.textContent = ""; return; }
        if (password.value === passwordCheck.value) {
            pwdCheckMsg.textContent = "비밀번호가 일치합니다.";
            pwdCheckMsg.className = "message success";
        } else {
            pwdCheckMsg.textContent = "비밀번호가 일치하지 않습니다.";
            pwdCheckMsg.className = "message error";
        }
    }
    passwordCheck.addEventListener("input", checkMatch);
    password.addEventListener("input", checkMatch);

    // 제출 전 최종 검증
    form.addEventListener("submit", function (event) {
        if (loginIdInput.value.trim() === "") {
            alert("아이디를 입력해주세요."); loginIdInput.focus(); event.preventDefault(); return;
        }
        if (!isIdChecked) {
            alert("아이디 중복확인을 해주세요.");
            checkIdBtn.focus();
            event.preventDefault();
            return;
        }
        if (password.value.length < 8) {
            alert("비밀번호는 8자리 이상이어야 합니다."); password.focus(); event.preventDefault(); return;
        }
        if (password.value !== passwordCheck.value) {
            alert("비밀번호가 일치하지 않습니다."); passwordCheck.focus(); event.preventDefault(); return;
        }
        if (nameInput.value.trim() === "") {
            alert("이름을 입력해주세요."); nameInput.focus(); event.preventDefault(); return;
        }
        if (!gradeSelect.value) {   // disabled 안내옵션이라 안 고르면 ""
            alert("학년을 선택해주세요."); gradeSelect.focus(); event.preventDefault(); return;
        }
        if (classInput.value.trim() === "") {
            alert("반을 입력해주세요."); classInput.focus(); event.preventDefault(); return;
        }
        if (numberInput.value.trim() === "") {
            alert("번호를 입력해주세요."); numberInput.focus(); event.preventDefault(); return;
        }
    });

    const checkIdBtn = document.querySelector("#checkIdBtn");
    const idMsg = document.querySelector("#id-check-msg");
    let isIdChecked = false;                       // 중복확인 통과 여부

    checkIdBtn.addEventListener("click", async function () {
        const loginId = loginIdInput.value.trim();
        if (loginId === "") {
            idMsg.textContent = "아이디를 입력하세요.";
            idMsg.className = "message error";
            return;
        }
        try {
            const res = await fetch(`/schoolstock/a/check-id?loginId=${encodeURIComponent(loginId)}`);
            const data = await res.json();         // {exists: true/false}
            if (data.exists) {
                idMsg.textContent = "이미 사용 중인 아이디입니다.";
                idMsg.className = "message error";
                isIdChecked = false;
            } else {
                idMsg.textContent = "사용 가능한 아이디입니다.";
                idMsg.className = "message success";
                isIdChecked = true;
            }
        } catch (e) {
            idMsg.textContent = "중복확인 중 오류가 발생했습니다.";
            idMsg.className = "message error";
        }
    });

// 아이디를 바꾸면 다시 확인하도록 초기화
    loginIdInput.addEventListener("input", function () {
        isIdChecked = false;
        idMsg.textContent = "";
    });
};