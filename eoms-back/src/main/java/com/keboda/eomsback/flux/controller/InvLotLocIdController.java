package com.keboda.eomsback.flux.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.flux.pojo.InvLotLocId;
import com.keboda.eomsback.flux.service.IInvLotLocIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invLotLocId")
public class InvLotLocIdController {

    @Autowired
    private IInvLotLocIdService iInvLotLocIdService;

    @RequestMapping("/invLotLocIdListPageHelper")
    public BaseResult invLotLocIdListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<InvLotLocId> page = iInvLotLocIdService.invLotLocIdListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }
}
