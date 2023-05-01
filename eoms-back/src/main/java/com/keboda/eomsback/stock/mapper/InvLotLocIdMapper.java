package com.keboda.eomsback.stock.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.InvLotLocId;

public interface InvLotLocIdMapper {

    Page<InvLotLocId> invLotLocIdListPageHelper(SearchVo searchVo);
}
