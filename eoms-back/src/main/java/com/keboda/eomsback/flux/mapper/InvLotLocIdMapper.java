package com.keboda.eomsback.flux.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.flux.pojo.InvLotLocId;

public interface InvLotLocIdMapper {

    Page<InvLotLocId> invLotLocIdListPageHelper(SearchVo searchVo);
}
