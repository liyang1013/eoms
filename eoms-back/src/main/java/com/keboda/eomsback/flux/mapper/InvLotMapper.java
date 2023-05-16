package com.keboda.eomsback.flux.mapper;

import com.keboda.eomsback.flux.pojo.InvLot;
import org.apache.ibatis.annotations.Param;

public interface InvLotMapper {
    void updateQty(InvLot invLot);

    void insertSelective(InvLot invLot);

    InvLot selectByKey(@Param("lotnum") String lotnum);
}
