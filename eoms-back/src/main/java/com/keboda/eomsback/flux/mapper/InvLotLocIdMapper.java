package com.keboda.eomsback.flux.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.flux.pojo.InvLotLocId;
import org.apache.ibatis.annotations.Param;

public interface InvLotLocIdMapper {

    Page<InvLotLocId> invLotLocIdListPageHelper(SearchVo searchVo);

    InvLotLocId selectByKey(@Param("locationid") String locationid, @Param("lotnum") String lotnum, @Param("traceid") String traceid);

    void updateQty(InvLotLocId invLotLocId);

    void insertSelective(InvLotLocId inv);
}
