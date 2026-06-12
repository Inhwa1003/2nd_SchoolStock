function buyCouponFromButton(button) {
    buyCoupon(button.dataset.couponNo, button.dataset.couponName, button.dataset.couponPoint);
}

async function buyCoupon(couponNo, couponName, couponPrice) {
    if (!window.confirm(couponName + "을(를) " + couponPrice + "P에 구매하시겠습니까?")) return;

    const csrfToken  = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    try {
        const response = await fetch("/schoolstock/s/me/coupon-purchases", {
            method: "POST",
            headers: { "Content-Type": "application/json", [csrfHeader]: csrfToken },
            body: JSON.stringify({ couponNo: Number(couponNo) })
        });

        const result = await response.json();
        alert(result.message);
        if (response.ok) location.reload();   // 성공 -> 새로고침 -> 사이드바 포인트도 갱신
    } catch (error) {
        alert("쿠폰 구매 요청 중 오류가 발생했습니다.");
    }
}

function enableCouponEdit(input) {
    input.removeAttribute("readonly");
    input.focus();

    const panel = input.closest(".cp-panel");
    const saveButton = panel.querySelector(".save-cp-btn");

    if (saveButton) {
        saveButton.style.display = "inline-block";
    }
}

async function updateCouponFromButton(button) {
    const panel = button.closest(".cp-panel");

    const couponNo = panel.dataset.couponNo;
    const nameInput = panel.querySelector(".coupon-name-input");
    const pointInput = panel.querySelector(".coupon-point-input");

    const name = nameInput.value;
    const couponPoint = pointInput.value;

    if (!name.trim()) {
        alert("쿠폰명을 입력해주세요.");
        nameInput.focus();
        return;
    }

    if (Number(couponPoint) <= 0) {
        alert("쿠폰 포인트는 0보다 커야 합니다.");
        pointInput.focus();
        return;
    }

    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    const requestBody = {
        couponNo: Number(couponNo),
        name: name,
        couponPoint: Number(couponPoint)
    };

    try {
        const response = await fetch("/schoolstock/t/coupons", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(requestBody)
        });

        const result = await response.json();

        alert(result.message);

        if (response.ok) {
            nameInput.setAttribute("readonly", true);
            pointInput.setAttribute("readonly", true);

            button.style.display = "none";
        }

    } catch {
        alert("쿠폰 수정 중 오류가 발생했습니다.");
    }
}

async function deleteCouponFromButton(button) {
    const couponNo = button.dataset.couponNo;

    if (!confirm("정말 이 쿠폰을 삭제하시겠습니까?")) {
        return;
    }

    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    const requestBody = {
        couponNo: Number(couponNo)
    };

    try {
        const response = await fetch("/schoolstock/t/coupons", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(requestBody)
        });

        const result = await response.json();

        alert(result.message);

        if (response.ok) {
            const panel = button.closest(".cp-panel");
            panel.remove();
        }

    } catch (error) {
        alert("쿠폰 삭제 중 오류가 발생했습니다.");
    }
}