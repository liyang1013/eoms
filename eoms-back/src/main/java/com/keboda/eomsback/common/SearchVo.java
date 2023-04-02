package com.keboda.eomsback.common;

import lombok.Data;

/**
 * 查询通用Vo，带分页
 */
@Data
public class SearchVo {

    private String code_1;

    private String code_2;

    private String pmc;

    private String centre;

    private Integer currentPage;

    private Integer size;

    private Integer total;
}
