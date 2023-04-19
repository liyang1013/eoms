package com.keboda.eomsback.system.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * operation
 * @author 
 */
@Data
public class Operation implements Serializable {
    /**
     * 作业编码
     */
    private String code;

    /**
     * 作业
     */
    private String name;

    /**
     * 具体描述
     */
    private String tips;

    private static final long serialVersionUID = 1L;
}