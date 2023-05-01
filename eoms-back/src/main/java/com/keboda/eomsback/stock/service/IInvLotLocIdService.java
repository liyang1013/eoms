package com.keboda.eomsback.stock.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.InvLotLocId;

public interface IInvLotLocIdService {
    Page<InvLotLocId> invLotLocIdListPageHelper(SearchVo searchVo);
}
