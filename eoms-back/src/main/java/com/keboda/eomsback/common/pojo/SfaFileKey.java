package com.keboda.eomsback.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 工单主键
 * SFA_FILE
 * @author
 */
@Data
public class SfaFileKey implements Serializable {
    private String sfa01;

    private String sfa03;

    private String sfa08;

    private String sfa12;

    private String sfa27;

    private String sfa012;

    private Integer sfa013;

    private static final long serialVersionUID = 1L;
}
