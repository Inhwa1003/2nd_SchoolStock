package com.school.schoolstock.domain.auth.service;

import com.school.schoolstock.domain.auth.dto.request.AddMemberRequest;
import com.school.schoolstock.domain.auth.entity.Role;
import com.school.schoolstock.domain.auth.entity.User;
import com.school.schoolstock.domain.auth.repository.UserRepository;
import com.school.schoolstock.domain.student.repository.StudentRepository;
import com.school.schoolstock.domain.student.vo.Students;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public boolean addMember(AddMemberRequest request) {
        if(userRepository.existsByLoginId(request.getLoginId())) {
           return false;
        }
        userRepository.save(User.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT).build());

        studentRepository.setMember(Students.builder()
                .studentId(request.getLoginId())
                .studentNumber(request.getStudentNumber())
                .name(request.getName())
                .grade(request.getGrade())
                .className(request.getClassName()).build());

        return true;
    }

    @Override
    public boolean existsByLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }
}
