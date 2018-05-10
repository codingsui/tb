package com.syl.tb.manage.web;

import com.syl.tb.manage.pojo.ItemParamItem;
import com.syl.tb.manage.service.impl.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("item/param/Item")
public class ItemParamItemController {

    @Autowired
    private ItemParamItemService itemParamItemService;

    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ResponseEntity<ItemParamItem> queryByItemParamItem(@PathVariable("itemId")Long itemId){
        try {
            ItemParamItem record = new ItemParamItem();
            record.setItemId(itemId);
            ItemParamItem itemParamItem = itemParamItemService.quertOne(record);
            if (null == itemParamItem){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            return ResponseEntity.ok(itemParamItem);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
