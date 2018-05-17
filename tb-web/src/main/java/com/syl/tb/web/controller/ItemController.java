package com.syl.tb.web.controller;

import com.syl.tb.manage.pojo.Item;
import com.syl.tb.manage.pojo.ItemDesc;
import com.syl.tb.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("item")
@Controller
public class ItemController {


    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ModelAndView itemDetail(@PathVariable("itemId")Long itemId){
        ModelAndView mv = new ModelAndView("item");
        com.syl.tb.web.bean.Item item = itemService.queryById(itemId);
        mv.addObject("item",item);

        ItemDesc itemDesc = itemService.queryDescByItemId(itemId);
        mv.addObject("itemDesc",itemDesc);

        String itemParam = itemService.queryItemParamItemByItemId(itemId);
        mv.addObject("itemParam",itemParam);
        return mv;
    }
}
