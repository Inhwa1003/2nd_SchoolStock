package com.school.schoolstock.domain.news.service;

import com.school.schoolstock.domain.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    // Service가 실행되는 도중에 Repository가 바뀌더라도, 적용이 안되게 하기위해 final 사용.
    @Autowired
    private NewsRepository newsRepository;
    //private final NewsRepository newsRepository;

    // 생성자 주입.
    // this.newsRepository => NewsService 객체가 갖고 있는 newsRepository 필드
    // 우항은 생성자로 전달받은 newsRepository 매개변수
    // public NewsService(NewsRepository newsRepository){
    //    this.newsRepository = newsRepository;
    //}

    // 뉴스 목록 조회
    public List<String> getNewsList(){
        return newsRepository.getNewsList();
    }
}
