package com.keboda.eomsback.stock.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.mapper.ImaFileMapper;
import com.keboda.eomsback.stock.mapper.ImdFileMapper;
import com.keboda.eomsback.stock.mapper.ImgFileMapper;
import com.keboda.eomsback.stock.pojo.ImaFile;
import com.keboda.eomsback.stock.pojo.ImdFile;
import com.keboda.eomsback.stock.pojo.ImgFile;
import com.keboda.eomsback.stock.service.IImgService;
import com.keboda.eomsback.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class ImgServiceImpl implements IImgService {

    @Resource
    private ImgFileMapper imgFileMapper;
    @Resource
    private ImaFileMapper imaFileMapper;
    @Resource
    private ImdFileMapper imdFileMapper;

    @Override
    public Page<ImgFile> searchImgList(SearchVo searchVo) {
        return imgFileMapper.searchImgList(searchVo);
    }

    @Override
    @Transactional
    public void repairImg(SearchVo searchVo) {
        if(searchVo.getCentre() == null || searchVo.getCentre().trim().isEmpty()) throw new RuntimeException("中心不能为空");
        if(searchVo.getCode_2() == null || searchVo.getCode_2().trim().isEmpty()) throw new RuntimeException("仓库不能为空");
        ImdFile imdFile = imdFileMapper.selectByKey(searchVo.getCentre(),searchVo.getCode_2());
        ImaFile imaFile = imaFileMapper.selectByKey(searchVo.getCentre(),searchVo.getCode_1());
        if(imaFile == null) throw new RuntimeException("料件编码不存在,请输入完整的料件编码");

        ImgFile imgFile = imgFileMapper.selectByKey(searchVo.getCentre(),searchVo.getCode_2(),imaFile.getIma01());
        if(imgFile == null){
            imgFile = new ImgFile();
            imgFile.setCentre(searchVo.getCentre());
            imgFile.setImgplant(searchVo.getCentre());
            imgFile.setImglegal(searchVo.getCentre());
            imgFile.setImg01(imaFile.getIma01());
            imgFile.setImg02(searchVo.getCode_2());
            imgFile.setImg03(" ");
            imgFile.setImg04(" ");
            imgFile.setImg09(imaFile.getIma25());
            imgFile.setImg10(BigDecimal.ZERO);
            imgFile.setImg14(DateUtils.parseDate(DateUtils.formatDate(new Date())));
            imgFile.setImg17(DateUtils.parseDate(DateUtils.formatDate(new Date())));
            imgFile.setImg18(DateUtils.parseDate("2099-12-31"));
            imgFile.setImg20(imaFile.getIma44Fac());
            imgFile.setImg21(imaFile.getIma44Fac());
            imgFile.setImg22(imdFile.getImd10());
            imgFile.setImg23(imdFile.getImd11());
            imgFile.setImg24(imdFile.getImd12());
            imgFile.setImg25(imdFile.getImd13());
            imgFile.setImg27(imdFile.getImd14());
            imgFile.setImg28(imdFile.getImd15());
            imgFile.setImg30(BigDecimal.ZERO);
            imgFile.setImg31(BigDecimal.ZERO);
            imgFile.setImg32(BigDecimal.ZERO);
            imgFile.setImg33(BigDecimal.ZERO);
            imgFile.setImg34(imaFile.getIma86Fac());
            imgFile.setImg37(DateUtils.parseDate(DateUtils.formatDate(new Date())));
            imgFileMapper.insertSelective(imgFile);
        }else throw new RuntimeException("库存记录已存在不需要修复");

    }
}
