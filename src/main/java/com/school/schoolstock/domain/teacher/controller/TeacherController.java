package com.school.schoolstock.domain.teacher.controller;

import com.school.schoolstock.domain.student.service.StudentService;
import com.school.schoolstock.domain.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/schoolstock/t")
public class TeacherController {

    private final TeacherService teacherService;
    private final StudentService studentService;

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
}