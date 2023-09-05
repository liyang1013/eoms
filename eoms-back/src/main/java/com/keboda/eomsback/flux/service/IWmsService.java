package com.keboda.eomsback.flux.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.flux.pojo.InvLotLocId;
import com.keboda.eomsback.flux.pojo.RobotsStatusFile;

import java.util.List;

public interface IWmsService {

    RobotsStatusFile selectByKey(Integer id);

    void insertByKeySelective(RobotsStatusFile robotsStatusFile);

    void updateByKeySelective(RobotsStatusFile robotsStatusFile);

    Page<InvLotLocId> invLotLocIdListPageHelper(SearchVo searchVo);

    void lock(List<InvLotLocId> invLotLocIdList, String status);
}
