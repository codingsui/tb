package com.syl.tb.manage.mapper;

import com.syl.tb.manage.pojo.Order;

public interface OrderMapper {
    int insert(Order record);

    int insertSelective(Order record);
}