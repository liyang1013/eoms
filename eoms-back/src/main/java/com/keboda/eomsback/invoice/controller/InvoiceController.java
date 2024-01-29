package com.keboda.eomsback.invoice.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.invoice.pojo.VatInvoice;
import com.keboda.eomsback.invoice.service.IInvoiceService;
import com.keboda.eomsback.stock.pojo.ImaFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private IInvoiceService iInvoiceService;

    @RequestMapping("/vatIdentify")
    public BaseResult vatIdentify(MultipartFile file, Integer grayscale) {
        return BaseResult.success(iInvoiceService.vatIdentify(file,grayscale),"识别成功");
    }

    @RequestMapping("/saveVatInvoice")
    public BaseResult saveVatInvoice(@RequestBody List<VatInvoice> vat){
        iInvoiceService.saveVatInvoice(vat);
        return BaseResult.success("保存成功");
    }

    @RequestMapping("/checkInvoice")
    public BaseResult checkInvoice(@RequestBody List<VatInvoice> vat){
        return BaseResult.success(iInvoiceService.checkInvoice(vat));
    }

    @RequestMapping("/searchVatInvoiceListPageHelper")
    public BaseResult searchVatInvoiceListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<VatInvoice> page = iInvoiceService.searchVatInvoiceListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletResponse response, @RequestParam("fileName") String fileName){
        iInvoiceService.downloadFile(response,fileName);
    }
}
