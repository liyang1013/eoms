package com.keboda.eomsback.purchase.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.produce.pojo.vo.SfbModify;
import com.keboda.eomsback.purchase.pojo.PmmFile;
import com.keboda.eomsback.purchase.service.IPmmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pmm")
public class PmmController {

    @Autowired
    private IPmmService iPmmService;

    @PostMapping("/searchPmmListPageHelper")
    public BaseResult searchPmmListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<PmmFile> page = iPmmService.searchPmmListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @PostMapping("/searchPmnList")
    public BaseResult searchPmnList(@RequestBody SearchVo searchVo){
        return BaseResult.success(iPmmService.searchPmnList(searchVo));
    }

    @PostMapping("/closeOut")
    public BaseResult closeOut(@RequestBody List<PmmFile> pmmFileList){
        iPmmService.closeOut(pmmFileList);
        return BaseResult.success("结案成功");
    }
}
