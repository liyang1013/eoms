package com.keboda.eomsback.stock.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * INB_FILE
 * @author 
 */
@Data
public class InbFileKey implements Serializable {
    private String inb01;

    private Integer inb03;

    private static final long serialVersionUID = 1L;
}