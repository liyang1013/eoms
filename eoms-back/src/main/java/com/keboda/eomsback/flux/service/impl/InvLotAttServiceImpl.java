package com.keboda.eomsback.flux.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.flux.mapper.InvLotAttMapper;
import com.keboda.eomsback.flux.pojo.InvLotAtt;
import com.keboda.eomsback.flux.service.IInvLotAttService;
import com.keboda.eomsback.system.service.impl.ITcIdsequenceFileService;
import com.keboda.eomsback.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@DS("flux wms")
public class InvLotAttServiceImpl implements IInvLotAttService {

    @Resource
    private InvLotAttMapper invLotAttMapper;
    @Resource
    ITcIdsequenceFileService iTcIdsequenceFileService;

    @Override
    @Transactional
    public Map<String, InvLotAtt> selectMapByKeyLotnumStatus(String lotnum, String status) {

        HashMap<String, InvLotAtt> map = new HashMap<>();
        InvLotAtt origin = invLotAttMapper.selectByKey(lotnum);
        map.put(origin.getLotatt08(),origin);
        origin.setLotatt08(status);
        InvLotAtt total = invLotAttMapper.selectOtherStatus(origin);

        if(total == null){
            String code = iTcIdsequenceFileService.getCode("lotnum");//得到新批次

            total = origin;
            total.setLotnum(code);
            total.setCurrentversion(100);
            total.setOprseqflag(DateUtils.format(new Date(),DateUtils.FORMAT_NO_MILLISECOND) + "RA127000000001[KEBODA]");
            total.setAddwho("KEBODA");
            total.setAddtime(new Date());
            total.setEditwho(null);
            total.setEdittime(null);
            invLotAttMapper.insertSelective(total);
        }
        map.put(status,total);
        return map;
    }
}
