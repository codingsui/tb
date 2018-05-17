package com.syl.tb.manage.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syl.tb.common.bean.ItemCatData;
import com.syl.tb.common.bean.ItemCatResult;
import com.syl.tb.common.service.RedisService;
import com.syl.tb.manage.pojo.ItemCat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("itemCatService")
public class ItemCatService extends BaseService<ItemCat> {

    @Autowired
    private RedisService redisService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public ItemCatResult quertItemCatList() {
        ItemCatResult result = new ItemCatResult();
        List<ItemCat> cats = super.queryAll();

        Map<Long,List<ItemCat>> itemCatMap = new HashMap<>();
        for (ItemCat item : cats){
            if (!itemCatMap.containsKey(item.getParentId())){
                itemCatMap.put(item.getParentId(),new ArrayList<ItemCat>());
            }
            itemCatMap.get(item.getParentId()).add(item);
        }
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for(ItemCat item : itemCatList1){
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/"+item.getId()+".html");
            itemCatData.setName("<a href='"+itemCatData.getUrl()+"'>"+item.getName()+"</a>");
            result.getItemCats().add(itemCatData);
            if (!item.getIsParent()){
                continue;
            }
            List<ItemCat> itemCatList2 = itemCatMap.get(item.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<>();
            itemCatData.setItems(itemCatData2);
            for(ItemCat item2 : itemCatList2){
                ItemCatData id2 = new ItemCatData();
                id2.setName(item2.getName());
                id2.setUrl("/products/"+item2.getId()+".html");
                itemCatData2.add(id2);
                if (item2.getIsParent()){
                    List<ItemCat> itemCatList3 = itemCatMap.get(item2.getId());
                    List<String> itemCatData3 = new ArrayList<>();
                    id2.setItems(itemCatData3);
                    for (ItemCat item3 : itemCatList3){
                        itemCatData3.add("/products/"+item3.getId()+".html|"+item3.getName());
                    }
                }
            }
            if (result.getItemCats().size() >= 14){
                break;
            }
        }
        return result;
    }

    public ItemCatResult quertItemCatList2()  {
        ItemCatResult result = new ItemCatResult();
        //先从缓存命中，如果命中就返回，没有命中继续执行
        String key = "TB_MANAGE_ITEM_CAT_API";//规则：项目名_模块_业务名
        try {
            String cacheData = redisService.get(key);
            if (StringUtils.isNotEmpty(cacheData)){
                try {
                    return MAPPER.readValue(cacheData,ItemCatResult.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        List<ItemCat> cats = super.queryAll();

        Map<Long,List<ItemCat>> itemCatMap = new HashMap<>();
        for (ItemCat item : cats){
            if (!itemCatMap.containsKey(item.getParentId())){
                itemCatMap.put(item.getParentId(),new ArrayList<ItemCat>());
            }
            itemCatMap.get(item.getParentId()).add(item);
        }
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for(ItemCat item : itemCatList1){
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/"+item.getId()+".html");
            itemCatData.setName("<a href='"+itemCatData.getUrl()+"'>"+item.getName()+"</a>");
            result.getItemCats().add(itemCatData);
            if (!item.getIsParent()){
                continue;
            }
            List<ItemCat> itemCatList2 = itemCatMap.get(item.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<>();
            itemCatData.setItems(itemCatData2);
            for(ItemCat item2 : itemCatList2){
                ItemCatData id2 = new ItemCatData();
                id2.setName(item2.getName());
                id2.setUrl("/products/"+item2.getId()+".html");
                itemCatData2.add(id2);
                if (item2.getIsParent()){
                    List<ItemCat> itemCatList3 = itemCatMap.get(item2.getId());
                    List<String> itemCatData3 = new ArrayList<>();
                    id2.setItems(itemCatData3);
                    for (ItemCat item3 : itemCatList3){
                        itemCatData3.add("/products/"+item3.getId()+".html|"+item3.getName());
                    }
                }
            }
            if (result.getItemCats().size() >= 14){
                break;
            }
        }
        try {
            redisService.set(key,MAPPER.writeValueAsString(result),60 * 60 * 24 * 30 * 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
