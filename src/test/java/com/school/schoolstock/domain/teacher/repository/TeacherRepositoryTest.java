package com.school.schoolstock.domain.teacher.repository;

import com.school.schoolstock.domain.teacher.dto.response.StudentListResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());

        log.info("조회된 학생 수 = {}", result.size());

        for (StudentListResponse student : result) {
            log.info("학생 정보 = {}", student);
        }
    }

    @Test
    void getTeacherNameTest() {
        // NO
        // 존재하지 않는 선생님 -> null
        Assertions.assertNull(teacherRepository.getTeacherName("nope_teacher"));

        // YES
        // 존재하는 선생님 이름 조회
        String name = teacherRepository.getTeacherName("t0");
        Assertions.assertNotNull(name);
        log.info("선생님 이름 = {}", name);
    }

    @Test
    void getStudentIdInClassTest() {
        String teacherId = "t0";

        // 그 선생 반의 실제 학생 번호 하나 확보
        List<StudentListResponse> students = teacherRepository.getMyStudents(teacherId);
        Assertions.assertFalse(students.isEmpty());
        int existingNumber = students.get(0).getStudentNumber();

        // YES - 내 반 학생 번호 → student_id 반환
        String studentId = teacherRepository.getStudentIdInClass(teacherId, existingNumber);
        Assertions.assertNotNull(studentId);
        log.info("번호 {} -> studentId = {}", existingNumber, studentId);

        // NO
        // 내 반에 없는 번호 -> null
        Assertions.assertNull(teacherRepository.getStudentIdInClass(teacherId, 99999));

        // NO
        // 다른(없는) 선생님으로 조회 -> null
        Assertions.assertNull(teacherRepository.getStudentIdInClass("nope_teacher", existingNumber));

    }

    @Transactional
    @Test
    void setPointGiveTest() {
        // YES
        // 지급 내역(get_point) INSERT 성공 (rows > 0 -> true)
        Assertions.assertTrue(teacherRepository.setPointGive("abc", 5000, "테스트 지급"));

        log.info("get_point INSERT 결과 : " + teacherRepository.setPointGive("abc", 5000, "테스트 지급"));
    }
}