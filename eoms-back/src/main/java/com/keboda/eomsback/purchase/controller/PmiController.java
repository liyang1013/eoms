package com.keboda.eomsback.purchase.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.PmiFile;
import com.keboda.eomsback.purchase.pojo.PmjFile;
import com.keboda.eomsback.purchase.pojo.RvaFile;
import com.keboda.eomsback.purchase.service.IPmiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pmi")
public class PmiController {

    @Autowired
    private IPmiService iPmiService;

    @PostMapping("/searchPmiListPageHelper")
    public BaseResult searchPmiListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<PmiFile> page = iPmiService.searchPmiListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @PostMapping("/searchPmjList")
    public BaseResult searchPmjList(@RequestBody SearchVo searchVo){
        return BaseResult.success(iPmiService.searchPmjList(searchVo));
    }

    @PostMapping("/alterDate")
    public BaseResult alterDate(@RequestBody PmjFile pmjFile,@RequestParam("flag") String flag){
        iPmiService.alterDate(pmjFile,flag);
        return BaseResult.success("修改成功");
    }

}
