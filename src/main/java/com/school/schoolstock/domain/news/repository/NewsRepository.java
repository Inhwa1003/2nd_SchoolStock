package com.school.schoolstock.domain.news.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsRepository {
    List<String> getNewsList();
}
