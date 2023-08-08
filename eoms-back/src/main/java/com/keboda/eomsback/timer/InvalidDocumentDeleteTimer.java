package com.keboda.eomsback.timer;

import com.keboda.eomsback.produce.mapper.SfpFileMapper;
import com.keboda.eomsback.purchase.mapper.PmkFileMapper;
import com.keboda.eomsback.purchase.mapper.PmmFileMapper;
import com.keboda.eomsback.stock.mapper.ImmFileMapper;
import com.keboda.eomsback.stock.mapper.InaFileMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
public class InvalidDocumentDeleteTimer {

    private final static String[] Centres =  {"WCTZ"};//{"ZJKBDJD","ZJKBDDZ","ADAS","JXKEAO","ZJZBZX","JXKSS"};

    @Resource
    private PmmFileMapper pmmFileMapper;
    @Resource
    private InaFileMapper inaFileMapper;
    @Resource
    private SfpFileMapper sfpFileMapper;
    @Resource
    private PmkFileMapper pmkFileMapper;
    @Resource
    private ImmFileMapper immFileMapper;


    @Scheduled(cron = "${timer.InvalidDocumentDeleteTimer.corn}")
    private void InvalidDocumentDelete() {

        for (String centre : Centres) {
            pmkFileMapper.InvalidDocumentDelete(centre);
            pmmFileMapper.InvalidDocumentDelete(centre);
            inaFileMapper.InvalidDocumentDelete(centre);
            sfpFileMapper.InvalidDocumentDelete(centre);
            immFileMapper.InvalidDocumentDelete(centre);
        }
    }
}
