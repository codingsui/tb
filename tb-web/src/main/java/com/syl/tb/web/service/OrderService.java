package com.syl.tb.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syl.tb.common.bean.HttpResult;
import com.syl.tb.common.bean.Result;
import com.syl.tb.common.service.ApiService;
import com.syl.tb.web.bean.Order;
import com.syl.tb.web.bean.User;
import com.syl.tb.web.threadlocal.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Service
public class OrderService {
    @Autowired
    private ApiService apiService;
    @Value("${TB_ORDER_URL}")
    private String TB_ORDER_URL;


    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String submit(Order order) {
        try {
            String url = TB_ORDER_URL + "/order/create";
            User user = UserThreadLocal.get();
            order.setUserId(user.getId());
            order.setBuyerNick(user.getUsername());
            HttpResult result = apiService.doPostJson(url,MAPPER.writeValueAsString(order));
            if (result.getCode().intValue() == 200){
                String body = result.getBody();
                JsonNode jsonNode = MAPPER.readTree(body);
                if (jsonNode.get("status").asInt() == 200){
                    return jsonNode.get("data").asText();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public Order queryByOrderId(String id) {
        String url = TB_ORDER_URL + "/order/query/"+id;
        String jsonData = null;
        try {
            jsonData = apiService.doGet(url);
            if (StringUtils.isNotEmpty(jsonData)){
                return MAPPER.readValue(jsonData,Order.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
