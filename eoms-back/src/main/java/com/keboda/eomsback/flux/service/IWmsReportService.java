package com.keboda.eomsback.flux.service;

import com.keboda.eomsback.report.pojo.JobDataVo;

import java.util.List;

public interface IWmsReportService {
    List<JobDataVo> jobData(String floor);
}
