package com.keboda.eomsback.report.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class JobDataVo implements Serializable {

    private String centre;

    private String code;

    private String cinvcode;

    private String cinvname;

    private String ddate;

    private Double qty;

    private String status;

    private String palletstatus;

    private String traceid;
}
