package com.keboda.eomsback.stock.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.stock.pojo.ImaFile;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.service.IImaService;
import com.keboda.eomsback.entity.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/ima")
@RestController
public class ImaController {

    @Autowired
    private IImaService iImaService;

    @RequestMapping("/searchImaList")
    public BaseResult searchImaList(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<ImaFile> page = iImaService.searchImaList(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @RequestMapping("/alterStockParameter")
    public BaseResult alterStockParameter( MultipartFile file, String centre) throws IOException {
        iImaService.alterStockParameter(file,centre);
        return BaseResult.success("库存参数刷新成功");
    }

    @RequestMapping("/imaPackage")
    public BaseResult imaPackage( MultipartFile file, String centre) throws IOException {
        iImaService.imaPackage(file,centre);
        return BaseResult.success("产品包装批量维护成功");
    }
}
