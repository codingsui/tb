package com.syl.tb.web.controller;

import com.syl.tb.manage.pojo.Cart;
import com.syl.tb.web.bean.Item;
import com.syl.tb.web.bean.Order;
import com.syl.tb.web.bean.User;
import com.syl.tb.web.interceptor.UserLoginHandlerInterceptor;
import com.syl.tb.web.service.CartService;
import com.syl.tb.web.service.ItemService;
import com.syl.tb.web.service.OrderService;
import com.syl.tb.web.service.UserService;
import com.syl.tb.web.threadlocal.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("order")
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CartService cartService;

    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ModelAndView toOrder(@PathVariable("itemId")Long itemId){
        ModelAndView mv = new ModelAndView("order");

        Item item = itemService.queryById(itemId);
        mv.addObject("item",item);
        return mv;
    }

    @RequestMapping(value = "submit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> submit(Order order){
        Map<String,Object> result = new HashMap<>();
        String orderId =  orderService.submit(order);
        if (StringUtils.isEmpty(orderId)){
            result.put("status",500);
        }else {
            result.put("status",200);
            result.put("data",orderId);
        }
        return result;
    }
    @RequestMapping(value = "success",method = RequestMethod.GET)
    public ModelAndView success(@RequestParam("id")String id){
        ModelAndView mv = new ModelAndView("success");

        Order order = orderService.queryByOrderId(id);
        mv.addObject("order",order);

        mv.addObject("data",new DateTime().plusDays(2).toString("MM月dd日"));
        return mv;
    }

    @RequestMapping(value = "create",method = RequestMethod.GET)
    public ModelAndView toCartOrder(){
        ModelAndView mv = new ModelAndView("order-cart");

        List<Cart> carts = cartService.queryList();
        mv.addObject("carts",carts);
        return mv;
    }
}
