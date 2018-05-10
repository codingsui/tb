package com.syl.tb.manage.web;

import com.syl.tb.common.bean.Result;
import com.syl.tb.manage.pojo.Item;
import com.syl.tb.manage.service.impl.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("item")
public class ItemController {

    private Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc")String desc,@RequestParam("itemParams")String itemParams){
        try {
            logger.info("新增商品,item={},desc={}",item,desc);
            boolean bool = itemService.saveItem(item,desc,itemParams);
            if (bool){
                logger.info("新增商品成功,item={},desc={}",item,desc);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
            logger.info("新增商品失败,item={}",item);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            logger.error("新增商品出错，item "+ item,e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Result> quertItemList(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "30")Integer rows){
       try {
           Result result = itemService.queryItemList(page,rows);
           return ResponseEntity.ok(result);
       }catch (Exception e){
           logger.error("查询商品出错,page = "+page   ,e);
       }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item,@RequestParam("desc")String desc,@RequestParam("itemParams")String itemParams){
        try {
            logger.info("编辑商品,item={},desc={}",item,desc);
            boolean bool = itemService.updateItem(item,desc,itemParams);
            if (bool){
                logger.info("编辑商品成功,item={},desc={}",item,desc);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            logger.info("编辑商品失败,item={}",item);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            logger.error("编辑商品出错，item "+ item,e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
