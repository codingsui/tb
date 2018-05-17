package com.syl.tb.manage.service.impl;

import com.syl.tb.manage.mapper.ItemParamItemMapper;
import com.syl.tb.manage.pojo.ItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {

    @Autowired
    private ItemParamItemMapper itemParamItemMapper;
    public int updateItemParamItem(Long itemId,String itemParams) {
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setParamData(itemParams);
        itemParamItem.setUpdated(new Date());
        Example example = new Example(ItemParamItem.class);
        example.createCriteria().andEqualTo("itemId",itemId);
        return itemParamItemMapper.updateByExampleSelective(itemParamItem,example);
    }
}
