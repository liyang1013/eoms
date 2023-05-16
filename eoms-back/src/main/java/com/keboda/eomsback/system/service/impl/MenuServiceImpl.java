package com.keboda.eomsback.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.system.mapper.MenuMapper;
import com.keboda.eomsback.system.pojo.Menu;
import com.keboda.eomsback.system.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@DS("slave")
public class MenuServiceImpl implements IMenuService {

    @Resource
    private MenuMapper menuMapper;


    @Override
    public List<Menu> getAllMenu() {
        return menuMapper.getAllMenu();
    }

    @Override
    public List<Menu> getChildrenMenu(Integer pid) {
        return menuMapper.getMenuChildren(pid);
    }
}
