package com.syl.tb.usermanage.dao;

import com.syl.tb.usermanage.pojo.ItemBak;

public interface ItemBakMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemBak record);

    int insertSelective(ItemBak record);

    ItemBak selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemBak record);

    int updateByPrimaryKey(ItemBak record);
}