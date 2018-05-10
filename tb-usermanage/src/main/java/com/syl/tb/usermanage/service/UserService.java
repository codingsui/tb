package com.syl.tb.usermanage.service;

import com.syl.tb.usermanage.dto.Result;
import com.syl.tb.usermanage.pojo.User;

public interface UserService {
    Result queryUserList(Integer page,Integer rows);

    User queryUserById(Long id);

    boolean saveUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(Long id);
}
