package com.school.schoolstock.domain.teacher.repository;

import com.school.schoolstock.domain.teacher.dto.StudentListResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherRepository {

    // 선생님 계정 존재 여부 확인
    String getTeacherIdCheck(String teacherId);

    // 선생님이 맡은 반 학생 목록 조회
    List<StudentListResponse> getMyStudents(String teacherId);
}