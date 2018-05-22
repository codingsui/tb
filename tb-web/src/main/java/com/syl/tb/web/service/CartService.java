package com.syl.tb.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syl.tb.common.service.ApiService;
import com.syl.tb.manage.pojo.Cart;
import com.syl.tb.manage.pojo.User;
import com.syl.tb.web.threadlocal.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER= new ObjectMapper();
    public List<Cart> queryList(){
        try {
            com.syl.tb.web.bean.User user = UserThreadLocal.get();
            String url = "http://cart.tb.cn/api/cart"+user.getId();
            String jsonData = apiService.doGet(url);
            if (StringUtils.isNotEmpty(jsonData)){
                return MAPPER.readValue(jsonData,MAPPER.getTypeFactory().constructCollectionType(List.class,Cart.class));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }
}
