package com.syl.tb.manage.mapper;

import com.syl.tb.manage.pojo.OrderItem;

public interface OrderItemMapper {
    int insert(OrderItem record);

    int insertSelective(OrderItem record);
}