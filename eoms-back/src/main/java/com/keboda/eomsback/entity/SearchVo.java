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

    private String code_4;

    private String code_5;

    private String pmc;

    private String centre;

    /**
     * 有效性默认Y
     */
    private String acti = "Y";

    private Date startdate;

    private Date enddate;

    private Integer currentPage = 1;

    private Integer size = 20;

    private Integer total;
}
