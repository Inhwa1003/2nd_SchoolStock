package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.teacher.dto.StudentListResponse;

import java.util.List;

public interface TeacherService {

    // 선생님이 맡은 반 학생 목록 조회
    List<StudentListResponse> getMyStudents(String teacherId);
}