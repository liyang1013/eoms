package com.keboda.eomsback.purchase.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.common.SearchVo;
import com.keboda.eomsback.purchase.mapper.*;
import com.keboda.eomsback.purchase.pojo.*;
import com.keboda.eomsback.purchase.service.IRvaService;
import com.keboda.eomsback.stock.mapper.ImgFileMapper;
import com.keboda.eomsback.stock.mapper.TlfFileMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
public class RvaServiceImpl implements IRvaService {

    @Resource
    private RvaFileMapper rvaFileMapper;
    @Resource
    private RvbFileMapper rvbFileMapper;
    @Resource
    private RvvFileMapper rvvFileMapper;
    @Resource
    private RvuFileMapper rvuFileMapper;
    @Resource
    private ImgFileMapper imgFileMapper;
    @Resource
    private TlfFileMapper tlfFileMapper;
    @Resource
    private PmnFileMapper pmnFileMapper;

    @Override
    public Page<RvaFile> searchRvaList(SearchVo searchVo) {
        return rvaFileMapper.searchRvaList(searchVo);
    }

    @Override
    public List<RvbFile> searchRvbList(SearchVo searchVo) {
        return rvbFileMapper.searchRvbList(searchVo);
    }

    /**
     * 修改收货单表头
     * @param rvaFile
     */
    @Override
    @Transient
    public void alterRva(RvaFile rvaFile) {
        rvaFileMapper.alterRva(rvaFile);
    }

    @Override
    @Transient
    public void alterRvb(List<RvbFile> rvbFiles) {
        for (RvbFile rvb : rvbFiles) {
            RvbFile rvbFile = rvbFileMapper.selectByKey(rvb.getRvb01(),rvb.getRvb02(),rvb.getRvbplant());
            if(rvbFile == null) throw new RuntimeException("单号："+rvb.getRvb01()+"查找不到对应收货单明细");
            //如果数量修改，更新数据库
            if(rvb.getRvb07().compareTo(rvbFile.getRvb07()) != 0){
                if(rvbFile.getRvb22() != null ) throw new RuntimeException("发票已生成不允许修改数量");
                List<RvvFile> rvvFiles = rvvFileMapper.selectByRvb(rvb.getRvb01(),rvb.getRvb02(),rvb.getRvbplant(),1);
                if(rvbFiles.size() > 1) throw new RuntimeException("对应入库单大于一张，请手动修改数量");
                BigDecimal rvb30 = BigDecimal.ZERO;//入库量，没入库单默认0
                //入库单存在更新入库单数量
                if(rvbFiles.size() != 0){
                    RvvFile rvvFile = rvvFiles.get(0);
                    RvuFile rvuFile = rvuFileMapper.selectByKey(rvvFile.getRvv01(),rvvFile.getRvvplant());
                    //入库单存在且审核，更新现存量表，异动表
                    if(rvuFile.getRvuconf().equals("Y")){
                        imgFileMapper.updateQty(rvvFile.getRvvplant(),rvvFile.getRvv31(),rvvFile.getRvv32(),rvvFile.getRvv33() ,rvvFile.getRvv34(),rvb.getRvb07().subtract(rvbFile.getRvb07()));
                        tlfFileMapper.updateQty(rvvFile.getRvvplant(),rvb.getRvb01(),rvb.getRvb02(), rvvFile.getRvv01(),rvvFile.getRvv02(),rvb.getRvb07().subtract(rvbFile.getRvb07()),rvb.getRvb07().subtract(rvbFile.getRvb07()));
                    }
                    //更新入库单数量，金额
                    rvvFileMapper.updateQty(rvvFile.getRvvplant(), rvvFile.getRvv01(),rvvFile.getRvv02(),rvb.getRvb07());
                    //出货单异动表
                    tlfFileMapper.updateQty(rvvFile.getRvvplant(),rvbFile.getRvb04(),rvbFile.getRvb03(), rvb.getRvb01(),rvb.getRvb02(), rvb.getRvb07().subtract(rvbFile.getRvb07()), BigDecimal.ZERO);
                    rvb30 = rvb.getRvb07();
                }
                //更新收货单表
                rvbFileMapper.updateQty(rvb.getRvbplant(),rvb.getRvb01(),rvb.getRvb02(),rvb.getRvb07(),rvb30);
                //更新采购订单pmn50
                pmnFileMapper.updatePmn50(rvb.getRvbplant(),rvbFile.getRvb04(),rvbFile.getRvb03(),rvb.getRvb07().subtract(rvbFile.getRvb07()));

            }
        }
    }
}
