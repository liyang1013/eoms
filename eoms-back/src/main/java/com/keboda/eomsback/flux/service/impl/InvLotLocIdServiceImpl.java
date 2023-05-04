package com.keboda.eomsback.flux.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.flux.mapper.InvLotLocIdMapper;
import com.keboda.eomsback.flux.pojo.InvLotLocId;
import com.keboda.eomsback.flux.service.IInvLotLocIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@DS("flux")
public class InvLotLocIdServiceImpl implements IInvLotLocIdService {

    @Resource
    private InvLotLocIdMapper invLotLocIdMapper;


    @Override
    public Page<InvLotLocId> invLotLocIdListPageHelper(SearchVo searchVo) {
        return invLotLocIdMapper.invLotLocIdListPageHelper(searchVo);
    }
}
