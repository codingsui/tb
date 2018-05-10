package com.syl.tb.usermanage.dao;

import com.syl.tb.usermanage.pojo.OrderItem;

public interface OrderItemMapper {
    int insert(OrderItem record);

    int insertSelective(OrderItem record);
}