package com.keboda.eomsback.system.service;

import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.system.pojo.Menu;

import java.util.List;

public interface IMenuService {

    List<Menu> getAllMenu();

    List<Menu> getChildrenMenu(Integer pid);
}
