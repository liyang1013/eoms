package com.keboda.eomsback.flux.service;

import com.keboda.eomsback.plc.pojo.Car;

public interface IConveyorMonitoringService {
    void readCarinfo(Car car);
}
