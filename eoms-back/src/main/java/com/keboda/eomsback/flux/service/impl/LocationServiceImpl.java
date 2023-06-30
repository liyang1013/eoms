package com.keboda.eomsback.flux.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.flux.mapper.LocationMapper;
import com.keboda.eomsback.flux.pojo.WcsTaskTypeTime;
import com.keboda.eomsback.flux.service.ILocationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@DS("fluxWms")
public class LocationServiceImpl  implements ILocationService {

    @Resource
    private LocationMapper locationMapper;


    @Override
    public List<WcsTaskTypeTime> aa() {
        return locationMapper.aa();
    }
}
