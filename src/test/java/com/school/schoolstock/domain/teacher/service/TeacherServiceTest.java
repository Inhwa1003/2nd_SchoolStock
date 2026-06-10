package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.student.repository.StudentRepository;
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
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void getMyStudentsListTest() {
        // NO
        // 존재하지 않는 선생님 아이디
        Assertions.assertTrue(teacherService.getMyStudentsList("testTeacher").isEmpty());

        // YES
        // users + teachers에 존재하는 선생님 아이디
        List<StudentListResponse> result = teacherService.getMyStudentsList("t0");

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

    @Test
    public void getStudentIdInClassTest() {
        String teacherId = "t0";

        // 그 선생 반 실제 학생 번호 하나 확보
        List<StudentListResponse> students = teacherService.getMyStudentsList(teacherId);
        Assertions.assertFalse(students.isEmpty());
        int existingNumber = students.get(0).getStudentNumber();

        // YES
        // 내 반 학생 번호 -> studentId 반환
        Assertions.assertNotNull(teacherService.getStudentIdInClass(teacherId, existingNumber));
        log.info("번호 " + existingNumber + " -> studentId : "
                + teacherService.getStudentIdInClass(teacherId, existingNumber));

        // NO
        // 내 반에 없는 번호 -> null
        Assertions.assertNull(teacherService.getStudentIdInClass(teacherId, 99999));
    }

    @Test
    public void givePointTest() {
        String teacherId = "t0";
        int amount = 3000;

        // 그 선생 반 실제 학생 한 명
        List<StudentListResponse> students = teacherService.getMyStudentsList(teacherId);
        Assertions.assertFalse(students.isEmpty());
        String studentId = teacherService.getStudentIdInClass(teacherId, students.get(0).getStudentNumber());

        int beforeHistory = studentRepository.getMyPointHistoryList(studentId).size();
        int beforePoint   = studentRepository.getMyPoint(studentId);

        // YES
        // 지급 -> 보유포인트 증가 + 내역 1건 추가
        Assertions.assertTrue(teacherService.givePoint(studentId, amount, "테스트 지급"));
        Assertions.assertEquals(beforeHistory + 1, studentRepository.getMyPointHistoryList(studentId).size());
        Assertions.assertEquals(beforePoint + amount, studentRepository.getMyPoint(studentId));   // getMyPoint가 total_point면 통과

        log.info("지급 후 보유포인트 : " + studentRepository.getMyPoint(studentId));
    }
}