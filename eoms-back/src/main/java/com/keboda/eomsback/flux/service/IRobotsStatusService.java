package com.keboda.eomsback.flux.service;

import com.keboda.eomsback.flux.pojo.RobotsStatusFile;

public interface IRobotsStatusService {
    RobotsStatusFile selectByKey(Integer id);

    void insertByKeySelective(RobotsStatusFile robotsStatusFile);

    void updateByKeySelective(RobotsStatusFile robotsStatusFile);
}
