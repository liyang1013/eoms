package com.keboda.eomsback.invoice.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class VatInvoice {

    private String uuid;

    private String suffix;

    //发票类型
    private String invoiceType;

    //发票代码
    private String invoiceCode;

    // 发票号码
    private String invoiceNo;

    // 发票金额
    private BigDecimal amount;

    //开票日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date invoiceDate;

    // 校验码
    private String checkCode;
}
