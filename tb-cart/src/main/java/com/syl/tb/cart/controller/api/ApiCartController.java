package com.syl.tb.cart.controller.api;

import com.syl.tb.cart.pojo.Cart;
import com.syl.tb.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("api/cart")
public class ApiCartController {
    @Autowired
    private CartService cartService;


    @RequestMapping(value = "{userid}",method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> queryList(@PathVariable("userId")Long userId){
        try {

            List<Cart> list = cartService.queryList(userId);
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
