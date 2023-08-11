package com.keboda.eomsback.stock.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.TcWiaFile;
import com.keboda.eomsback.stock.service.IBarCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/barcode")
public class BarCodeController {

    @Autowired
    private IBarCodeService iBarCodeService;


    @RequestMapping("/searchStockGapPageHelper")
    public BaseResult searchStockGapPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<TcWiaFile> page = iBarCodeService.searchStockGapPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }
}
