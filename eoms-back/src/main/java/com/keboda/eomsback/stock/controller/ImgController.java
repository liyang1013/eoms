package com.keboda.eomsback.stock.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.base.pojo.BaseResult;
import com.keboda.eomsback.base.pojo.SearchVo;
import com.keboda.eomsback.stock.pojo.ImaFile;
import com.keboda.eomsback.stock.pojo.ImgFile;
import com.keboda.eomsback.stock.service.IImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/img")
public class ImgController {

    @Autowired
    private IImgService iImgService;

    @RequestMapping("/searchImgList")
    public BaseResult searchImgList(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<ImgFile> page = iImgService.searchImgList(searchVo);
        return BaseResult.success(page,page.getTotal());
    }
}
