package com.keboda.eomsback.purchase.mapper;

import com.keboda.eomsback.purchase.pojo.PmmFile;

public interface PmmFileMapper {

    int insertSelective(PmmFile record);

    int updateByPrimaryKeySelective(PmmFile record);

}