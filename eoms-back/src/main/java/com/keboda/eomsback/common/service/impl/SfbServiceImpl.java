package com.keboda.eomsback.common.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.common.mapper.*;
import com.keboda.eomsback.common.pojo.*;
import com.keboda.eomsback.common.pojo.vo.SfbProcessVo;
import com.keboda.eomsback.common.pojo.vo.SfbSearchVo;
import com.keboda.eomsback.common.service.ISfbService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SfbServiceImpl  implements ISfbService {

    @Resource
    private SfbFileMapper sfbFileMapper;
    @Resource
    private SfpFileMapper sfpFileMapper;
    @Resource
    private ShbFileMapper shbFileMapper;
    @Resource
    private QcfFileMapper qcfFileMapper;
    @Resource
    private SfuFileMapper sfuFileMapper;

    /*
     查询工单单号,每次100条
     */
    @Override
    public List<SfbFile> sfb01Arr(String queryStr, String centre) {
        return sfbFileMapper.sfb01Arr(queryStr,centre);
    }

    @Override
    public SfbProcessVo sfbProcess(String sfb01, String centre) throws RuntimeException {

        SfbFile sfb = sfbFileMapper.selectDateByCode(sfb01,centre);
        if(sfb == null) throw new RuntimeException("查询不到对应工单信息");
        List<SfpFile> sfp = sfpFileMapper.selectDateByCode(sfb01,centre);
        List<ShbFile> shb = shbFileMapper.selectDateByCode(sfb01,centre);
        List<QcfFile> qcf = qcfFileMapper.selectDateByCode(sfb01,centre);
        List<SfuFile> sfu = sfuFileMapper.selectDateByCode(sfb01,centre);

        return SfbProcessVo.builder().sfb(sfb).sfp(sfp).shb(shb).qcf(qcf).sfu(sfu).build();
    }

    @Override
    public Page<SfbFile> sfbArr(SfbSearchVo searchVo) {
        return sfbFileMapper.sfbArr(searchVo);
    }
}
