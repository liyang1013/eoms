package com.keboda.eomsback.system.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.mapper.GenFileMapper;
import com.keboda.eomsback.system.pojo.GenFile;
import com.keboda.eomsback.system.service.IGenService;
import com.keboda.eomsback.utils.JwtTokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GenServiceImpl implements IGenService {

    @Resource
    private GenFileMapper genFileMapper;

    @Override
    public GenFile login(GenFile genFile) {
        if(genFile.getGen01() == null || genFile.getGen01().trim().isEmpty()){
            throw new RuntimeException("用户名不能为空");
        }
        if(genFile.getGen02() == null || genFile.getGen02().trim().isEmpty()){
            throw new RuntimeException("密码不能为空");
        }
        genFile.setToken(JwtTokenUtils.getToken(genFile.getGen01(),genFile.getGen02()));
        return genFile;
    }

    @Override
    public Page<GenFile> searchGenList(SearchVo searchVo) {
        return genFileMapper.searchGenList(searchVo);
    }
}
