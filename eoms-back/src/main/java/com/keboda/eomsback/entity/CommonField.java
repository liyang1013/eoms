package com.keboda.eomsback.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CommonField {

    /**
     * 中心
     */
    private String centre;

    /**
     * 仓库
     */
    private String imd02;

    /**
     * 料件名称
     */
    private String ima02;

    /**
     * 料件规格
     */
    private String ima021;

    /**
     * 采购单位
     */
    private String ima44;


    /**
     * 供应商
     */
    private String pmc03;

    /**
     * 人员
     */
    private String gen02;

    /**
     * 单别
     */
    private String smydesc;

    private String gee04;

    private String gee05;


    /**
     * 自定义flux出库数量
     */
    private BigDecimal fickqty;

}
