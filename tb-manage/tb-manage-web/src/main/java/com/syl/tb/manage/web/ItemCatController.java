package com.syl.tb.manage.web;

import com.syl.tb.manage.service.impl.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import com.syl.tb.manage.pojo.*;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(
            @RequestParam(value = "id",defaultValue = "0")Long id){
        try {
            ItemCat t = new ItemCat();
            t.setParentId(id);
            List<ItemCat> list = itemCatService.queryListByWhere(t);
            if (null == list || list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
