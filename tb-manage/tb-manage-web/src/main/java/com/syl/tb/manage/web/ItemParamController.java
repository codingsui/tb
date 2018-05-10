package com.syl.tb.manage.web;

import com.github.pagehelper.PageInfo;
import com.syl.tb.common.bean.Result;
import com.syl.tb.manage.pojo.ItemParam;
import com.syl.tb.manage.service.impl.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ResponseEntity<Result> queryList(@RequestParam(value = "page",defaultValue = "1")Integer page,
    @RequestParam(value = "rows",defaultValue = "30")Integer rows) {
        try {
            PageInfo<ItemParam> paramPageInfo =itemParamService.queryPageListByWhere(page,rows);
            Result result = new Result(paramPageInfo.getTotal(),paramPageInfo.getList());
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    @RequestMapping(value = "{itemCatId}",method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemCatId")Long itemCatId){
        try {

            ItemParam itemParam = new ItemParam();
            itemParam.setItemCatId(itemCatId);
            ItemParam result = itemParamService.quertOne(itemParam);
            System.out.println(result + "----------6666");
            if (null == result){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "{itemCatId}",method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId")Long itemCatId,
                                              @RequestParam("paramData")String paramData){
        try {
            System.out.println(itemCatId + "itemCatId");
            System.out.println(paramData + "paramData");
            ItemParam itemParam = new ItemParam();
            itemParam.setItemCatId(itemCatId);
            itemParam.setId(null);
            itemParam.setParamData(paramData);
            itemParamService.save(itemParam);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
