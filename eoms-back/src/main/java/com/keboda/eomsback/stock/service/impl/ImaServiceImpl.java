package com.keboda.eomsback.stock.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.github.pagehelper.Page;
import com.keboda.eomsback.stock.mapper.ImaFileMapper;
import com.keboda.eomsback.stock.mapper.ObeFileMapper;
import com.keboda.eomsback.stock.mapper.ObkFileMapper;
import com.keboda.eomsback.stock.pojo.ImaFile;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.ImgFile;
import com.keboda.eomsback.stock.pojo.ObeFile;
import com.keboda.eomsback.stock.pojo.ObkFile;
import com.keboda.eomsback.stock.service.IImaService;
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

    @Override
    public List<ImgFile> imgArr(String ima01, String centre) {
        return imaFileMapper.imgArr(ima01, centre);
    }

    @Override
    public Page<ImaFile> searchImaList(SearchVo searchVo) {
        return imaFileMapper.searchImaList(searchVo);
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
            //产品档案
            ImaFile imaFile = imaFileMapper.selectByKey(centre, map.get("品号").toString());
            if (imaFile == null) throw new RuntimeException("料件：" + map.get("品号").toString() + "在中心：" + centre + "中不存在");
            ImaFile updateIma = new ImaFile();
            updateIma.setCentre(centre);
            updateIma.setIma01(imaFile.getIma01());
            updateIma.setTbIma202(1);
            updateIma.setTbIma205(25);
            updateIma.setTbIma206("Y");
            updateIma.setTbIma208(map.get("包装方式").toString());
            imaFileMapper.updateByPrimaryKeySelective(updateIma);

            //产品客户档案
            ObkFile obkFile = obkFileMapper.selectByKeyLimitOne(centre,imaFile.getIma01());
            if(obkFile == null){
                obkFile = new ObkFile();
                obkFile.setCentre(centre);
                obkFile.setObk01(imaFile.getIma01());
                obkFile.setObk02("C01041");
                obkFile.setObk03(map.get("外箱客户零件号").toString());
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
                if(!Objects.equals(obkFile.getObkacti(), "Y") || obkFile.getObk03() == null || !obkFile.getObk03().equals(map.get("外箱客户零件号").toString())){
                    ObkFile updateObk = new ObkFile();
                    updateObk.setCentre(centre);
                    updateObk.setObk01(obkFile.getObk01());
                    updateObk.setObk02(obkFile.getObk02());
                    updateObk.setObk05(obkFile.getObk05());
                    updateObk.setObk03(map.get("外箱客户零件号").toString());
                    updateObk.setObkacti("Y");
                    obkFileMapper.updateByPrimaryKeySelective(updateObk);
                }
            }

            //包装方式
//            ObeFile obeFile = obeFileMapper.selectByKey(centre,);
        }
    }
}
