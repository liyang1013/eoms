package com.keboda.eomsback.base.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.base.mapper.ImaFileMapper;
import com.keboda.eomsback.base.pojo.ImaFile;
import com.keboda.eomsback.common.SearchVo;
import com.keboda.eomsback.common.pojo.ImgFile;
import com.keboda.eomsback.base.service.IImaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ImaServiceImpl implements IImaService {
    @Resource
    private ImaFileMapper imaFileMapper;

    @Override
    public List<ImgFile> imgArr(String ima01, String centre) {
        return imaFileMapper.imgArr(ima01,centre);
    }

    @Override
    public Page<ImaFile> searchImaList(SearchVo searchVo) {
        return imaFileMapper.searchImaList(searchVo);
    }
}
