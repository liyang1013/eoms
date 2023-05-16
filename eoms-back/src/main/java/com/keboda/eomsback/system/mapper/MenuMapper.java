package com.keboda.eomsback.system.mapper;

import com.keboda.eomsback.system.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {

    List<Menu> getAllMenu();
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Menu record);

    List<Menu> getMenuChildren(@Param("id") Integer pid);

    int updateByPrimaryKeySelective(Menu record);

}