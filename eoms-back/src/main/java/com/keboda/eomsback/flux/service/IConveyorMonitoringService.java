package com.keboda.eomsback.flux.service;

import com.keboda.eomsback.plc.pojo.Car;
import com.keboda.eomsback.plc.pojo.Hoist;

public interface IConveyorMonitoringService {
    void writerCarinfo(Car car);

    void readHoistinfo(Hoist hoist);
}
