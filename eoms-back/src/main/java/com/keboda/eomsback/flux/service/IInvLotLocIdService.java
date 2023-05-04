package com.keboda.eomsback.flux.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.flux.pojo.InvLotLocId;

public interface IInvLotLocIdService {
    Page<InvLotLocId> invLotLocIdListPageHelper(SearchVo searchVo);
}
