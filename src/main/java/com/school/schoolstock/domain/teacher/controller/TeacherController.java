package com.school.schoolstock.domain.teacher.controller;

import com.school.schoolstock.domain.student.service.StudentService;
import com.school.schoolstock.domain.coupon.dto.CouponDeleteRequest;
import com.school.schoolstock.domain.coupon.dto.CouponUpdateRequest;
import com.school.schoolstock.domain.coupon.service.CouponService;
import com.school.schoolstock.domain.coupon.vo.Coupons;
import com.school.schoolstock.domain.teacher.dto.StudentListResponse;
import com.school.schoolstock.domain.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/schoolstock/t")
public class TeacherController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final CouponService couponService;

    // 선생님이 맡은 반 학생 목록 조회 화면
    @GetMapping("/me/teachers/my-students")
    public String getMyStudents(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        model.addAttribute("studentList", teacherService.getMyStudentsList(userDetails.getUsername()));

        return "myStudentList";
    }

    @GetMapping("/me/teachers/my-students/{studentNumber}/assets")
    public String getMyStudentAssets(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int studentNumber,
            Model model) {

        String studentId = teacherService.getStudentIdInClass(userDetails.getUsername(), studentNumber);

        model.addAttribute("targetStudentNumber", studentNumber);
        model.addAttribute("assetsRefreshUrl",
                "/schoolstock/t/me/teachers/my-students/" + studentNumber + "/assets/stocks");
        model.addAttribute("myAssets", studentService.getMyAsset(studentId));
        model.addAttribute("assetTitle",
                studentService.getStudentInfo(studentId).getName() + "의 자산");

        return "myAssets";
    }
    // 쿠폰 상점 조회 화면
    @GetMapping("/coupons")
    public String getCouponList(Model model){
        List<Coupons> couponList = couponService.getCouponList();
        model.addAttribute("couponList", couponList);

        return "teacherCouponMarket";
    }

    // 쿠폰 정보(쿠폰명, 쿠폰 포인트) 수정
    @PatchMapping("/coupons")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> setCoupon(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CouponUpdateRequest request
    ) {
        // 인증 확인
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(errorResponse(401, "UNAUTHORIZED", "로그인이 필요합니다."));
        }

        Coupons coupon = new Coupons();
        coupon.setCouponNo(request.getCouponNo());
        coupon.setName(request.getName());
        coupon.setCouponPoint(request.getCouponPoint());

        String message = teacherService.setCoupon(coupon);

        // 성공
        if (message.equals("쿠폰 정보가 정상적으로 수정되었습니다.")) {
            return ResponseEntity.ok(
                    successResponse(200, null, message)
            );
        }

        // 수정할 쿠폰 없음
        if (message.equals("수정할 쿠폰을 찾을 수 없습니다.")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorResponse(404, "COUPON_NOT_FOUND", message));
        }

        // 쿠폰명 공백, 포인트 0 이하 등 요청값 문제
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse(400, "INVALID_COUPON_REQUEST", message));
    }

    // 쿠폰 상점에 있는 쿠폰 삭제
    @DeleteMapping("/coupons")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteCoupon(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CouponDeleteRequest request
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(errorResponse(401, "UNAUTHORIZED", "로그인이 필요합니다."));
        }

        String message = teacherService.deleteCoupon(request.getCouponNo());

        // 성공
        if (message.equals("쿠폰이 정상적으로 삭제되었습니다.")) {
            return ResponseEntity.ok(
                    successResponse(200, null, message)
            );
        }

        // 삭제할 쿠폰 없음
        if (message.equals("삭제할 쿠폰을 찾을 수 없습니다.")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorResponse(404, "COUPON_NOT_FOUND", message));
        }

        // 그 외 요청 문제
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse(400, "INVALID_COUPON_REQUEST", message));
    }

    private Map<String, Object> successResponse(int code, Object data, String message) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("code", code);
        result.put("data", data);
        result.put("message", message);
        return result;
    }

    private Map<String, Object> errorResponse(int status, String code, String message) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", status);
        result.put("code", code);
        result.put("message", message);
        return result;
    }


}