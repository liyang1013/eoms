package com.keboda.eomsback.authorityReview.service;

import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.authorityReview.pojo.Permission;
import com.keboda.eomsback.entity.SearchVo;

import java.util.List;

public interface IPermissionService {
    Permission selectByKey(Permission permission);

    void insertSelective(Permission permission);

    void deleteRecords(List<AuthorityRecordsVo> authorityRecordsVoList);

    List<AuthorityRecordsVo> searchRecordsList(Integer year,String code);

    void updateSelective(Permission permission);
}
