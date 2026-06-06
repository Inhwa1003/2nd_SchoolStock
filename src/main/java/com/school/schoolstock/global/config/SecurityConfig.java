package com.school.schoolstock.global.config;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/schoolstock/s/**").hasRole("STUDENT")
                .requestMatchers("/schoolstock/t/**").hasRole("TEACHER")
                .anyRequest().permitAll());

        http.formLogin(form -> form
                .loginPage("/loginView")
                .loginProcessingUrl("/login")
                .failureUrl("/loginView?error=true"));

        http.logout(logout -> logout
                .logoutSuccessUrl("/loginView")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"));


        return http.build();
    }
}
