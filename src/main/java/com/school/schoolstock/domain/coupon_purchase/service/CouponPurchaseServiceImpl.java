package com.school.schoolstock.domain.coupon_purchase.service;

import com.school.schoolstock.domain.coupon.repository.CouponRepository;
import com.school.schoolstock.domain.coupon.vo.Coupons;
import com.school.schoolstock.domain.coupon_purchase.repository.CouponPurchaseRepository;
import com.school.schoolstock.domain.coupon_purchase.vo.CouponPurchase;
import com.school.schoolstock.domain.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class CouponPurchaseServiceImpl implements CouponPurchaseService {
    private final CouponPurchaseRepository couponPurchaseRepository;
    private final CouponRepository couponRepository;
    private final StudentRepository studentRepository;

    @Transactional
    @Override
    public boolean buyCoupon(String studentId, int couponNo) {
        //학생 보유쿠폰 수량 초과시
        if(studentRepository.getMyCouponAmount(studentId) >= 3)
            return false;

        //1.쿠폰 정보 조회
        Coupons coupon = couponRepository.getCoupon(couponNo);
        //없는 쿠폰
        if(coupon == null)
            return false;

        //2.포인트 충분하면 차감 + 보유쿠폰 +1 (부족하면 0행 -> false)
        if(!studentRepository.setStudentAssets(studentId, coupon.getCouponPoint()))
            return false;

        // 3. 구매내역 객체 생성
        couponPurchaseRepository.setPurchaseRecord(CouponPurchase.builder()
                .studentId(studentId)
                .couponNo(couponNo)
                .purchasePoint(coupon.getCouponPoint())
                .name(coupon.getName())
                .purchaseState("NOT_USED").build());
        return true;
    }
}
