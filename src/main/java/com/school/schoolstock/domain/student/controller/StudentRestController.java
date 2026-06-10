package com.school.schoolstock.domain.student.controller;

import com.school.schoolstock.domain.student.dto.response.MyAssetResponse;
import com.school.schoolstock.domain.student.dto.response.MyOrderResponse;
import com.school.schoolstock.domain.student.dto.response.StudentInfoResponse;
import com.school.schoolstock.domain.student.service.StudentService;
import com.school.schoolstock.global.security.SchoolUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> cancelMyOrder(@PathVariable int orderNo) {
        boolean ok = studentService.setMyOrderCancel(orderNo);
        Map<String, Object> body = new HashMap<>();
        if (!ok) {
            body.put("code", 400);
            body.put("message", "주문 취소에 실패했습니다.");
            return ResponseEntity.badRequest().body(body);
        }
        body.put("code", 200);
        body.put("message", "주문이 취소되었습니다.");
        return ResponseEntity.ok(body);
    }

    @GetMapping("/info")
    public StudentInfoResponse myInfo(@AuthenticationPrincipal SchoolUserDetails userDetails) {
        return studentService.getStudentInfo(userDetails.getUsername());
    }
}
