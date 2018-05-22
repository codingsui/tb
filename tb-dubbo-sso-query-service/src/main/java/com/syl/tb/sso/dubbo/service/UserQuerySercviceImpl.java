package com.syl.tb.sso.dubbo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sty.tb.dubbo.pojo.User;
import com.sty.tb.dubbo.service.UserQueryService;
import com.syl.tb.common.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("userQueryService")
public class UserQuerySercviceImpl implements UserQueryService {

    @Autowired
    private RedisService redisService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public List<User> queryAll() {
        return null;
    }

    @Override
    public User queryUserByToken(String token) {
        String key = "TOKEN_"+ token;
        String jsonData = redisService.get(key);
        if (StringUtils.isEmpty(jsonData)){
            return null;
        }
        redisService.expire(key,60*60*3);
        try {
            return MAPPER.readValue(jsonData,User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
