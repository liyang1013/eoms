package com.keboda.eomsback.report.controller;

import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.report.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private IReportService iReportService;

    @GetMapping("/jobData")
    public BaseResult jobData(String floor){
        return BaseResult.success(iReportService.jobData(floor));
    }
}
