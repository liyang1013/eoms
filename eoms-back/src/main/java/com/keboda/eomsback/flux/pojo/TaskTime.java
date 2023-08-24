package com.keboda.eomsback.flux.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TaskTime {

    private String carid;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ddate;

    private String taskid;

    private String tasktype;

    private String wmstasktype;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date startdate;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date enddate;

    private String tasktime;
}
