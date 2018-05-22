package com.syl.tb.cart.service;

import com.syl.tb.cart.mapper.CartMapper;
import com.syl.tb.cart.pojo.Cart;
import com.syl.tb.cart.pojo.Item;
import com.syl.tb.cart.pojo.User;
import com.syl.tb.cart.threadlocal.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ItemService itemService;

    public void addItemToCart(Long itemId) {
        User user = UserThreadLocal.get();
        Cart record = new Cart();
        record.setItemId(itemId);
        record.setUserId(user.getId());
        Cart cart = cartMapper.selectOne(record);
        System.out.println("----------9494--"+cart);
        if (cart == null){
            //不存在
            cart = new Cart();
            cart.setItemId(itemId);
            cart.setUserId(user.getId());
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());

            //商品基本数据需要后台系统查询
            Item item = itemService.queryById(itemId);

            cart.setItemTitle(item.getTitle());
            cart.setItemPrice(item.getPrice());
            cart.setItemImage(StringUtils.split(item.getImage(),",")[0]);
            cart.setNum(1);//TODO
            int c =cartMapper.insert(cart);
            System.out.println("--9494-插入"+c);
        }else {
            //存在，数量相加
            cart.setNum(cart.getNum()+1);
            cart.setUpdated(new Date());
            int c = cartMapper.updateByPrimaryKey(cart);
            System.out.println("--9494-更新"+c);
        }
    }

    public List<Cart> queryList() {
        return queryList(UserThreadLocal.get().getId());
    }

    public int updateNum(Long itemId, Integer num) {
        Cart record = new Cart();
        record.setNum(num);
        record.setUpdated(new Date());
        Example example = new Example(Cart.class);
        example.createCriteria().andEqualTo("itemId",itemId).andEqualTo("userId",UserThreadLocal.get().getId());
        return cartMapper.updateByExample(record,example);
    }

    public int delete(Long itemId) {
        Cart cart = new Cart();
        cart.setItemId(itemId);
        cart.setUserId(UserThreadLocal.get().getId());
        return cartMapper.delete(cart);
    }

    public List<Cart> queryList(Long userId) {
        Example example = new Example(Cart.class);
        example.setOrderByClause("created DESC");

        example.createCriteria().andEqualTo("userId",userId);
        return cartMapper.selectByExample(example);
    }
}
