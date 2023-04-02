package com.keboda.eomsback.purchase.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * PMN_FILE
 * @author 
 */
@Data
public class PmnFileKey implements Serializable {
    private String pmn01;

    private Integer pmn02;

    private static final long serialVersionUID = 1L;
}