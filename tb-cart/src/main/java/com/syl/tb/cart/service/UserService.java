package com.syl.tb.cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syl.tb.cart.pojo.User;
import com.syl.tb.common.service.ApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public User queryByToken(String token){
        try {
            String url = "http://localhost:8083/user/"+token;
            String jsobData = apiService.doGet(url);
            System.out.println("json--------"+jsobData);
            if (!StringUtils.isEmpty(jsobData)){
                return MAPPER.readValue(jsobData,User.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
