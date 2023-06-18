package com.keboda.eomsback.flux.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.flux.mapper.EchartsMapper;
import com.keboda.eomsback.flux.pojo.ChartsCommonVo;
import com.keboda.eomsback.flux.pojo.WcsTaskTypeTime;
import com.keboda.eomsback.flux.service.IEchartsWcsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@DS("fluxWcs")
public class EchartsWcsServiceImpl implements IEchartsWcsService {

    @Resource
    private EchartsMapper echartsMapper;


    @Override
    public List<ChartsCommonVo> wcsTaskTypeTime(String type) {
        List<WcsTaskTypeTime> arr = echartsMapper.wcsTaskTypeTime();
        List<ChartsCommonVo> list = new ArrayList<>();

        for (WcsTaskTypeTime wcsTaskTypeTime : arr) {

            ChartsCommonVo commonVo = new ChartsCommonVo();
            commonVo.setName(wcsTaskTypeTime.getTaskType());

            switch (type) {
                case "taskNum":
                    commonVo.setValue(wcsTaskTypeTime.getTaskNum());
                    break;
                case "taskTime":
                    commonVo.setValue(wcsTaskTypeTime.getTaskTime());
                    break;
                case "avgTime":
                    commonVo.setValue(wcsTaskTypeTime.getAvgTime());
                    break;
            }

            list.add(commonVo);

        }

        return list;
    }
}
