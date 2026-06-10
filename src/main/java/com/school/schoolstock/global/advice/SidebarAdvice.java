package com.school.schoolstock.global.advice;

import com.school.schoolstock.domain.auth.controller.UserController;
import com.school.schoolstock.domain.auth.entity.Role;
import com.school.schoolstock.domain.coupon.controller.CouponController;
import com.school.schoolstock.domain.news.controller.NewsController;
import com.school.schoolstock.domain.order.controller.OrderController;
import com.school.schoolstock.domain.stock.controller.StockController;
import com.school.schoolstock.domain.student.controller.StudentController;
import com.school.schoolstock.domain.student.dto.response.StudentInfoResponse;
import com.school.schoolstock.domain.student.service.StudentService;
import com.school.schoolstock.global.security.SchoolUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@ControllerAdvice(assignableTypes ={
        StudentController.class,
        StockController.class,
        OrderController.class,
        NewsController.class,
        CouponController.class,
        UserController.class
})
public class SidebarAdvice {
    private final StudentService studentService;

    @ModelAttribute("info")
    public StudentInfoResponse getStudentInfo(@AuthenticationPrincipal SchoolUserDetails userDetails) {
        if(userDetails == null) {
            return null;
        }
        if(userDetails.getUser().getRole() == Role.TEACHER) {
            return StudentInfoResponse.builder()
                    .name(userDetails.getUsername()).build();
        }
        return studentService.getStudentInfo(userDetails.getUsername());
    }
}
