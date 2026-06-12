package com.school.schoolstock.domain.teacher.controller;

import com.school.schoolstock.domain.coupon.dto.CouponDeleteRequest;
import com.school.schoolstock.domain.coupon.dto.CouponUpdateRequest;
import com.school.schoolstock.domain.student.dto.response.MyAssetResponse;
import com.school.schoolstock.domain.student.service.StudentService;
import com.school.schoolstock.domain.teacher.dto.request.PointGrantRequest;
import com.school.schoolstock.domain.teacher.service.TeacherService;
import com.school.schoolstock.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schoolstock/t")
public class TeacherRestController {
    private final TeacherService teacherService;
    private final StudentService studentService;

    @GetMapping("/me/teachers/my-students/{studentNumber}/assets/stocks")
    public MyAssetResponse getMyStudentAssets(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int studentNumber) {

        return  studentService.getMyAsset(teacherService.getStudentIdInClass(userDetails.getUsername(), studentNumber));
    }

    @PostMapping("/me/teachers/my-students/{studentNumber}/points")
    public ApiResponse grantPoints(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable int studentNumber,
                                            @RequestBody PointGrantRequest request) {

        teacherService.givePoint(userDetails.getUsername(), studentNumber, request);
        return ApiResponse.of(200, "포인트가 지급되었습니다.", null);
    }
    // 쿠폰 정보(쿠폰명, 쿠폰 포인트) 수정
    @PostMapping("/coupons")
    @ResponseBody
    public ApiResponse setCoupon(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CouponUpdateRequest request) {
        teacherService.setCoupon(request);
        return ApiResponse.of(200, "쿠폰 정보가 수정되었습니다.", null);
    }

    // 쿠폰 상점에 있는 쿠폰 삭제
    @DeleteMapping("/coupons")
    @ResponseBody
    public ApiResponse deleteCoupon(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CouponDeleteRequest request) {

        teacherService.deleteCoupon(request.getCouponNo());
        return ApiResponse.of(200, "쿠폰이 정상적으로 삭제되었습니다.", null);
    }
}
