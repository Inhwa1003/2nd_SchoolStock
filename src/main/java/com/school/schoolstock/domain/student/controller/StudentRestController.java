package com.school.schoolstock.domain.student.controller;

import com.school.schoolstock.domain.student.dto.response.MyAssetResponse;
import com.school.schoolstock.domain.student.dto.response.MyOrderResponse;
import com.school.schoolstock.domain.student.dto.response.StudentInfoResponse;
import com.school.schoolstock.domain.student.service.StudentService;
import com.school.schoolstock.global.response.ApiResponse;
import com.school.schoolstock.global.security.SchoolUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schoolstock/s/me/students")
public class StudentRestController {
    private final StudentService studentService;

    @GetMapping("/assets/stocks")
    public MyAssetResponse refreshMyAssets(@AuthenticationPrincipal SchoolUserDetails userDetails) {
        return studentService.getMyAsset(userDetails.getUsername());
    }

    @GetMapping("/{stockNo}/orders")
    public List<MyOrderResponse> getMyOrder(@AuthenticationPrincipal SchoolUserDetails userDetails, @PathVariable int stockNo) {
        return studentService.getMyOrder(userDetails.getUsername(), stockNo);
    }

    @PostMapping("/{orderNo}/orders/cancel")
    public ApiResponse cancelMyOrder(@PathVariable int orderNo) {
        studentService.setMyOrderCancel(orderNo);
        return ApiResponse.of(200, "주문이 취소되었습니다.", null);
    }

    @GetMapping("/info")
    public StudentInfoResponse myInfo(@AuthenticationPrincipal SchoolUserDetails userDetails) {
        return studentService.getStudentInfo(userDetails.getUsername());
    }
}
