package com.keboda.eomsback.flux.mapper;

import com.keboda.eomsback.plc.pojo.Car;
import com.keboda.eomsback.plc.pojo.Hoist;

public interface ConveyorMonitoringMapper {

    void writerCarinfo(Car car);

    void readHoistinfo(Hoist hoist);
}
