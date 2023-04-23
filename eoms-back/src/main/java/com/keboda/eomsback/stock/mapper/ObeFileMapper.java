package com.keboda.eomsback.stock.mapper;

import com.keboda.eomsback.stock.pojo.ObeFile;

public interface ObeFileMapper {
    int insertSelective(ObeFile record);
    int updateByPrimaryKeySelective(ObeFile record);

}