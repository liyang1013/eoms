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

    /**
     * 供应商
     */
    private String pmc;

    /**
     * 料件
     */
    private String ima;

    /**
     * 中心
     */
    private String centre;

    /**
     * 有效性默认Y
     */
    private String acti = "Y";

    private Integer currentPage = 1;

    private Integer size = 20;

    private Integer total;
}
