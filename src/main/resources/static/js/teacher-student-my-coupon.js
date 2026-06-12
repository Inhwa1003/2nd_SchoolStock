document.addEventListener("DOMContentLoaded", function () {
    const backBtn = document.getElementById("backBtn");
    const couponContainer = document.getElementById("couponContainer");

    // 뒤로가기 버튼
    if (backBtn) {
        backBtn.addEventListener("click", function () {
            history.back();
        });
    }

    if (!couponContainer) {
        return;
    }

    const studentNumber = couponContainer.dataset.studentNumber;

    const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute("content");

    const buttons = document.querySelectorAll(".use-coupon-btn");

    buttons.forEach(function (button) {
        button.addEventListener("click", async function () {
            const isConfirmed = confirm("해당 쿠폰을 삭제하시겠습니까?");
            if (!isConfirmed) {
                return;
            }

            const couponPurchaseNo = button.dataset.couponPurchaseNo;

            try {
                const headers = {
                    "Content-Type": "application/json"
                };

                if (csrfToken && csrfHeader) {
                    headers[csrfHeader] = csrfToken;
                }

                const response = await fetch(`/schoolstock/t/me/teachers/my-students/${studentNumber}/coupons`, {
                    method: "PATCH",
                    headers: headers,
                    body: JSON.stringify({
                        couponPurchaseNo: Number(couponPurchaseNo)
                    })
                });

                const result = await response.json();

                if (!response.ok) {
                    alert(result.message || "쿠폰 삭제에 실패했습니다.");
                    return;
                }

                alert(result.message || "쿠폰이 삭제되었습니다.");

                const couponPanel = button.closest(".cp-panel");
                if (couponPanel) {
                    couponPanel.remove();
                }

                const remainingCoupons = document.querySelectorAll(".cp-panel");
                if (remainingCoupons.length === 0) {
                    couponContainer.innerHTML = `
                        <div class="text-center w-100">
                            보유 쿠폰이 없습니다.
                        </div>
                    `;
                }

            } catch {
                alert("쿠폰 삭제 중 오류가 발생했습니다.");
            }
        });
    });
});