document.addEventListener("DOMContentLoaded", function () {
    const backBtn = document.getElementById("backBtn");
    if (backBtn) backBtn.addEventListener("click", () => history.back());

    const container = document.getElementById("couponContainer");
    const studentNumber = container?.dataset.studentNumber;
    const token  = document.querySelector('meta[name="_csrf"]')?.getAttribute("content");
    const header = document.querySelector('meta[name="_csrf_header"]')?.getAttribute("content");

    document.querySelectorAll(".use-coupon-btn").forEach(btn => {   // 선생만 존재
        btn.addEventListener("click", async () => {
            if (!confirm("이 쿠폰을 사용 처리할까요?")) return;
            const headers = { "Content-Type": "application/json" };
            if (token && header) headers[header] = token;
            try {
                const res = await fetch(`/schoolstock/t/me/teachers/my-students/${studentNumber}/coupons`, {
                    method: "POST",
                    headers,
                    body: JSON.stringify({ couponPurchaseNo: Number(btn.dataset.couponPurchaseNo) })
                });
                const result = await res.json();
                if (!res.ok) { alert(result.message); return; }
                alert(result.message);
                btn.closest(".cp-panel").remove();
            } catch { alert("사용 처리 중 오류가 발생했습니다."); }
        });
    });
});