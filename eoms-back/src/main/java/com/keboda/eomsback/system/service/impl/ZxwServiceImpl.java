package com.keboda.eomsback.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.authorityReview.service.IActionMenuService;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.enums.CentreOa;
import com.keboda.eomsback.system.mapper.ZxwFileMapper;
import com.keboda.eomsback.system.pojo.*;
import com.keboda.eomsback.system.service.IHrmResourceService;
import com.keboda.eomsback.system.service.IZxwService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@DS("tiptop")
public class ZxwServiceImpl implements IZxwService {

    @Resource
    private ZxwFileMapper zxwFileMapper;
    @Resource
    private IActionMenuService iActionMenuService;


    @Override
    public List<AuthorityRecordsVo> searchRecordsList(String code) {

        String[] exclude = iActionMenuService.selectByPrimaryKey("exclude").getAction().split(",");
        String[] amountTags = iActionMenuService.selectByPrimaryKey("amount").getAction().split(",");
        String[] createTags = iActionMenuService.selectByPrimaryKey("create").getAction().split(",");
        String[] deleteTags = iActionMenuService.selectByPrimaryKey("delete").getAction().split(",");
        String[] updateTags = iActionMenuService.selectByPrimaryKey("update").getAction().split(",");
        String[] readTags = iActionMenuService.selectByPrimaryKey("read").getAction().split(",");
        String[] confirmTags = iActionMenuService.selectByPrimaryKey("confirm").getAction().split(",");
        String[] unConfirmTags = iActionMenuService.selectByPrimaryKey("unConfirm").getAction().split(",");
        String[] postTags = iActionMenuService.selectByPrimaryKey("post").getAction().split(",");
        String[] unPostTags = iActionMenuService.selectByPrimaryKey("unPost").getAction().split(",");
        String[] voidTags = iActionMenuService.selectByPrimaryKey("void").getAction().split(",");
        String[] unVoidTags = iActionMenuService.selectByPrimaryKey("unVoid").getAction().split(",");
        String[] printTags = iActionMenuService.selectByPrimaryKey("print").getAction().split(",");
        String[] exportTags = iActionMenuService.selectByPrimaryKey("export").getAction().split(",");

        return zxwFileMapper.searchRecordsList(code,amountTags,createTags,deleteTags,updateTags,readTags,confirmTags,unConfirmTags,postTags,unPostTags,voidTags,unVoidTags,printTags,exportTags,exclude);
    }

}
