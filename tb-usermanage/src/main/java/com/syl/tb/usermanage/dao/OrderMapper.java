package com.syl.tb.usermanage.dao;

import com.syl.tb.usermanage.pojo.Order;

public interface OrderMapper {
    int insert(Order record);

    int insertSelective(Order record);
}