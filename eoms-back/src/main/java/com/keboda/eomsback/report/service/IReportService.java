package com.keboda.eomsback.report.service;

import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.report.pojo.JobDataVo;

import java.util.List;

public interface IReportService {
    List<JobDataVo> jobData(String floor);
}

