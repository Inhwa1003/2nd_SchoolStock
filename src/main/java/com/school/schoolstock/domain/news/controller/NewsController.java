package com.school.schoolstock.domain.news.controller;

import com.school.schoolstock.domain.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class NewsController {

    private final NewsService newsService;

    // 뉴스 목록 화면
    @GetMapping("/schoolstock/s/news")
    public String getNewsList(Model model) {

        try {
            List<String> newsList = newsService.getNewsList();

            if (newsList == null || newsList.isEmpty()) {
                model.addAttribute("newsList", Collections.emptyList());
                model.addAttribute("code", "NEWS_NOT_FOUND");
                model.addAttribute("message", "등록된 뉴스를 찾을 수 없습니다.");

                return "news";
            }

            model.addAttribute("newsList", newsList);
            model.addAttribute("code", "OK");
            model.addAttribute("message", "뉴스 목록이 정상적으로 조회되었습니다.");

            return "news";

        } catch (Exception e) {
            model.addAttribute("newsList", Collections.emptyList());
            model.addAttribute("code", "INTERNAL_SERVER_ERROR");
            model.addAttribute("message", "서버 내부 오류로 뉴스 목록 조회에 실패했습니다.");

            return "news";
        }
    }
}