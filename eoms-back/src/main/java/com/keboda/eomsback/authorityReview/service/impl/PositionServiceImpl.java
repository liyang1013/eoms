package com.keboda.eomsback.authorityReview.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.mapper.PositionMapper;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.authorityReview.pojo.Position;
import com.keboda.eomsback.authorityReview.service.IPositionService;
import com.keboda.eomsback.entity.SearchVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@DS("myself")
public class PositionServiceImpl implements IPositionService {

    @Resource
    private PositionMapper positionMapper;

    @Override
    public Page<AuthorityRecordsVo> searchRecordsListPageHelper(SearchVo searchVo) {
        return positionMapper.searchRecordsListPageHelper(searchVo);
    }

    @Override
    public Integer selectByYearWithCode(Position position) {
        return positionMapper.selectByYearWithCode(position);
    }

    @Override
    public Integer insertSelective(Position position) {
        return positionMapper.insertSelective(position);
    }
}
