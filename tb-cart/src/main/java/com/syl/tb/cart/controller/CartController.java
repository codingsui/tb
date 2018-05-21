package com.syl.tb.cart.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.syl.tb.cart.mapper.CartMapper;
import com.syl.tb.cart.pojo.Cart;
import com.syl.tb.cart.pojo.User;
import com.syl.tb.cart.service.CartCookieService;
import com.syl.tb.cart.service.CartService;
import com.syl.tb.cart.threadlocal.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartCookieService cartCookieService;
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView cartList(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("cart");
        User user = UserThreadLocal.get();
        List<Cart> list = null;
        if (null == user){
           list = cartCookieService.quertList(request);
        }else {
           list = cartService.queryList();

        }
        mv.addObject("cartList",list);
        return mv;
    }

    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public String addItemToCart(@PathVariable("itemId")Long itemId, HttpServletRequest request, HttpServletResponse response){
        User user = UserThreadLocal.get();
        if (null == user){
            cartCookieService.addItemToCart(itemId,request,response);
        }else {
            System.out.println("登录--9494");
            cartService.addItemToCart(itemId);
        }
        System.out.println(user + "*****************");
        return "redirect:/cart/list.html";
    }

    @RequestMapping(value = "update/num/{itemId}/{num}",method = RequestMethod.POST)
    public ResponseEntity<Void> updateNum(@PathVariable("itemId")Long itemId,
                                          @PathVariable("num")Integer num,HttpServletRequest request,HttpServletResponse response){

        User user = UserThreadLocal.get();
        if (null == user){
            try {
                cartCookieService.updateNum(itemId,num,request,response);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else {
            cartService.updateNum(itemId,num);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "delete/{itemId}",method = RequestMethod.GET)
    public String delete(@PathVariable("itemId")Long itemId,HttpServletRequest request,HttpServletResponse response){
        User user = UserThreadLocal.get();
        if (null == user){
            try {
                cartCookieService.delete(itemId,request,response);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else {
            cartService.delete(itemId);
        }
        return "redirect:/cart/list.html";
    }
}
