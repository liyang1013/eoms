package com.keboda.eomsback.system.mapper;

import com.keboda.eomsback.system.pojo.HrmResource;

import java.util.List;

public interface HrmResourceMapper {
    List<HrmResource> selectByDepartCode(String code);
}
