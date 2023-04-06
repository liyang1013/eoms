package com.keboda.eomsback.base.controller;

import com.keboda.eomsback.base.pojo.User;
import com.keboda.eomsback.base.service.IUserService;
import com.keboda.eomsback.common.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/login")
    public BaseResult login(@RequestBody User user){
        return BaseResult.success(iUserService.login(user));
    }
}
