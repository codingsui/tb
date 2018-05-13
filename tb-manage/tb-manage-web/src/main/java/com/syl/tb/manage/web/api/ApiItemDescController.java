package com.syl.tb.manage.web.api;

import com.syl.tb.common.bean.ItemCatResult;
import com.syl.tb.manage.pojo.ItemDesc;
import com.syl.tb.manage.service.impl.ItemCatService;
import com.syl.tb.manage.service.impl.ItemDescService;
import com.syl.tb.manage.service.impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/item/desc")
public class ApiItemDescController {
    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ResponseEntity<ItemDesc> queryById(@PathVariable("itemId")Long id){
        try {
            ItemDesc itemDesc = itemDescService.queryById(id);
            if (itemDesc == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.ok(itemDesc);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


}
