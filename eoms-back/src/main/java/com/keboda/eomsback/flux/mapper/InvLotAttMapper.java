package com.keboda.eomsback.flux.mapper;

import com.keboda.eomsback.flux.pojo.InvLotAtt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvLotAttMapper {
    void insertSelective(InvLotAtt zc);

    InvLotAtt selectByKey(@Param("lotnum") String lotnum);

    InvLotAtt selectOtherStatus(InvLotAtt origin);
}
