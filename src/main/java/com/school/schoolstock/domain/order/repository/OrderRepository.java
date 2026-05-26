package com.school.schoolstock.domain.order.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderRepository {

    Integer findLatestOrderNo(
            @Param("orderContent") String orderContent,
            @Param("studentId") String studentId,
            @Param("stockNo") int stockNo,
            @Param("orderState") String orderState,
            @Param("amount") int amount,
            @Param("orderPoint") int orderPoint
    );
}