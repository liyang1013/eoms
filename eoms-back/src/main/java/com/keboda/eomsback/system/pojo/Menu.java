package com.keboda.eomsback.system.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * menu
 * @author 
 */
@Data
public class Menu implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 父菜单id
     */
    private Integer pid;

    /**
     * 地址
     */
    private String path;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;


    private String component;


    private List<Menu> children;

    private static final long serialVersionUID = 1L;
}