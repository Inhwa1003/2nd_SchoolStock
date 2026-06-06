package com.school.schoolstock.domain.auth.repository;

import com.school.schoolstock.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    //로그인시 사용자 조회
    Optional<User> findByLoginId(String loginId);
    //회원가입 중복 체크용
    boolean existsByLoginId(String loginId);
}
