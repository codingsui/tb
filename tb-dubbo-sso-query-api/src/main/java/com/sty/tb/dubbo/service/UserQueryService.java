package com.sty.tb.dubbo.service;

import com.sty.tb.dubbo.pojo.User;

import java.util.List;

public interface UserQueryService {
    public List<User> queryAll();
    public User queryUserByToken(String token);
}
