package com.school.schoolstock.domain.news.service;

import com.school.schoolstock.domain.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;

    // 뉴스 목록 조회
    @Override
    public List<String> getNewsList(){
        return newsRepository.getNewsList();
    }
}
