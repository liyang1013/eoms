package com.keboda.eomsback.purchase.mapper;

import com.keboda.eomsback.purchase.pojo.PmiFile;

public interface PmiFileMapper {

    int insertSelective(PmiFile record);

    int updateByPrimaryKeySelective(PmiFile record);
}