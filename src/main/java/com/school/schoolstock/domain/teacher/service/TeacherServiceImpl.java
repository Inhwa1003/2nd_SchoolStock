package com.school.schoolstock.domain.teacher.service;

import com.school.schoolstock.domain.teacher.dto.StudentListResponse;
import com.school.schoolstock.domain.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public List<StudentListResponse> getMyStudents(String teacherId) {
        return teacherRepository.getMyStudents(teacherId);
    }
}