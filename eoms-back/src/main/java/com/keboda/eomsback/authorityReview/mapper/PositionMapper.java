package com.keboda.eomsback.authorityReview.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.authorityReview.pojo.Position;
import com.keboda.eomsback.entity.SearchVo;


public interface PositionMapper {

    int insertSelective(Position record);

    int updateByPrimaryKeySelective(Position record);

    Page<AuthorityRecordsVo> searchPositionRecordsListPageHelper(SearchVo searchVo);

    Integer selectByYearWithCode(Position position);
}