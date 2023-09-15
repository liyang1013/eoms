package com.keboda.eomsback.produce.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * SFS_FILE
 */
@Data
public class SfsFileKey implements Serializable {
    private String sfs01;

    private Integer sfs02;

    private static final long serialVersionUID = 1L;
}