package com.keboda.eomsback.invoice.controller;

import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.invoice.service.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private IInvoiceService iInvoiceService;

    @RequestMapping("/ocr")
    public BaseResult Ocr(MultipartFile file, Integer grayscale) {
        return BaseResult.success(iInvoiceService.ocr(file,grayscale),"识别成功");
    }

}
