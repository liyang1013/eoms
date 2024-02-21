package com.keboda.eomsback.authorityReview.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.mapper.PersonalPositionMapper;
import com.keboda.eomsback.authorityReview.pojo.PersonalPosition;
import com.keboda.eomsback.authorityReview.service.IPersonalPositionService;
import com.keboda.eomsback.entity.SearchVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@DS("myself")
public class PersonalPositionServiceImpl implements IPersonalPositionService {

    @Resource
    private PersonalPositionMapper personalPositionMapper;

    @Override
    public Page<PersonalPosition> searchPersonalPositionRecordsListPageHelper(SearchVo searchVo) {
        return personalPositionMapper.searchPersonalPositionRecordsListPageHelper(searchVo);
    }

    @Override
    public void insertSelective(PersonalPosition personalPosition) {
        personalPositionMapper.insertSelective(personalPosition);
    }

    @Override
    public PersonalPosition selectByPrimaryKey(String year, String zx01, String zw01) {
        return personalPositionMapper.selectByPrimaryKey( year,  zx01,  zw01);
    }

    @Override
    public void deletePersonalPositionRecords(PersonalPosition personalPosition) {
        personalPositionMapper.deleteByPrimaryKey(personalPosition);
    }

    @Override
    public PersonalPosition PersonalPositionContrastRecords(Integer year, int itype, String code) {
        return personalPositionMapper.PersonalPositionContrastRecords(year,itype,code);
    }
}
