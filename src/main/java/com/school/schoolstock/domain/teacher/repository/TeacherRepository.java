package com.school.schoolstock.domain.teacher.repository;

import com.school.schoolstock.domain.teacher.dto.StudentListResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherRepository {

    // 로그인 후 첫 화면: 담당학생의 정보(이름, 번호, 보유 포인트)를 조회
    List<StudentListResponse> getMyStudents(String teacherId);

}
