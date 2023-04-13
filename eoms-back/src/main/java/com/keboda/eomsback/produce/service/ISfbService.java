package com.keboda.eomsback.produce.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.produce.pojo.SfbFile;
import com.keboda.eomsback.produce.pojo.vo.SfbModify;
import com.keboda.eomsback.produce.pojo.vo.SfbProcessVo;

import java.util.List;

public interface ISfbService {
    List<SfbFile> sfb01Arr(String queryStr, String centre);

    SfbProcessVo sfbProcess(String sfb01, String centre) throws RuntimeException;

    Page<SfbFile> searchSfbList(SearchVo searchVo);

    void modifyDate(SfbModify sfbModify);
}
