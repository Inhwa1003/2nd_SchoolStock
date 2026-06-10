package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.teacher.dto.response.StudentListResponse;

import java.util.List;

public interface TeacherService {

    // 선생님이 맡은 반 학생 목록 조회
    List<StudentListResponse> getMyStudentsList(String teacherId);

    // 선생님이 맡은 반 학생 반번호로 아이디 조회
    String getStudentIdInClass(String teacherId, int studentNumber);

    // 선생님 이름 조회(사이드바)
    String getTeacherName(String teacherId);

    // 포인트 지급
    boolean givePoint(String studentId, int point, String content);
}