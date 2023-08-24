package com.keboda.eomsback.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 查询通用Vo，带分页
 */
@Data
public class SearchVo {

    /**
     * 单号
     */
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
     * 用户
     */
    private String zx;

    /**
     * 部门
     */
    private String gem;

    /**
     * 程序代码
     */
    private String gaz;
    /**
     * 程序目的
     */
    private String gaz04;

    /**
     * 码别
     */
    private String azf;

    private String azf02;

    private String azf09;

    /**
     * 单位
     */
    private String gfe;

    /**
     * 项目大类
     */
    private String pja;

    /**
     * 项目
     */
    private String pjb;

    /**
     * 中心
     */
    private String centre;

    private Date startdate;

    private Date enddate;

    /**
     * 有效性默认Y
     */
    private String acti = "Y";

    /**
     * 审核状态
     */
    private String conf = "Y";

    private Boolean flag;

    private Integer currentPage = 1;

    private Integer size = 20;

    private Integer total;
}
