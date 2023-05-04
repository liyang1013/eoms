package com.keboda.eomsback.stock.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.github.pagehelper.Page;
import com.keboda.eomsback.stock.mapper.ImaFileMapper;
import com.keboda.eomsback.stock.mapper.ObeFileMapper;
import com.keboda.eomsback.stock.mapper.ObkFileMapper;
import com.keboda.eomsback.stock.mapper.OblFileMapper;
import com.keboda.eomsback.stock.pojo.*;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.service.IImaService;
import com.keboda.eomsback.stock.service.IObeService;
import com.keboda.eomsback.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ImaServiceImpl implements IImaService {
    @Resource
    private ImaFileMapper imaFileMapper;
    @Resource
    private ObkFileMapper obkFileMapper;
    @Resource
    private ObeFileMapper obeFileMapper;
    @Resource
    private IObeService iObeService;
    @Resource
    private OblFileMapper oblFileMapper;

    @Override
    public List<ImgFile> imgArr(String ima01, String centre) {
        return imaFileMapper.imgArr(ima01, centre);
    }

    @Override
    public Page<ImaFile> searchImaListPageHelper(SearchVo searchVo) {
        return imaFileMapper.searchImaListPageHelper(searchVo);
    }

    @Override
    @Transactional
    public void alterStockParameter(MultipartFile file, String centre) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<Map<String, Object>> read = reader.readAll();
        for (Map<String, Object> map : read) {
            ImaFile imaFile = new ImaFile();
            imaFile.setCentre(centre);
            imaFile.setIma01(map.get("料号").toString());
            imaFile.setIma27(new BigDecimal(map.get("安全库存").toString()));
            imaFile.setImaud09(new BigDecimal(map.get("最低库存").toString()));
            imaFile.setIma271(new BigDecimal(map.get("最高库存").toString()));
            imaFileMapper.updateByPrimaryKeySelective(imaFile);
        }
    }

    @Override
    @Transactional
    public void imaPackage(MultipartFile file, String centre) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<Map<String, Object>> read = reader.readAll();
        for (Map<String, Object> map : read) {

            String ima01 = map.get("品号").toString();
            String packaging = map.get("包装方式").toString();
            String customerCode = map.get("外箱客户零件号").toString();
            BigDecimal innerQty = new BigDecimal(map.get("Qty").toString());//内包装
            BigDecimal net = new BigDecimal(map.get("NET").toString());
            BigDecimal innerWeight = net.divide(innerQty);//内包装重量
            BigDecimal total = new BigDecimal(map.get("Total").toString());
            BigDecimal outWeight = total.subtract(net);//外包装重量
            Integer outQty = Integer.valueOf(map.get("栈板包装箱数量").toString());//外包装
            String dimensions = map.get("包装箱尺寸").toString().replace("mm","");
            BigDecimal l = new BigDecimal(dimensions.split("\\*")[0]);
            BigDecimal w = new BigDecimal(dimensions.split("\\*")[1]);
            BigDecimal h = new BigDecimal(dimensions.split("\\*")[2]);
            String pallet = map.get("托盘尺寸").toString().replace("mm","");
            BigDecimal tl = new BigDecimal(pallet.split("\\*")[0]);
            BigDecimal tw = new BigDecimal(pallet.split("\\*")[1]);
            BigDecimal row = new BigDecimal(map.get("层数").toString());

            //产品档案
            ImaFile imaFile = imaFileMapper.selectByKey(centre, ima01);
            if (imaFile == null) throw new RuntimeException("料件：" + ima01 + "在中心：" + centre + "中不存在");
            ImaFile updateIma = new ImaFile();
            updateIma.setCentre(centre);
            updateIma.setIma01(imaFile.getIma01());
            updateIma.setTbIma202(1);
            updateIma.setTbIma205(25);
            updateIma.setTbIma206("Y");
            updateIma.setTbIma208(packaging);
            imaFileMapper.updateByPrimaryKeySelective(updateIma);

            //产品客户档案
            ObkFile obkFile = obkFileMapper.selectByKeyLimitOne(centre,imaFile.getIma01());
            if(obkFile == null){
                obkFile = new ObkFile();
                obkFile.setCentre(centre);
                obkFile.setObk01(imaFile.getIma01());
                obkFile.setObk02("C01041");
                obkFile.setObk03(customerCode);
                obkFile.setObk04(DateUtils.parseDate(DateUtils.formatDate(new Date())));
                obkFile.setObk05("RMB");
                obkFile.setObk07(imaFile.getIma31());
                obkFile.setObk08(BigDecimal.ZERO);
                obkFile.setObk09(BigDecimal.ZERO);
                obkFile.setObk11(imaFile.getIma24());
                obkFile.setObkacti("Y");
                obkFile.setObkdate(DateUtils.parseDate(DateUtils.formatDate(new Date())));
                obkFile.setObkgrup("3209");
                obkFile.setObkmodu("tiptop");
                obkFile.setObkuser("tiptop");
                obkFileMapper.insertSelective(obkFile);
            }else{
                if(!Objects.equals(obkFile.getObkacti(), "Y") || obkFile.getObk03() == null || !obkFile.getObk03().equals(customerCode)){
                    ObkFile updateObk = new ObkFile();
                    updateObk.setCentre(centre);
                    updateObk.setObk01(obkFile.getObk01());
                    updateObk.setObk02(obkFile.getObk02());
                    updateObk.setObk05(obkFile.getObk05());
                    updateObk.setObk03(customerCode);
                    updateObk.setObkacti("Y");
                    obkFileMapper.updateByPrimaryKeySelective(updateObk);
                }
            }

            //包装方式
            ObeFile obeFile = new ObeFile();
            obeFile.setCentre(centre);
            obeFile.setObe02(packaging);
            obeFile.setObe11("箱");
            obeFile.setObe12(innerQty);
            obeFile.setObe13(innerWeight);
            obeFile.setObe21("托");
            obeFile.setObe22(outQty);
            obeFile.setObe23(outWeight);
            obeFile.setObe24(innerQty.multiply(new BigDecimal(outQty)));
            obeFile.setObe251(l);
            obeFile.setObe252(w);
            obeFile.setObe253(h);
            obeFile.setObe26(l.multiply(w).multiply(h));
            obeFile.setObe291(tl);
            obeFile.setObe292(tw);
            obeFile.setObe30(row);

            ObeFile temp = obeFileMapper.selectByParam(obeFile);
            String obe01;
            if(temp == null){
                obe01 = iObeService.primaryKey(centre);
                obeFile.setObe01(obe01);
                obeFileMapper.insertSelective(obeFile);
            }else obe01 = temp.getObe01();

            //产品客户包装
            OblFile oblFile = oblFileMapper.selectByKey(centre,ima01,obkFile.getObk02());
            if(oblFile == null){
                oblFile = new OblFile();
                oblFile.setCentre(centre);
                oblFile.setObl01(ima01);
                oblFile.setObl02(obkFile.getObk02());
                oblFile.setObl03(obe01);
                oblFileMapper.insertSelective(oblFile);
            }else{
                if(!oblFile.getObl03().equals(obe01)){
                    oblFile.setCentre(centre);
                    oblFile.setObl03(obe01);
                    oblFileMapper.insertSelective(oblFile);
                }
            }
        }
    }
}
