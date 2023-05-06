package com.keboda.eomsback.flux.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.flux.pojo.InvLotLocId;

import java.util.List;

public interface IInvLotLocIdService {
    Page<InvLotLocId> invLotLocIdListPageHelper(SearchVo searchVo);

    void lock(List<InvLotLocId> invLotLocIdList, String status);
}
