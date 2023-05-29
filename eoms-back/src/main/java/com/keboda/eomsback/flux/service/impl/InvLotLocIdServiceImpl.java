package com.keboda.eomsback.flux.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.flux.mapper.DocAdjDetailsMapper;
import com.keboda.eomsback.flux.mapper.IdxAsrsReceiveMapper;
import com.keboda.eomsback.flux.mapper.InvLotLocIdMapper;
import com.keboda.eomsback.flux.pojo.InvLotAtt;
import com.keboda.eomsback.flux.pojo.InvLotLocId;
import com.keboda.eomsback.flux.service.IInvLotAttService;
import com.keboda.eomsback.flux.service.IInvLotLocIdService;
import com.keboda.eomsback.flux.service.IInvLotService;
import com.keboda.eomsback.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@DS("flux wms")
public class InvLotLocIdServiceImpl implements IInvLotLocIdService {

    @Resource
    private InvLotLocIdMapper invLotLocIdMapper;
    @Resource
    private IdxAsrsReceiveMapper idxAsrsReceiveMapper;
    @Resource
    private DocAdjDetailsMapper docAdjDetailsMapper;
    @Resource
    private IInvLotAttService iInvLotAttService;
    @Resource
    private IInvLotService invLotService;

    @Override
    public Page<InvLotLocId> invLotLocIdListPageHelper(SearchVo searchVo) {
        return invLotLocIdMapper.invLotLocIdListPageHelper(searchVo);
    }

    @Override
    @Transactional
    public void lock(List<InvLotLocId> invLotLocIdList, String status) {
        for (InvLotLocId invLotLocId : invLotLocIdList) {

            //检查数据库记录及状态
            InvLotLocId inv = invLotLocIdMapper.selectByKey(invLotLocId.getLocationid(), invLotLocId.getLotnum(), invLotLocId.getTraceid());
            if (inv == null) throw new RuntimeException("栈板：" + invLotLocId.getTraceid() + "对应的库存记录不存在");
            if (status.equals(inv.getLotatt08()))
                throw new RuntimeException("栈板：" + invLotLocId.getTraceid() + "目标状态与源状态冲突");

            BigDecimal oneWeight = inv.getGrossweight().divide(inv.getQty(), RoundingMode.HALF_UP);//一份的重量

            //检查分配数量
            if (inv.getAsqtyavailed().compareTo(invLotLocId.getIqty()) < 0)
                throw new RuntimeException("栈板：" + invLotLocId.getTraceid() + "可用数量不足");

            //检查是否有活动的拣货任务
            Integer count = idxAsrsReceiveMapper.selectAsrsCount(inv.getLocationid(), inv.getTraceid());
            if (count != 0)
                throw new RuntimeException("栈板：" + invLotLocId.getTraceid() + "目标记录有未完成的拣选任务，不能更改状态");

            //查找调整单，状态为00，调整完后数量是否大于锁定数量
            BigDecimal qty = docAdjDetailsMapper.checkQty(inv.getLocationid(), inv.getLotnum(), inv.getTraceid());
            if (qty != null && qty.compareTo(invLotLocId.getIqty()) < 0)
                throw new RuntimeException("栈板：" + invLotLocId.getTraceid() + "有尚未完成的库存调整任务，且调整后数量少于" + status + "数量");

            //寻找相反状态的记录，没有就新增并返回
            Map<String,InvLotAtt> map = iInvLotAttService.selectMapByKeyLotnumStatus(invLotLocId.getLotnum(),status);

            //更新inv_lot_loc_id源记录数量
            this.mergeQty(inv,BigDecimal.ZERO.subtract(invLotLocId.getIqty()),oneWeight);

            //更新目标数量
            inv.setLotnum(map.get(status).getLotnum());

            this.mergeQty(inv,invLotLocId.getIqty(),oneWeight);

            //更新inv_lot记录
            String[] arr = {map.get("ZC").getLotnum(),map.get("SD").getLotnum()};
            invLotService.updateQtyBykey(arr);
        }
    }

    //检查对象是否存在并更新数量，毛重，版本号
    private void mergeQty(InvLotLocId invLotLocId,BigDecimal qty,BigDecimal oneWeight){

        InvLotLocId inv = invLotLocIdMapper.selectByKey(invLotLocId.getLocationid(),invLotLocId.getLotnum(),invLotLocId.getTraceid());
        if(inv == null){
            inv = invLotLocId;
            inv.setQty(BigDecimal.ZERO);
            inv.setQtyallocated(BigDecimal.ZERO);
            inv.setQtyrpin(BigDecimal.ZERO);
            inv.setQtyrpout(BigDecimal.ZERO);
            inv.setQtymvin(BigDecimal.ZERO);
            inv.setQtymvout(BigDecimal.ZERO);
            inv.setQtyonhold(BigDecimal.ZERO);
            inv.setGrossweight(BigDecimal.ZERO);
            inv.setQtypa(BigDecimal.ZERO);
            inv.setCurrentversion(100);
            inv.setOprseqflag(DateUtils.format(new Date(),DateUtils.FORMAT_NO_MILLISECOND) + "RA127000000001[KEBODA]");
            inv.setAddwho("KEBODA");
            inv.setAddtime(new Date());
            inv.setInloctime(new Date());
            invLotLocIdMapper.insertSelective(inv);
        }

        inv.setQty(inv.getQty().add( qty ) );
        inv.setGrossweight(inv.getQty().multiply(oneWeight).setScale(3, RoundingMode.HALF_UP));
        inv.setEdittime(new Date());
        inv.setEditwho("KEBODA");
        invLotLocIdMapper.updateQty(inv);

    }
}
