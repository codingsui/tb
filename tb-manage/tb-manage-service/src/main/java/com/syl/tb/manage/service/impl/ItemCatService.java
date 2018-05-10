package com.syl.tb.manage.service.impl;

import com.syl.tb.common.bean.ItemCatData;
import com.syl.tb.common.bean.ItemCatResult;
import com.syl.tb.manage.mapper.ItemCatMapper;
import com.syl.tb.manage.pojo.ItemCat;
import com.syl.tb.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("itemCatService")
public class ItemCatService extends BaseService<ItemCat> {
    @Autowired
    private ItemCatMapper itemCatMapper;

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
}
