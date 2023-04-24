package com.keboda.eomsback.stock.mapper;

import com.keboda.eomsback.stock.pojo.OblFile;
import com.keboda.eomsback.stock.pojo.OblFileKey;
import org.apache.ibatis.annotations.Param;

public interface OblFileMapper {

    int insertSelective(OblFile record);

    int updateByPrimaryKeySelective(OblFile record);

    OblFile selectByKey(@Param("centre") String centre, @Param("obl01") String obl01, @Param("obl02") String obl02);
}