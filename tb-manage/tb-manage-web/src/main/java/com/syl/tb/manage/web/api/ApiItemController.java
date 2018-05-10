package com.syl.tb.manage.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syl.tb.common.bean.ItemCatResult;
import com.syl.tb.manage.service.impl.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/item/cat")
public class ApiItemController {


//    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 对外提供接口服务
     */
    @Autowired
    private ItemCatService itemCatService;
//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<String> quertItemCatList(@RequestParam(value = "callback" ,required = false)String callback){
//        try {
//            ItemCatResult result = itemCatService.quertItemCatList();
//            String json = MAPPER.writeValueAsString(result);
//            if (StringUtils.isEmpty(callback)){
//                return ResponseEntity.ok(json);
//            }
//            return ResponseEntity.ok(callback + "(" + json + ");");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> quertItemCatList(@RequestParam(value = "callback" ,required = false)String callback){
        try {
            ItemCatResult result = itemCatService.quertItemCatList();
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
