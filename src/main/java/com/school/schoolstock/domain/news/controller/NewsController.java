package com.school.schoolstock.domain.news.controller;

import com.school.schoolstock.domain.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class NewsController {

    private final NewsService newsService;

    // 뉴스 목록 화면
    @GetMapping("/schoolstock/s/news")
    public String getNewsList(Model model) {

        model.addAttribute("newsList", newsService.getNewsList());

        return "news";
    }
}