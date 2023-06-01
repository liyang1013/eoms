package com.keboda.eomsback.flux.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.flux.mapper.RobotsStatusFileMapper;
import com.keboda.eomsback.flux.pojo.RobotsStatusFile;
import com.keboda.eomsback.flux.service.IRobotsStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@DS("fluxWms")
public class RobotsStatusServiceImpl implements IRobotsStatusService {

    @Resource
    private RobotsStatusFileMapper robotsStatusFileMapper;


    @Override
    public RobotsStatusFile selectByKey(Integer id) {
        return robotsStatusFileMapper.selectByKey(id);
    }

    @Override
    @Transactional
    public void insertByKeySelective(RobotsStatusFile robotsStatusFile) {
        robotsStatusFileMapper.insertByKeySelective(robotsStatusFile);
    }

    @Override
    @Transactional
    public void updateByKeySelective(RobotsStatusFile robotsStatusFile) {
        robotsStatusFileMapper.updateByKeySelective(robotsStatusFile);
    }
}
