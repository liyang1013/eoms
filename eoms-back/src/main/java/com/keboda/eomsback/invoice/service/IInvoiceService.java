package com.keboda.eomsback.invoice.service;

import com.keboda.eomsback.invoice.pojo.Invoice;
import org.springframework.web.multipart.MultipartFile;

public interface IInvoiceService {
    Invoice ocr(MultipartFile file,Integer grayscale);
}
