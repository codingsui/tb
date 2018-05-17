package com.syl.tb.sso.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syl.tb.common.service.RedisService;
import com.syl.tb.sso.mapper.UserMapper;
import com.syl.tb.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Boolean check(String param, Integer type) {
        User user = new User();
        switch (type){
            case 1:
                user.setUsername(param);
                break;
            case 2:
                user.setPhone(param);
                break;
            case 3:
                user.setEmail(param);
                break;
            default:
                return null;
        }
        User user1 = userMapper.selectOne(user);

        return user1 == null ;
    }

    public Boolean doRegister(User user) {
        user.setId(null);
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return  userMapper.insert(user) == 1;
    }

    public String doLogin(String username, String password) throws JsonProcessingException {
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);
        if (null == record){
            return null;
        }
        if (!StringUtils.equals(DigestUtils.md5Hex(password),user.getPassword())){
            return null;
        }
        String token = DigestUtils.md5Hex(username+System.currentTimeMillis());
        redisService.set("TOKEN_"+token,MAPPER.writeValueAsString(user),60*30);
        return token;
    }

    public User quertUserByToken(String token) {
        String key = "TOKEN_"+token;
        String jsonData = redisService.get(key);
        if (StringUtils.isEmpty(jsonData))
            return null;
        this.redisService.expire(key,60*30);
        try {
            return MAPPER.readValue(jsonData,User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
