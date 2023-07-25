package com.keboda.eomsback.flux.mapper;

import com.keboda.eomsback.report.pojo.JobDataVo;

import java.util.List;

public interface WmsReportMapper{

    List<JobDataVo> jobDataIN();

    List<JobDataVo> jobDataOUT(String floor);
}
