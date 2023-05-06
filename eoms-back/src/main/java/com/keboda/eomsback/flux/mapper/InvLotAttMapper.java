package com.keboda.eomsback.flux.mapper;

import org.apache.ibatis.annotations.Param;

public interface InvLotAttMapper {
    void lock(@Param("lotnum") String lotnum, @Param("status") String status);
}
