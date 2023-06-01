package com.keboda.eomsback.flux.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.flux.mapper.InvLotMapper;
import com.keboda.eomsback.flux.pojo.InvLot;
import com.keboda.eomsback.flux.service.IInvLotService;
import com.keboda.eomsback.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
@DS("fluxWms")
public class InvLotServiceImpl implements IInvLotService {

    @Resource
    private InvLotMapper invLotMapper;

    @Override
    @Transactional
    public void updateQtyBykey(String[] arr) {
        for (String lotnum : arr) {

            InvLot invLot = invLotMapper.selectByKey(lotnum);
            if(invLot == null) {

                invLot = new InvLot();
                invLot.setOrganizationid("*");
                invLot.setWarehouseid("*");
                invLot.setCustomerid("*");
                invLot.setSku("*");
                invLot.setLotnum(lotnum);
                invLot.setQty(BigDecimal.ZERO);
                invLot.setQtyallocated(BigDecimal.ZERO);
                invLot.setQtypreallocated(BigDecimal.ZERO);
                invLot.setQtyonhold(BigDecimal.ZERO);
                invLot.setGrossweight(BigDecimal.ZERO);
                invLot.setNetweight(BigDecimal.ZERO);
                invLot.setCubic(BigDecimal.ZERO);
                invLot.setPrice(BigDecimal.ZERO);
                invLot.setCurrentversion(100);
                invLot.setOprseqflag(DateUtils.format(new Date(),DateUtils.FORMAT_NO_MILLISECOND) + "RA127000000001[KEBODA]");
                invLot.setAddwho("KEBODA");
                invLot.setAddtime(new Date());
                invLotMapper.insertSelective(invLot);
            }

            invLot.setEdittime(new Date());
            invLot.setEditwho("KEBODA");
            invLotMapper.updateQty(invLot);
        }
    }
}
