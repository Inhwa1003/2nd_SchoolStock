package com.school.schoolstock.global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RoleSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        boolean isTeacher = authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_TEACHER"));
        String targetUrl = isTeacher ? "/schoolstock/t/me/teachers/my-students" : "/schoolstock/s/me/students/assets";
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
