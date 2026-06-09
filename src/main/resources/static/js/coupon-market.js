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
        if (response.ok) location.reload();   // 성공 → 새로고침 → 사이드바 포인트도 갱신
    } catch (error) {
        console.error(error);
        alert("쿠폰 구매 요청 중 오류가 발생했습니다.");
    }
}