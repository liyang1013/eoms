package com.keboda.eomsback.stock.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.EWStockGap;
import com.keboda.eomsback.stock.service.ITlfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tlf")
public class TlfController {

    @Autowired
    private ITlfService iTlfService;

    @RequestMapping("/searchEWStockGapPageHelper")
    public BaseResult searchEWStockGapPageHelper(@RequestBody SearchVo searchVo) {
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<EWStockGap> page = iTlfService.searchEWStockGapPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @RequestMapping("/EWStockGapToExcel")
    public void stockGapToExcel(@RequestBody SearchVo searchVo, HttpServletResponse response) throws IOException {
        List<EWStockGap> rows = iTlfService.EWStockGapToExcel(searchVo,response);
        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.addHeaderAlias("cdanhao", "单号");
        writer.addHeaderAlias("cproduceno", "品号");
        writer.addHeaderAlias("cproducename", "品名");
        writer.addHeaderAlias("cwhcode", "仓库编码");
        writer.addHeaderAlias("imd02", "仓库");
        writer.addHeaderAlias("iqty", "单据数量");
        writer.addHeaderAlias("qty", "异动数量");
        writer.addHeaderAlias("gapqty", "差异数量");
        writer.setOnlyAlias(true);
        writer.write(rows, true);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=test.xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out);
        writer.close();
    }
}
