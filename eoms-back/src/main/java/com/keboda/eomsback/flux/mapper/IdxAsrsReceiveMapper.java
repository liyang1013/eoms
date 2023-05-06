package com.keboda.eomsback.flux.mapper;

import org.apache.ibatis.annotations.Param;

public interface IdxAsrsReceiveMapper {
    Integer selectAsrsCount(@Param("slocation") String slocation, @Param("palletid") String palletid);
}
