package com.keboda.eomsback.system.service.impl;

import com.keboda.eomsback.system.mapper.GenFileMapper;
import com.keboda.eomsback.system.service.IGenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GenServiceImpl implements IGenService {

    @Resource
    private GenFileMapper genFileMapper;

}
