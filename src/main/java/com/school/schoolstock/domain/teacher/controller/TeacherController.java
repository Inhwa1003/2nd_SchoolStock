package com.school.schoolstock.domain.teacher.controller;

import com.school.schoolstock.domain.teacher.dto.StudentListResponse;
import com.school.schoolstock.domain.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/schoolstock/t")
public class TeacherController {

    private final TeacherService teacherService;

    // 선생님이 맡은 반 학생 목록 조회 화면
    @GetMapping("/me/teachers/my-students")
    public String getMyStudents(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        String teacherId = userDetails.getUsername();

        List<StudentListResponse> myStudentList = teacherService.getMyStudentsList(teacherId);

        model.addAttribute("studentList", myStudentList);

        return "MyStudentList";
    }
}