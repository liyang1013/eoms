package com.keboda.eomsback.system.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.ZxwFile;

import java.util.List;

public interface IZxwService {
    Page<ZxwFile> searchZxwListPageHelper(SearchVo searchVo);

    String selectPositionCodeByName(String positionName);

    List<AuthorityRecordsVo> searchRecordsList(String code);
}
