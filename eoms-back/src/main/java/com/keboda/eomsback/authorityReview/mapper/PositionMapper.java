package com.keboda.eomsback.authorityReview.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.pojo.Position;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.entity.SearchVo;
import org.springframework.stereotype.Repository;


public interface PositionMapper {

    int insertSelective(Position record);

    int updateByPrimaryKeySelective(Position record);

    Page<AuthorityRecordsVo> searchRecordsListPageHelper(SearchVo searchVo);

    Integer selectByYearWithCode(Position position);
}