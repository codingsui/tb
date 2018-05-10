package com.syl.tb.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syl.tb.common.bean.Result;
import com.syl.tb.manage.mapper.ItemMapper;
import com.syl.tb.manage.pojo.Item;
import com.syl.tb.manage.pojo.ItemDesc;
import com.syl.tb.manage.pojo.ItemParamItem;
import com.syl.tb.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service("itemService")
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemParamItemService itemParamItemService;

    public boolean saveItem(Item item, String desc,String itemParams) {
        item.setStatus(1);
        item.setId(null);//出于安全考虑
        int count = super.save(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        int count2 = itemDescService.save(itemDesc);
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(item.getId());
        itemParamItem.setParamData(itemParams);
        int count3 = itemParamItemService.save(itemParamItem);
        if (count + count2 +count3 != 3)
            return false;
        return true;
    }

    public Result queryItemList(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        Example example = new Example(Item.class);
        example.setOrderByClause("created DESC");
        List<Item> list =itemMapper.selectByExample(example);
        PageInfo<Item> pageInfo = new PageInfo<>(list);
        return new Result(pageInfo.getTotal(),pageInfo.getList());
    }

    public boolean updateItem(Item item, String desc,String itemParams) {
        item.setStatus(null);
        int count = super.updateSelective(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        int count1 = itemDescService.updateSelective(itemDesc);
        int count2 = itemParamItemService.updateItemParamItem(item.getId(),itemParams);
        return count == 1 && count1 == 1 && count2==1;
    }
}
