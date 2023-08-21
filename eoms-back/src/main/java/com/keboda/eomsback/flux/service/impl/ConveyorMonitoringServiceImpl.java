package com.keboda.eomsback.flux.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.flux.mapper.ConveyorMonitoringMapper;
import com.keboda.eomsback.flux.service.IConveyorMonitoringService;
import com.keboda.eomsback.plc.pojo.Car;
import com.keboda.eomsback.plc.pojo.Hoist;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@DS("fluxWcs")
public class ConveyorMonitoringServiceImpl implements IConveyorMonitoringService {

    @Resource
    private ConveyorMonitoringMapper conveyorMonitoringMapper;

    @Override
    @Transactional
    public void writerCarinfo(Car car) {
        conveyorMonitoringMapper.writerCarinfo(car);
    }

    @Override
    @Transactional
    public void readHoistinfo(Hoist hoist) {
        conveyorMonitoringMapper.readHoistinfo(hoist);
    }
}
