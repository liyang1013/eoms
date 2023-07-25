package com.keboda.eomsback.report.service.impl;

import com.keboda.eomsback.flux.service.IWmsReportService;
import com.keboda.eomsback.report.pojo.JobDataVo;
import com.keboda.eomsback.report.service.IReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    @Resource
    private IWmsReportService iWmsReportService;

    @Override
    public List<JobDataVo> jobData(String floor) {
        return iWmsReportService.jobData(floor);
    }
}
