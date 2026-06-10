package com.school.schoolstock.domain.student.controller;

import com.school.schoolstock.domain.student.service.StudentService;
import com.school.schoolstock.global.security.SchoolUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/schoolstock/s/me/students")
@Controller
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/history")
    public String getMyPointHistory(@AuthenticationPrincipal SchoolUserDetails userDetails, Model model) {
        model.addAttribute("pointHistory",studentService.getMyPointHistory(userDetails.getUsername()));
        return "myPointHistory";
    }

    @GetMapping("/assets")
    public String studentMainView(@AuthenticationPrincipal SchoolUserDetails userDetails, Model model) {
        model.addAttribute("assetsRefreshUrl", "/schoolstock/s/me/students/assets/stocks");
        model.addAttribute("myAssets", studentService.getMyAsset(userDetails.getUsername()));
        return "myAssets";
    }

    @GetMapping("/coupons")
    public String getMyCoupons(@AuthenticationPrincipal SchoolUserDetails userDetails, Model model) {
        model.addAttribute("studentName", userDetails.getUser().getLoginId());
        model.addAttribute("coupons", studentService.getMyCoupon(userDetails.getUsername()));
        return "myCoupons";
    }
}
