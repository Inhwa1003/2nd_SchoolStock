package com.school.schoolstock.domain.student.controller;

import com.school.schoolstock.domain.student.dto.response.MyAssetResponse;
import com.school.schoolstock.domain.student.service.StudentService;
import com.school.schoolstock.global.security.SchoolUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@RequestMapping("/schoolstock/s/me/student")
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
        model.addAttribute("myAssets", studentService.getMyAsset(userDetails.getUsername()));
        return "myAssets";
    }

    @GetMapping("/coupons")
    public String getMyCoupons(@AuthenticationPrincipal SchoolUserDetails userDetails, Model model) {
        model.addAttribute("studentName", userDetails.getUser().getLoginId());
        model.addAttribute("coupons", studentService.getMyCoupon(userDetails.getUsername()));
        return "myCoupons";
    }

    @GetMapping("/assets/stocks")
    @ResponseBody
    public MyAssetResponse refreshMyAssets(@AuthenticationPrincipal SchoolUserDetails userDetails) {
        return studentService.getMyAsset(userDetails.getUsername());
    }
}
