package com.keboda.eomsback.purchase.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.RvaFile;
import com.keboda.eomsback.purchase.pojo.RvbFile;
import com.keboda.eomsback.purchase.service.IRvaService;
import com.keboda.eomsback.entity.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/rva")
@RestController
public class RvaController {

    @Autowired
    private IRvaService iRvaService;

    @RequestMapping("/searchRvaList")
    public BaseResult searchRvaList(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<RvaFile> page = iRvaService.searchRvaList(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @RequestMapping("/searchRvbList")
    public BaseResult searchRvbList(@RequestBody SearchVo searchVo){
        List<RvbFile> list = iRvaService.searchRvbList(searchVo);
        return BaseResult.success(list);
    }

    @RequestMapping("/alterRva")
    public BaseResult alterRva(@RequestBody RvaFile rvaFile){
        iRvaService.alterRva(rvaFile);
        return BaseResult.success("修改成功");
    }

    @RequestMapping("/alterRvb")
    public BaseResult alterRva(@RequestBody List<RvbFile> rvbFiles){
        iRvaService.alterRvb(rvbFiles);
        return BaseResult.success("修改成功");
    }

}
