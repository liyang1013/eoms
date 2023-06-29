package com.keboda.eomsback.system.mapper;

import com.keboda.eomsback.system.pojo.SmyFile;

public interface SmyFileMapper {

    int insertSelective(SmyFile record);

    int updateByPrimaryKeySelective(SmyFile record);
}