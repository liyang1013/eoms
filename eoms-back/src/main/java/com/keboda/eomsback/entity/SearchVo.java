package com.keboda.eomsback.entity;

import lombok.Data;

import java.util.Date;

/**
 * 查询通用Vo，带分页
 */
@Data
public class SearchVo {

    private String code;

    private String code_1;

    private String code_2;

    private String code_3;

    private String pmc;

    private String centre;

    private Date startdate;

    private Date enddate;

    private Integer currentPage;

    private Integer size;

    private Integer total;
}
