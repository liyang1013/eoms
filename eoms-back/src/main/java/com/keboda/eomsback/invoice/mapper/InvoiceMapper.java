package com.keboda.eomsback.invoice.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.invoice.pojo.VatInvoice;

public interface InvoiceMapper {

    void saveVatInvoice(VatInvoice invoice);

    VatInvoice selectByPrimarykey(VatInvoice invoice);

    void updateByPrimarykey(VatInvoice invoice);

    Page<VatInvoice> searchVatInvoiceListPageHelper(SearchVo searchVo);
}
