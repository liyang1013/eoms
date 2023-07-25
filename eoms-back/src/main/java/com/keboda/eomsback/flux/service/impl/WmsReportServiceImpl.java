package com.keboda.eomsback.flux.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.flux.mapper.WmsReportMapper;
import com.keboda.eomsback.flux.service.IWmsReportService;
import com.keboda.eomsback.report.pojo.JobDataVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@DS("fluxWms")
public class WmsReportServiceImpl implements IWmsReportService {

    @Resource
    private WmsReportMapper wmsReportMapper;

    @Override
    public List<JobDataVo> jobData(String floor) {
        if(floor.contains("IN"))
            return  wmsReportMapper.jobDataIN();
        else
            return wmsReportMapper.jobDataOUT(floor);
    }
}
