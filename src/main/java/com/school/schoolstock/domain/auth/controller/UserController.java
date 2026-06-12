package com.school.schoolstock.domain.auth.controller;

import com.school.schoolstock.domain.auth.dto.request.AddMemberRequest;
import com.school.schoolstock.domain.auth.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/schoolstock/login-view";
    }

    @GetMapping("/schoolstock/login-view")
    public String loginView(){
        return "loginView";
    }

    @GetMapping("/schoolstock/add-member-view")
    public String addMemberView(){
        return "addMember";
    }

    @PostMapping("/schoolstock/add-member")
    public String addMember(AddMemberRequest request){
        if(userService.addMember(request)){
            return "redirect:/schoolstock/login-view";
        }
        return "redirect:/schoolstock/add-member-view";
    }
    @GetMapping("/schoolstock/t/main")
    public String teacherMainView(){
        return "teacherMain";
    }
}
