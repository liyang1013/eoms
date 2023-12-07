package com.keboda.eomsback.sale.mapper;

import com.keboda.eomsback.sale.pojo.OccFile;

public interface OccFileMapper {

    int insertSelective(OccFile record);

    int updateByPrimaryKeySelective(OccFile record);
}