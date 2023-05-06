package com.keboda.eomsback.flux.mapper;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface DocAdjDetailsMapper {
    BigDecimal checkQty(@Param("locationid") String locationid, @Param("lotnum") String lotnum, @Param("traceid") String traceid);
}
