package com.keboda.eomsback.entity;

import lombok.Data;

import java.util.Date;

/**
 * 查询通用Vo，带分页
 */
@Data
public class SearchVo {

    private String code;

    /**
     * 供应商
     */
    private String pmc;

    /**
     * 料件
     */
    private String ima;


    /**
     * 仓库
     */
    private String imd;

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
