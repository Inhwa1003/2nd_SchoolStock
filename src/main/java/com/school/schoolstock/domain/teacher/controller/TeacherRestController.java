package com.school.schoolstock.domain.teacher.controller;

import com.school.schoolstock.domain.teacher.dto.request.UpdateStudentCouponUsedRequest;
import com.school.schoolstock.domain.coupon.dto.request.CouponDeleteRequest;
import com.school.schoolstock.domain.coupon.dto.request.CouponUpdateRequest;
import com.school.schoolstock.domain.coupon.vo.Coupons;
import com.school.schoolstock.domain.student.dto.response.MyAssetResponse;
import com.school.schoolstock.domain.student.service.StudentService;
import com.school.schoolstock.domain.teacher.dto.request.PointGrantRequest;
import com.school.schoolstock.domain.teacher.service.CouponUseResult;
import com.school.schoolstock.domain.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schoolstock/t")
public class TeacherRestController {
    private final TeacherService teacherService;
    private final StudentService studentService;

    @GetMapping("/me/teachers/my-students/{studentNumber}/assets/stocks")
    public MyAssetResponse getMyStudentAssets(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int studentNumber
    ) {

        return  studentService.getMyAsset(teacherService.getStudentIdInClass(userDetails.getUsername(), studentNumber));
    }

    @PostMapping("/me/teachers/my-students/{studentNumber}/points")
    public ResponseEntity<Void> grantPoints(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable int studentNumber,
                                            @RequestBody PointGrantRequest request) {

        String studentId = teacherService.getStudentIdInClass(userDetails.getUsername(), studentNumber);
        if (studentId == null) return ResponseEntity.status(403).build();
        teacherService.givePoint(studentId, request.getPoints(), request.getContent());
        return ResponseEntity.ok().build();
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

    // 학생 보유 쿠폰 상태를 '사용'으로 변경
    @PatchMapping("/me/teachers/my-students/{studentNumber}/coupons")
    public ResponseEntity<Map<String, Object>> updateStudentCouponUsed(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int studentNumber,
            @RequestBody UpdateStudentCouponUsedRequest request
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(errorResponse(401, "UNAUTHORIZED", "로그인이 필요합니다."));
        }

        CouponUseResult result = teacherService.updateStudentCouponUsed(
                userDetails.getUsername(),
                studentNumber,
                request.getCouponPurchaseNo()
        );

        TeacherResponseCode responseCode = TeacherResponseCode.from(result);

        if (responseCode.getHttpStatus().isError()) {
            return ResponseEntity.status(responseCode.getHttpStatus())
                    .body(errorResponse(
                            responseCode.getHttpStatus().value(),
                            responseCode.getCode(),
                            responseCode.getMessage()
                    ));
        }

        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(successResponse(
                        responseCode.getHttpStatus().value(),
                        null,
                        responseCode.getMessage()
                ));
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
