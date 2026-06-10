package com.school.schoolstock.global.config;

import com.school.schoolstock.global.security.RoleSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RoleSuccessHandler SuccessHandler) throws Exception{

//        http.csrf(csrf -> csrf
//                .ignoringRequestMatchers("/schoolstock/s/me/coupon-purchases")
//        );

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/",
                        "/schoolstock/login-view",
                        "/schoolstock/login",
                        "/schoolstock/add-member-view",
                        "/schoolstock/add-member",
                        "/schoolstock/a/check-id",
                        "/css/**",
                        "/js/**")
                .permitAll()

                .requestMatchers("/schoolstock/s/**").hasRole("STUDENT")
                .requestMatchers("/schoolstock/t/**").hasRole("TEACHER")

                .anyRequest().authenticated()
        );

        http.formLogin(form -> form
                .loginPage("/schoolstock/login-view")
                .loginProcessingUrl("/schoolstock/login")
                .successHandler(SuccessHandler)
                .failureUrl("/schoolstock/login-view?error=true"));

        http.logout(logout -> logout
                .logoutUrl("/schoolstock/logout")
                .logoutSuccessUrl("/schoolstock/login-view")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"));


        return http.build();
    }
}
