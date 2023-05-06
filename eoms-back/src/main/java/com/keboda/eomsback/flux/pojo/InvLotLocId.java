package com.keboda.eomsback.flux.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvLotLocId implements Serializable {

    private String warehouseid;

    private String customerid;

    private String locationid;

    private String traceid;

    private String sku;

    private String lotnum;

    private String skudescr1;

    private BigDecimal qtyallocated;

    private String packdescr;

    private String skudescr2;

    private String packid;

    private String freightclass;

    private String packuom;

    private BigDecimal qty;

    private BigDecimal cubic;

    private BigDecimal grossweight;

    private BigDecimal qtyonhold;

    private BigDecimal asqtyavailed;

    private String lotatt04;

    private String lotatt05;

    private String lotatt06;

    private String lotatt07;

    private String lotatt08;

    private static final long serialVersionUID = 1L;

}
