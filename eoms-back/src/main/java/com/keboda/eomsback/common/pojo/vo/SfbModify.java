package com.keboda.eomsback.common.pojo.vo;

import com.keboda.eomsback.common.pojo.SfbFile;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SfbModify {

    private String centre;

    private Date ddate;

    private Boolean flag;

    private List<SfbFile> sfbArr;
}
