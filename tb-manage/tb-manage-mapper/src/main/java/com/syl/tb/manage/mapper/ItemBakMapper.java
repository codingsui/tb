package com.syl.tb.manage.mapper;

import com.syl.tb.manage.pojo.ItemBak;

public interface ItemBakMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemBak record);

    int insertSelective(ItemBak record);

    ItemBak selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemBak record);

    int updateByPrimaryKey(ItemBak record);
}