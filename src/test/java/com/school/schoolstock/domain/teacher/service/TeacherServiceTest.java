package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.teacher.dto.response.StudentListResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@SpringBootTest
public class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;

    @Test
    public void getMyStudentsListTest() {
        // NO
        // 존재하지 않는 선생님 아이디
        Assertions.assertTrue(teacherService.getMyStudentsList("testTeacher").isEmpty());

        // YES
        // users + teachers에 존재하는 선생님 아이디
        List<StudentListResponse> result = teacherService.getMyStudentsList("teacher01");

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());

        log.info("담당 반 학생 목록 조회 결과 : {}", result);
    }

    @Test
    public void getTeacherNameTest() {
        String teacherId = "t0";

        // NO
        // 존재하지 않는 선생님 -> null
        Assertions.assertNull(teacherService.getTeacherName("testTeacher"));

        // YES
        // 존재하는 선생님 이름
        String name = teacherService.getTeacherName(teacherId);
        Assertions.assertNotNull(name);
        Assertions.assertNotEquals(teacherId, name);
        log.info("선생님 이름 = {}", name);
    }
}