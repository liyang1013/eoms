package com.keboda.eomsback.invoice.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.invoice.pojo.VatInvoice;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IInvoiceService {
    VatInvoice vatIdentify(MultipartFile file, Integer grayscale);

    void saveVatInvoice(List<VatInvoice> vat);

    Page<VatInvoice> searchVatInvoiceListPageHelper(SearchVo searchVo);

    void downloadFile(HttpServletResponse response, String fileName);

    List<VatInvoice> checkInvoice(List<VatInvoice> vat);
}
