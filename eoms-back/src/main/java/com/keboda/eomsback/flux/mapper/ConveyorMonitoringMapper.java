package com.keboda.eomsback.flux.mapper;

import com.keboda.eomsback.plc.pojo.Car;

public interface ConveyorMonitoringMapper {

    void readCarinfo(Car car);
}
