package com.school.schoolstock.domain.teacher.controller;

import com.school.schoolstock.domain.student.dto.response.MyAssetResponse;
import com.school.schoolstock.domain.student.service.StudentService;
import com.school.schoolstock.domain.teacher.dto.request.PointGrantRequest;
import com.school.schoolstock.domain.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
