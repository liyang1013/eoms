package com.keboda.eomsback.flux.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.flux.mapper.DocAdjDetailsMapper;
import com.keboda.eomsback.flux.mapper.IdxAsrsReceiveMapper;
import com.keboda.eomsback.flux.mapper.InvLotAttMapper;
import com.keboda.eomsback.flux.mapper.InvLotLocIdMapper;
import com.keboda.eomsback.flux.pojo.InvLotLocId;
import com.keboda.eomsback.flux.service.IInvLotLocIdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
@DS("flux")
public class InvLotLocIdServiceImpl implements IInvLotLocIdService {

    @Resource
    private InvLotLocIdMapper invLotLocIdMapper;
    @Resource
    private IdxAsrsReceiveMapper idxAsrsReceiveMapper;
    @Resource
    private DocAdjDetailsMapper docAdjDetailsMapper;
    @Resource
    private InvLotAttMapper invLotAttMapper;


    @Override
    public Page<InvLotLocId> invLotLocIdListPageHelper(SearchVo searchVo) {
        return invLotLocIdMapper.invLotLocIdListPageHelper(searchVo);
    }

    @Override
    @Transactional
    public void lock(List<InvLotLocId> invLotLocIdList, String status) {
        for (InvLotLocId invLotLocId : invLotLocIdList) {

            //检查数据库状态
            InvLotLocId inv = invLotLocIdMapper.selectByKey(invLotLocId.getLocationid(),invLotLocId.getLotnum(),invLotLocId.getTraceid());
            if(inv == null) throw new RuntimeException("对应的库存记录不存在");
            if(status.equals(inv.getLotatt08())) throw new RuntimeException("目标状态与现在状态冲突");

            //检查分配数量是否大于0
            if(inv.getAsqtyavailed().compareTo(inv.getQty()) != 0  ) throw new RuntimeException("目标记录已被分配任务，不允许更改状态");

            //检查是否有活动的拣货任务
            Integer count = idxAsrsReceiveMapper.selectAsrsCount(inv.getLocationid(),inv.getTraceid());
            if(count != 0) throw new RuntimeException("目标记录有未完成的拣选任务，不能更改状态");

            //查找调整单，状态为00，调整完后数量是否大于锁定数量
            BigDecimal qty = docAdjDetailsMapper.checkQty(inv.getLocationid(),inv.getLotnum(),inv.getTraceid());
            if(qty.compareTo(BigDecimal.ZERO) > 0) throw new RuntimeException("有尚未完成的库存调整单，且调整后数量少于原数量");

            //更改批次状态
            invLotAttMapper.lock(inv.getLotnum(),status);
        }
    }
}
