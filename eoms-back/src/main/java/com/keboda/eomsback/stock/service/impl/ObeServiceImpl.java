package com.keboda.eomsback.stock.service.impl;

import com.keboda.eomsback.stock.mapper.ObeFileMapper;
import com.keboda.eomsback.stock.service.IObeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ObeServiceImpl implements IObeService {

    @Resource
    private ObeFileMapper obeFileMapper;

    @Override
    @Transactional
    public String primaryKey(String centre) {
        Integer num = obeFileMapper.selectMaxCount(centre);
        if(num == null) num = 1;
        else num++;
        StringBuilder sb = new StringBuilder();
        sb.append(num);
        while(sb.length() < 3){
            sb.insert(0,"0");
        }
        return sb.toString();
    }
}
