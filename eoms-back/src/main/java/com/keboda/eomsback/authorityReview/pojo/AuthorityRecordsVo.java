package com.keboda.eomsback.authorityReview.pojo;

import lombok.Data;

@Data
public class AuthorityRecordsVo {

    private Integer uuid;

    private Integer year;

    private String positioncode;
    /**
     * 职位名
     */
    private String positionname;

    private String permissioncode;

    /**
     * 权限名
     */
    private String permissionname;

    private String containAmount;

    private String color;

}
