package com.keboda.eomsback.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.flux.mapper.TcIdsequenceFileMapper;
import com.keboda.eomsback.flux.pojo.TcIdsequenceFile;
import com.keboda.eomsback.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@DS("fluxWms")
public class TcIdsequenceFileServiceImpl implements ITcIdsequenceFileService{

    @Resource
    private TcIdsequenceFileMapper tcIdsequenceFileMapper;

    @Override
    @Transactional
    public String getCode(String itype) {

        TcIdsequenceFile seq = tcIdsequenceFileMapper.selectByKey(itype);
        Integer sequence = Integer.valueOf(seq.getIdsequence());
        sequence += 1;
        StringBuilder sb = new StringBuilder();
        sb.append(sequence);

        while(sb.length() < seq.getLength()){
            sb.insert(0,0);
        }

        if(seq.getDateformat() != null) sb.insert(0,DateUtils.parse(new Date().toString(),seq.getDateformat()));
        sb.insert(0,seq.getPrefix());

        tcIdsequenceFileMapper.updateSequence(itype);

        return sb.toString();
    }
}
