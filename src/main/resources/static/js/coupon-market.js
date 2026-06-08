/**
 * 구매 버튼에서 쿠폰 정보를 꺼내서 buyCoupon 함수로 전달
 * @param button 구매 버튼
 */
function buyCouponFromButton(button) {
    const couponNo = button.dataset.couponNo;
    const couponName = button.dataset.couponName;
    const couponPoint = button.dataset.couponPoint;

    buyCoupon(couponNo, couponName, couponPoint);
}

/**
 * 쿠폰 구매 확인 및 요청 처리 함수
 * @param couponNo 쿠폰 번호
 * @param couponName 쿠폰 이름
 * @param couponPrice 쿠폰 가격
 */
async function buyCoupon(couponNo, couponName, couponPrice) {
    const confirmMessage = couponName + "을(를) " + couponPrice + "P에 구매하시겠습니까?";

    if (!window.confirm(confirmMessage)) {
        return;
    }

    const resultMessage = document.getElementById("resultMessage");
    const jsonResult = document.getElementById("jsonResult");

    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    resultMessage.innerText = "쿠폰 구매 요청 중...";
    jsonResult.innerText = "";

    try {
        const response = await fetch("/schoolstock/s/me/coupon-purchases", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({
                couponNo: Number(couponNo)
            })
        });

        const result = await response.json();

        console.log(result);
        jsonResult.innerText = JSON.stringify(result, null, 2);

        if (!response.ok) {
            resultMessage.innerText = "실패: " + result.message;
            alert(result.message);
            return;
        }

        resultMessage.innerText = "성공: " + result.message;
        alert(result.message);

    } catch (error) {
        console.error(error);
        resultMessage.innerText = "쿠폰 구매 요청 중 오류가 발생했습니다.";
        jsonResult.innerText = error;
        alert("쿠폰 구매 요청 중 오류가 발생했습니다.");
    }
}