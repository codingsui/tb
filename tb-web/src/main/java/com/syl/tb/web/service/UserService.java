package com.syl.tb.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syl.tb.common.service.ApiService;
import com.syl.tb.web.bean.User;
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
            if (!StringUtils.isEmpty(jsobData)){
                return MAPPER.readValue(jsobData,User.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
