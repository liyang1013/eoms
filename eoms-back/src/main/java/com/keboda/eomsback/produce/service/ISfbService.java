package com.keboda.eomsback.produce.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.produce.pojo.SfaFile;
import com.keboda.eomsback.produce.pojo.SfbFile;
import com.keboda.eomsback.produce.pojo.vo.SfbModify;
import com.keboda.eomsback.produce.pojo.vo.SfbStatusVo;

import java.util.List;

public interface ISfbService {
    List<SfbFile> searchSfbList(SearchVo searchVo);

    SfbStatusVo searchSfbStatus(SearchVo searchVo) throws RuntimeException;

    Page<SfbFile> searchSfbListPageHelper(SearchVo searchVo);

    void modifySfbDate(SfbModify sfbModify);

    void closeOut(List<SfbFile> sfbFiles);

    List<SfaFile> searchSfaList(SearchVo searchVo);

    void alterGem(SfbFile sfbFile);

    void isFQC(SfbFile sfbFile);
}
