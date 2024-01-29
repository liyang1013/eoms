package com.keboda.eomsback.invoice.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.invoice.mapper.InvoiceMapper;
import com.keboda.eomsback.invoice.pojo.VatInvoice;
import com.keboda.eomsback.invoice.service.IInvoiceService;
import com.keboda.eomsback.utils.*;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@DS("myself")
public class InvoiceServiceImpl implements IInvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;

    @Value("${filepath.invoiceTemp}")
    public String tempPath;

    @Value("${filepath.vatInvoice}")
    public String vatPath;

    @Override
    public VatInvoice vatIdentify(MultipartFile file, Integer grayscale) {

        String filePath = null;
        String uuid = UUID.randomUUID().toString();
        String invoiceInfo = null;
        String suffix = FileUtils.extractFileSuffix(file.getOriginalFilename());

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
        invoice.setFileName(uuid + "." + suffix);
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
     *
     * @param list
     */
    @Override
    @Transactional
    public void saveVatInvoice(List<VatInvoice> list) {

        for (VatInvoice invoice : list) {

            File file = new File(tempPath + invoice.getFileName());
            File target = new File(vatPath);
            FileUtil.move(file, target, true);

            VatInvoice vat = invoiceMapper.selectByPrimarykey(invoice);
            if (vat == null) {
                invoiceMapper.saveVatInvoice(invoice);
            } else {
                invoiceMapper.updateByPrimarykey(invoice);
            }
        }
    }

    @Override
    public Page<VatInvoice> searchVatInvoiceListPageHelper(SearchVo searchVo) {

        return invoiceMapper.searchVatInvoiceListPageHelper(searchVo);
    }

    @Override
    public void downloadFile(HttpServletResponse response, String fileName) {

        String filePath = vatPath + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            // 设置响应头，使得浏览器能够识别该文件，并进行下载
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());
            response.setCharacterEncoding("UTF-8");

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("下载成功");
            } catch (Exception e) {
                System.out.println("下载失败");
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public List<VatInvoice> checkInvoice(List<VatInvoice> vatInvoiceList) {

        for (VatInvoice invoice : vatInvoiceList) {

            String host = "https://fapiao.market.alicloudapi.com";
            String path = "/invoice/query";
            String method = "GET";
            String appcode = "90abd42712f8445dab9c63393d1c117b";
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization", "APPCODE " + appcode);
            Map<String, String> querys = new HashMap<String, String>();


            querys.put("fpdm", invoice.getInvoiceCode());
            querys.put("jshj", "39.57");
            querys.put("fphm", invoice.getInvoiceNo());
            querys.put("kprq", DateUtils.formatDate(invoice.getInvoiceDate()));
            querys.put("checkCode", "703577");
            querys.put("noTaxAmount", invoice.getAmount().toString());

            try {
                HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
                System.out.println(response.toString());
                //获取response的body
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return vatInvoiceList;
    }
}
