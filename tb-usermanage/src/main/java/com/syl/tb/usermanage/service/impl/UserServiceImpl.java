package com.syl.tb.usermanage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syl.tb.usermanage.dao.UserMapper;
import com.syl.tb.usermanage.dto.Result;
import com.syl.tb.usermanage.pojo.User;
import com.syl.tb.usermanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public Result queryUserList(Integer page, Integer rows) {
        //设置分页参数
        PageHelper.startPage(page,rows);
        List<User> users = (List<User>) userMapper.selectList();
        PageInfo<User> pageInfo = new PageInfo(users);

        return new Result(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public User queryUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean saveUser(User user) {
        return userMapper.insertSelective(user) == 1;
    }

    @Override
    public boolean updateUser(User user) {

        return userMapper.updateByPrimaryKeySelective(user) == 1;
    }

    @Override
    public boolean deleteUser(Long id) {
        return userMapper.deleteByPrimaryKey(id) == 1;
    }
}
