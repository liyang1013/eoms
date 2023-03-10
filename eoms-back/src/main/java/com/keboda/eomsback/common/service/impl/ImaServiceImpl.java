package com.keboda.eomsback.common.service.impl;

import com.keboda.eomsback.common.mapper.ImaFileMapper;
import com.keboda.eomsback.common.pojo.ImaFile;
import com.keboda.eomsback.common.pojo.ImgFile;
import com.keboda.eomsback.common.service.IImaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ImaServiceImpl implements IImaService {
    @Resource
    private ImaFileMapper imaFileMapper;

    @Override
    public List<ImaFile> imaArr(String queryStr, String centre) {
        return imaFileMapper.imaArr(queryStr,centre);
    }

    @Override
    public List<ImgFile> imgArr(String ima01, String centre) {
        return imaFileMapper.imgArr(ima01,centre);
    }
}
