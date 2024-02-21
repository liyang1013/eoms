package com.keboda.eomsback.authorityReview.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.pojo.PersonalPosition;
import com.keboda.eomsback.entity.SearchVo;

public interface IPersonalPositionService {
    Page<PersonalPosition> searchPersonalPositionRecordsListPageHelper(SearchVo searchVo);

    void insertSelective(PersonalPosition personalPosition);

    PersonalPosition selectByPrimaryKey(String year, String zx01, String zw01);

    void deletePersonalPositionRecords(PersonalPosition personalPosition);

    PersonalPosition PersonalPositionContrastRecords(Integer year, int itype, String code);
}
