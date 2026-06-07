package com.school.schoolstock.domain.news.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class NewsServiceImplTest {

    @Autowired
    private NewsServiceImpl newsServiceImpl;

    @Test
    void getNewsListTest(){
        List<String> newsList = newsServiceImpl.getNewsList();

        assertNotNull(newsList);

        System.out.println("뉴스 목록 = " + newsList);

    }

}
