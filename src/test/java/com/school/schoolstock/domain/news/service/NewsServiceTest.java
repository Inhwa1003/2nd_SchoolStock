package com.school.schoolstock.domain.news.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class NewsServiceTest {

    @Autowired
    private NewsService newsService;

    @Test
    void getNewsListTest(){
        List<String> newsList = newsService.getNewsList();

        assertNotNull(newsList);

        System.out.println("뉴스 목록 = " + newsList);

    }

}
