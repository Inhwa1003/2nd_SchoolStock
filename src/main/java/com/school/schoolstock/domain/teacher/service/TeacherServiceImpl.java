package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.student.repository.StudentRepository;
import com.school.schoolstock.domain.teacher.dto.response.StudentListResponse;
import com.school.schoolstock.domain.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<StudentListResponse> getMyStudentsList(String teacherId) {
        String checkedTeacherId = teacherRepository.getTeacherIdCheck(teacherId);

        if (checkedTeacherId == null) {
            return Collections.emptyList();
        }

        return teacherRepository.getMyStudents(checkedTeacherId);
    }

    @Override
    public String getStudentIdInClass(String teacherId, int studentNumber) {
        return teacherRepository.getStudentIdInClass(teacherId, studentNumber);
    }

    @Override
    public String getTeacherName(String teacherId) {
        return teacherRepository.getTeacherName(teacherId);
    }

    @Transactional
    @Override
    public boolean givePoint(String studentId, int point, String content) {
        // 예외 처리 필요함 지급 안될경우
        studentRepository.setStudentPointUp(studentId, point);
        teacherRepository.setPointGive(studentId, point, content);
        return true;
    }
}