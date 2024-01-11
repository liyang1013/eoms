package com.keboda.eomsback.invoice.controller;

import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.invoice.pojo.VatInvoice;
import com.keboda.eomsback.invoice.service.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

}
