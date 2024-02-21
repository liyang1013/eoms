package com.keboda.eomsback.authorityReview.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.authorityReview.pojo.Position;
import com.keboda.eomsback.entity.SearchVo;

public interface IPositionService {
    Page<AuthorityRecordsVo> searchPositionRecordsListPageHelper(SearchVo searchVo);

    Integer selectByYearWithCode(Position position);

    Integer insertSelective(Position position);
}
