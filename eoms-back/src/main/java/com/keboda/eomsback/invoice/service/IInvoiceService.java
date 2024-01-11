package com.keboda.eomsback.invoice.service;

import com.keboda.eomsback.invoice.pojo.VatInvoice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IInvoiceService {
    VatInvoice vatIdentify(MultipartFile file, Integer grayscale);

    void saveVatInvoice(List<VatInvoice> vat);
}
