package com.syl.tb.cart.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syl.tb.cart.mapper.CartMapper;
import com.syl.tb.cart.pojo.Cart;
import com.syl.tb.cart.pojo.Item;
import com.syl.tb.cart.pojo.User;
import com.syl.tb.cart.threadlocal.UserThreadLocal;
import com.syl.tb.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartCookieService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ItemService itemService;
    private static final String COOKIENAME = "TB_CART";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void addItemToCart(Long itemId, HttpServletRequest request, HttpServletResponse response) {
      try {
          List<Cart> list = findList(request);
          Cart cart = null;
          for (Cart c : list){
              if (c.getItemId().longValue() == itemId.longValue()){
                  cart = c;
                  break;
              }
          }
          if (cart == null){
              //不存在
              cart = new Cart();
              cart.setUpdated(new Date());
              cart.setCreated(cart.getUpdated());
              //商品基本数据需要后台系统查询
              Item item = itemService.queryById(itemId);
              cart.setItemId(itemId);
              cart.setItemTitle(item.getTitle());
              cart.setItemPrice(item.getPrice());
              cart.setItemImage(StringUtils.split(item.getImage(),",")[0]);
              cart.setNum(1);//TODO

              list.add(cart);
          }else {
              //存在，数量相加
              cart.setNum(cart.getNum()+1);
              cart.setUpdated(new Date());
          }
          CookieUtils.setCookie(request,response,COOKIENAME,MAPPER.writeValueAsString(list),60*60*24*30*12,true);
      }catch (Exception e){
          e.printStackTrace();;
      }
    }


    public List<Cart> quertList(HttpServletRequest request) {
       return findList(request);
    }
    private List<Cart> findList(HttpServletRequest request){
        List<Cart> list = null;
        try {
            String jsonData = CookieUtils.getCookieValue(request,COOKIENAME,true);

            if (StringUtils.isEmpty(jsonData)){
                list = new ArrayList<>();
            }else {
                list = MAPPER.readValue(jsonData,MAPPER.getTypeFactory().constructCollectionType(List.class,Cart.class));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public void updateNum(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        List<Cart> list = findList(request);
        Cart cart = null;
        for (Cart c : list){
            if (c.getItemId().longValue() == itemId.longValue()){
                cart = c;
                break;
            }
        }
        if (cart != null){
            cart.setNum(num);
            cart.setUpdated(new Date());
        }else {
            return;
        }

        CookieUtils.setCookie(request,response,COOKIENAME,MAPPER.writeValueAsString(list),60*60*24*30*12,true);

    }

    public void delete(Long itemId, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        List<Cart> list = findList(request);
        Cart cart = null;
        for (Cart c : list){
            if (c.getItemId().longValue() == itemId.longValue()){
                cart = c;
                list.remove(c);
                break;
            }
        }
        if (cart == null){
            return;
        }
        CookieUtils.setCookie(request,response,COOKIENAME,MAPPER.writeValueAsString(list),60*60*24*30*12,true);

    }
}
