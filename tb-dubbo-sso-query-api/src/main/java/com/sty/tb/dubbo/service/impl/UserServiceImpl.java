package com.sty.tb.dubbo.service.impl;

import com.sty.tb.dubbo.pojo.User;
import com.sty.tb.dubbo.service.UserQueryService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserQueryService {
    @Override
    public List<User> queryAll() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(Long.valueOf(i+1));
            user.setPassword("123456");
            user.setUsername("username_"+i);
            list.add(user);
        }
        return list;
    }

    @Override
    public User queryUserByToken(String token) {
        return null;
    }
}
