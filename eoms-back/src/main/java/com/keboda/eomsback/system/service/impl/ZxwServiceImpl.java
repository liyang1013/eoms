package com.keboda.eomsback.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.enums.CentreOa;
import com.keboda.eomsback.system.mapper.ZxwFileMapper;
import com.keboda.eomsback.system.pojo.*;
import com.keboda.eomsback.system.service.IHrmResourceService;
import com.keboda.eomsback.system.service.IZxwService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@DS("tiptop")
public class ZxwServiceImpl implements IZxwService {

    @Resource
    private ZxwFileMapper zxwFileMapper;
    @Resource
    private IHrmResourceService iHrmResourceService;


    @Override
    public Page<ZxwFile> searchZxwListPageHelper(SearchVo searchVo) {
        return zxwFileMapper.searchZxwListPageHelper(searchVo);
    }

    @Override
    public String selectPositionCodeByName(String positionName) {
        return zxwFileMapper.selectPositionCodeByName(positionName);
    }

    @Override
    public List<AuthorityRecordsVo> searchRecordsList(String code) {
        return zxwFileMapper.searchRecordsList(code);
    }
}
