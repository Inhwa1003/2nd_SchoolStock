package com.school.schoolstock.domain.teacher.controller;

import com.school.schoolstock.domain.stock.service.StockService;
import com.school.schoolstock.domain.student.dto.response.StudentInfoResponse;
import com.school.schoolstock.domain.student.service.StudentService;
import com.school.schoolstock.domain.coupon.service.CouponService;
import com.school.schoolstock.domain.coupon.vo.Coupons;
import com.school.schoolstock.domain.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/schoolstock/t")
public class
TeacherController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final CouponService couponService;
    private final StockService stockService;

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

        model.addAttribute("couponHref",
                "/schoolstock/t/me/teachers/my-students/" + studentNumber + "/coupons");
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

        return "couponMarket";
    }

    // 학생 보유 쿠폰 확인 화면
    @GetMapping("/me/teachers/my-students/{studentNumber}/coupons")
    public String getStudentCoupons(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int studentNumber,
            Model model){

        String studentId = teacherService.getStudentIdInClass(
                userDetails.getUsername(),
                studentNumber
        );

        StudentInfoResponse studentName = studentService.getStudentInfo(studentId);

        model.addAttribute("coupons", studentService.getMyCouponList(studentId));
        model.addAttribute("couponTitle", studentName.getName() + "의 보유 쿠폰");
        model.addAttribute("targetStudentNumber", studentNumber);

        return "myCoupons";
    }

    // 선생님이 주식 목록 + 등록 폼 화면 보기
    @GetMapping("/stocks")
    public String getStockList(Model model){
        model.addAttribute("stockList", stockService.getManageStockList());
        return "stockList";
    }

}