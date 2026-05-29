package com.school.schoolstock.domain.coupon.repository;

import com.school.schoolstock.domain.coupon.vo.Coupons;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper

public interface CouponRepository {

    //쿠폰 상점에 등록된 쿠폰 조회
    List<Coupons> getCouponList();

}
