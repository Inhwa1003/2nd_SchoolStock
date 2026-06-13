package com.school.schoolstock.domain.news.service;

import com.school.schoolstock.domain.news.dto.request.NewsCreateRequest;
import com.school.schoolstock.domain.news.dto.request.NewsDeleteRequest;
import com.school.schoolstock.domain.news.dto.request.NewsUpdateRequest;
import com.school.schoolstock.domain.news.dto.response.NewsManageResponse;
import com.school.schoolstock.domain.news.repository.NewsRepository;
import com.school.schoolstock.domain.news.vo.News;
import com.school.schoolstock.domain.teacher.repository.TeacherRepository;
import com.school.schoolstock.global.error.BusinessException;
import com.school.schoolstock.global.error.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    //뉴스 목록 조회
    @Override
    public List<String> getNewsList(){
        return newsRepository.getNewsList();
    }

    //뉴스 목록 조회(번호포함) -> 선생님 업무
    @Override
    public List<NewsManageResponse> getNewsManageList() {
        List<News> news = newsRepository.getNewsManageList();
        List<NewsManageResponse> newsManageList = new ArrayList<>();

        for (News newsItem : news) {
            newsManageList.add(NewsManageResponse.builder()
                    .newsNo(newsItem.getNewsNo())
                    .newsContent(newsItem.getNewsContent()).build());
        }

        return newsManageList;
    }

    //뉴스 추가
    @Transactional
    @Override
    public void setNews(NewsCreateRequest request) {
        //빈값 요청 체크
        if(request.getNewsContent() == null || request.getNewsContent().trim().isEmpty()){
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        //100자 제한 체크
        if(request.getNewsContent().length() > 100){
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        newsRepository.setNews(request.getNewsContent());
    }

    //뉴스 수정
    @Transactional
    @Override
    public void setUpdateNews(NewsUpdateRequest request) {
        //빈값 요청 체크
        if(request.getNewsContent() == null || request.getNewsContent().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        //없는번호 수정 체크
        if(newsRepository.setUpdateNews(request.getNewsNo(), request.getNewsContent()) == 0) {
            throw new BusinessException(ErrorCode.NEWS_NOT_FOUND);
        }
    }

    //뉴스 삭제
    @Transactional
    @Override
    public void setDeleteNews(NewsDeleteRequest request) {
        //음수값 체크 (잘못된 번호)
        if(request.getNewsNo() <= 0) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        //존재하지 않는 뉴스NO 체크 (없는 번호)
        if(newsRepository.setDeleteNews(request.getNewsNo()) ==  0) {
            throw new BusinessException(ErrorCode.NEWS_NOT_FOUND);
        }
    }
}
