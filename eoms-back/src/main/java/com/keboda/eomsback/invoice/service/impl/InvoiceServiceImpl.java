package com.keboda.eomsback.invoice.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.invoice.mapper.InvoiceMapper;
import com.keboda.eomsback.invoice.pojo.VatInvoice;
import com.keboda.eomsback.invoice.service.IInvoiceService;
import com.keboda.eomsback.utils.DateUtils;
import com.keboda.eomsback.utils.ImageUtils;
import com.keboda.eomsback.utils.PDFUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@DS("myself")
public class InvoiceServiceImpl implements IInvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;

    @Value("${filepath.invoiceTemp}")
    public String tempPath;

    @Override
    public VatInvoice vatIdentify(MultipartFile file, Integer grayscale) {

        String filePath = null;
        String uuid = UUID.randomUUID().toString();
        String invoiceInfo = null;
        String suffix = file.getContentType().split("/")[1];

        try {
            filePath = tempPath + uuid + "." + suffix;

            File f = new File(filePath);
            file.transferTo(f);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


        if (file.getContentType().contains("pdf")) {

            invoiceInfo = new PDFUtils().getInvoiceInfo(filePath);

        } else if (file.getContentType().contains("image")) {
            invoiceInfo = ImageUtils.deEncodeByPath(filePath);
        }

        // 读取到的信息为 ： 01，发票类型，发票代码，发票号码，发票金额，开票日期，校验码，随机产生的摘要信息
        String[] infos = invoiceInfo.split(",");
        if (infos.length != 8) {
            throw new RuntimeException("发票格式不正确或图片不清晰");
        }

        VatInvoice invoice = new VatInvoice();
        invoice.setUuid(uuid);
        invoice.setSuffix(suffix);
        invoice.setInvoiceType(infos[1]); //发票类型
        invoice.setInvoiceCode(infos[2]); //发票代码
        invoice.setInvoiceNo(infos[3]); // 发票号码
        invoice.setAmount(new BigDecimal(infos[4])); // 发票金额
        invoice.setInvoiceDate(DateUtils.parseNODate(infos[5])); //开票日期
        invoice.setCheckCode(infos[6]); // 校验码*/

        return invoice;
    }

    /**
     * 转移temp文件&&保存数据
     * @param vat
     */
    @Override
    @Transactional
    public void saveVatInvoice(List<VatInvoice> vat) {

        for (VatInvoice invoice : vat) {


            invoiceMapper.saveVatInvoice(invoice);
        }
    }
}
