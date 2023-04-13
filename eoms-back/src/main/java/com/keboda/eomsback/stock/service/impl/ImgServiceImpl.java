package com.keboda.eomsback.stock.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.mapper.ImgFileMapper;
import com.keboda.eomsback.stock.pojo.ImgFile;
import com.keboda.eomsback.stock.service.IImgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ImgServiceImpl implements IImgService {

    @Resource
    private ImgFileMapper imgFileMapper;

    @Override
    public Page<ImgFile> searchImgList(SearchVo searchVo) {
        return imgFileMapper.searchImgList(searchVo);
    }
}
