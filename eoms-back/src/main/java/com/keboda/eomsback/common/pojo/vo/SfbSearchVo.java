package com.keboda.eomsback.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SfbSearchVo {

    private String centre;

    private String sfb01;

    private String startdate;

    private String enddate;

    private String sfb05;

    private String sfp01;

    private String sfu01;

    private Integer currentPage;

    private Integer size;

    private Integer total;
}
