package com.school.schoolstock.domain.news.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class NewsRepositoryTest {

    @Autowired
    NewsRepository newsRepository;

    @Test
    void getNewsListTest(){
        List<String> result = newsRepository.getNewsList();
        System.out.println("등록된 전체 뉴스 내용: "+ result);
    }

}
