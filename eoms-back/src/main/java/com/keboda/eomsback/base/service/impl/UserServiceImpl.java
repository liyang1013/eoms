package com.keboda.eomsback.base.service.impl;

import com.keboda.eomsback.base.pojo.User;
import com.keboda.eomsback.base.service.IUserService;
import com.keboda.eomsback.utils.JwtTokenUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Override
    public User login(User user) {
        if(user.getUsername() == null || user.getUsername().trim().isEmpty()){
            throw new RuntimeException("用户名不能为空");
        }
        if(user.getPassword() == null || user.getPassword().trim().isEmpty()){
            throw new RuntimeException("密码不能为空");
        }
        user.setToken(JwtTokenUtils.getToken(user.getUsername(),user.getPassword()));
        user.setPassword(null);
        return user;
    }
}
