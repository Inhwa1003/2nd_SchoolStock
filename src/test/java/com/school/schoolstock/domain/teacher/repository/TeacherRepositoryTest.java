package com.school.schoolstock.domain.teacher.repository;

import com.school.schoolstock.domain.teacher.dto.StudentListResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void getMyStudentsTest() {
        // given
        String teacherId = "teacher01";

        // when
        List<StudentListResponse> result = teacherRepository.getMyStudents(teacherId);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());

        log.info("조회된 학생 수 = {}", result.size());

        for (StudentListResponse student : result) {
            log.info("학생 정보 = {}", student);
        }
    }
}