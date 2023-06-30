package com.keboda.eomsback.flux.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.flux.mapper.EchartsMapper;
import com.keboda.eomsback.flux.pojo.ChartsCommonVo;
import com.keboda.eomsback.flux.pojo.WcsTaskTypeTime;
import com.keboda.eomsback.flux.service.IEchartsWcsService;
import com.keboda.eomsback.flux.service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@DS("fluxWcs")
public class EchartsWcsServiceImpl implements IEchartsWcsService {

    @Resource
    private EchartsMapper echartsMapper;

    @Autowired
    private ILocationService iLocationService;


    @Override
    public List<ChartsCommonVo> wcsTaskTypeTime() {
        List<WcsTaskTypeTime> arr = iLocationService.aa();
        for (WcsTaskTypeTime typeTime : arr) {

        }
        return null;
    }
}
