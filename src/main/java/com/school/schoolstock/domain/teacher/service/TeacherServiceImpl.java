package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.teacher.dto.StudentListResponse;
import com.school.schoolstock.domain.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public List<StudentListResponse> getMyStudentsList(String teacherId) {
        String checkedTeacherId = teacherRepository.getTeacherIdCheck(teacherId);

        if (checkedTeacherId == null) {
            return Collections.emptyList();
        }

        return teacherRepository.getMyStudents(checkedTeacherId);
    }
}