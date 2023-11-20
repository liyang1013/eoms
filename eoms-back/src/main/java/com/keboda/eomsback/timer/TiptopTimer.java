package com.keboda.eomsback.timer;

import com.keboda.eomsback.produce.mapper.SfpFileMapper;
import com.keboda.eomsback.purchase.mapper.PmkFileMapper;
import com.keboda.eomsback.purchase.mapper.PmmFileMapper;
import com.keboda.eomsback.stock.mapper.ImmFileMapper;
import com.keboda.eomsback.stock.mapper.InaFileMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TiptopTimer {

    private final static String[] Centres =  {"WCTZ","ZJKBDJD","JXKEAO","ZJZBZX","JXKSS"};

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


    /**
     * 每月1号自动删除空白单身的单据
     */
    @Scheduled(cron = "${timer.invalidDocumentDelete.corn}")
    private void invalidDocumentDelete() {

        for (String centre : Centres) {
            pmkFileMapper.InvalidDocumentDelete(centre);
            pmmFileMapper.InvalidDocumentDelete(centre);
            inaFileMapper.InvalidDocumentDelete(centre);
            sfpFileMapper.InvalidDocumentDelete(centre);
            immFileMapper.InvalidDocumentDelete(centre);
        }
    }
}
