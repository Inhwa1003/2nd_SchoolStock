package com.school.schoolstock.domain.teacher.repository;

import com.school.schoolstock.domain.teacher.dto.response.StudentListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherRepository {

    // 선생님 계정 존재 여부 확인
    String getTeacherIdCheck(String teacherId);

    // 선생님이 맡은 반 학생 목록 조회
    List<StudentListResponse> getMyStudents(String teacherId);

    // 선생님이 맡은 반 학생 반번호로 아이디 조회
    String getStudentIdInClass(@Param("teacherId") String teacherId, @Param("studentNumber") int studentNumber);

    // 선생님 이름 조회(사이드바)
    String getTeacherName(String teacherId);

    // 포인트 지급
    boolean setPointGive(String studentId, int point, String content);
}