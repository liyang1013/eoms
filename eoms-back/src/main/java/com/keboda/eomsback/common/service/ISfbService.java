package com.keboda.eomsback.common.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.common.pojo.SfbFile;
import com.keboda.eomsback.common.pojo.vo.SfbModify;
import com.keboda.eomsback.common.pojo.vo.SfbProcessVo;
import com.keboda.eomsback.common.pojo.vo.SfbSearchVo;

import java.util.List;

public interface ISfbService {
    List<SfbFile> sfb01Arr(String queryStr, String centre);

    SfbProcessVo sfbProcess(String sfb01, String centre) throws RuntimeException;

    Page<SfbFile> sfbArr(SfbSearchVo searchVo);

    void modifyDate(SfbModify sfbModify);
}
