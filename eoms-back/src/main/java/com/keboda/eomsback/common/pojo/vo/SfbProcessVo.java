package com.keboda.eomsback.common.pojo.vo;

import com.keboda.eomsback.common.pojo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SfbProcessVo {

    private SfbFile sfb;

    private List<SfpFile> sfp;

    private List<ShbFile> shb;

    private List<QcfFile> qcf;

    private List<SfuFile> sfu;
}
