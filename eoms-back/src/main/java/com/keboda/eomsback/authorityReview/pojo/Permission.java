package com.keboda.eomsback.authorityReview.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 权限表
 * permission
 */
@Data
public class Permission implements Serializable {
    /**
     * uuid
     */
    private Integer uuid;

    /**
     * 权限编码
     */
    private String permissioncode;

    /**
     * 权限名
     */
    private String permissionname;

    private static final long serialVersionUID = 1L;
}