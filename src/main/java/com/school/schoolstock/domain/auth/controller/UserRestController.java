package com.school.schoolstock.domain.auth.controller;

import com.school.schoolstock.domain.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schoolstock/a")
public class UserRestController {
    private final UserService userService;

    @GetMapping("/check-id")
    public Map<String, Boolean> existsByLoginId(@RequestParam String loginId){
        return Map.of("exists", userService.existsByLoginId(loginId));
    }
}
