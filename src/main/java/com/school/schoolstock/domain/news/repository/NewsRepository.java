package com.school.schoolstock.domain.news.repository;

import com.school.schoolstock.domain.news.vo.News;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsRepository {
    //뉴스조회
    List<String> getNewsList();

    //선생님 관리용 목록(번호포함)
    List<News> getNewsManageList();

}
