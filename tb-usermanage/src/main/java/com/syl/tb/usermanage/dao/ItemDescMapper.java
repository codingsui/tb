package com.syl.tb.usermanage.dao;

import com.syl.tb.usermanage.pojo.ItemDesc;

public interface ItemDescMapper {
    int insert(ItemDesc record);

    int insertSelective(ItemDesc record);
}