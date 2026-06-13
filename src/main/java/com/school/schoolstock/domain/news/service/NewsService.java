package com.school.schoolstock.domain.news.service;

import com.school.schoolstock.domain.news.dto.request.NewsCreateRequest;
import com.school.schoolstock.domain.news.dto.request.NewsDeleteRequest;
import com.school.schoolstock.domain.news.dto.request.NewsUpdateRequest;
import com.school.schoolstock.domain.news.dto.response.NewsManageResponse;

import java.util.List;

public interface NewsService {
    // 뉴스 목록 조회
    List<String> getNewsList();

    // 뉴스 목목 조회(번호포함)
    List<NewsManageResponse> getNewsManageList();

    // 뉴스 등록
    void setNews(NewsCreateRequest request);

    // 뉴스 수정
    void setUpdateNews(NewsUpdateRequest request);

    // 뉴스 삭제
    void setDeleteNews(NewsDeleteRequest request);
}
