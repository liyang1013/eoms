package com.keboda.eomsback.authorityReview.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 年度职位表
 * position
 */
@Data
public class Position  implements Serializable {

    private Integer uuid;

    private Integer year;

    private String positioncode;
    /**
     * 职位名
     */
    private String positionname;

    private static final long serialVersionUID = 1L;
}