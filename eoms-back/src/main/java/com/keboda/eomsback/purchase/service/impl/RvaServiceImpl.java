package com.keboda.eomsback.purchase.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.mapper.PmnFileMapper;
import com.keboda.eomsback.purchase.mapper.RvaFileMapper;
import com.keboda.eomsback.purchase.mapper.RvbFileMapper;
import com.keboda.eomsback.purchase.pojo.RvaFile;
import com.keboda.eomsback.purchase.pojo.RvbFile;
import com.keboda.eomsback.purchase.service.IRvaService;
import com.keboda.eomsback.stock.service.ITlfService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class RvaServiceImpl implements IRvaService {

    @Resource
    private RvaFileMapper rvaFileMapper;
    @Resource
    private RvbFileMapper rvbFileMapper;
    @Resource
    private PmnFileMapper pmnFileMapper;
    @Resource
    private ITlfService iTlfService;

    @Override
    public Page<RvaFile> searchRvaListPageHelper(SearchVo searchVo) {
        return rvaFileMapper.searchRvaListPageHelper(searchVo);
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
    @Transactional
    public void alterRvaConf(RvaFile rvaFile) {
        rvaFileMapper.alterRvaConf(rvaFile);
    }

    @Override
    @Transactional
    public void alterRvbQty(List<RvbFile> rvbFiles) {
        for (RvbFile rvb : rvbFiles) {
            RvbFile rvbFile = rvbFileMapper.selectByKey(rvb.getRvb01(),rvb.getRvb02(),rvb.getCentre());
            if(rvbFile == null) throw new RuntimeException("单号:"+rvb.getRvb01()+"在中心:"+rvb.getCentre()+"中查找不到对应收货单明细");
            //如果数量修改，更新数据库
            if(rvb.getRvb07().compareTo(rvbFile.getRvb07()) != 0){
                if(rvbFile.getRvb22() != null ) throw new RuntimeException("发票已生成不允许修改数量");
                if(rvbFile.getRvb30().compareTo(BigDecimal.ZERO) != 0 ) throw new RuntimeException("入库单已审核不允许修改数量");

                RvaFile rvaFile = rvaFileMapper.selectByKey(rvb.getRvb01(),rvb.getCentre());
                if(rvaFile == null) throw new RuntimeException("单号:"+rvb.getRvb01()+"在中心:"+rvb.getRvbplant()+"中查找不到对应收货单");
                //收货单审核，更新采购订单收货数量
                if( rvaFile.getRvaconf().equals("Y")){
                    pmnFileMapper.updatePmn50(rvb.getCentre(),rvbFile.getRvb04(),rvbFile.getRvb03(),rvb.getRvb07().subtract(rvbFile.getRvb07()));
                }
                //更新收货异动表
                iTlfService.alterByRow(rvb.getCentre(),rvbFile.getRvb04(),rvb.getRvb03(), rvb.getRvb01(),rvb.getRvb02(),rvb.getRvb07(),null,null);
                //更新收货单表
                rvbFileMapper.alterRvbQty(rvb.getCentre(),rvb.getRvb01(),rvb.getRvb02(),rvb.getRvb07());
            }
        }
    }

    @Override
    @Transactional
    public void alterRvaDate(RvaFile rvaFile) {
        rvaFileMapper.alterRvaDate(rvaFile);
        iTlfService.alterByCode(rvaFile.getCentre(),rvaFile.getRva02(),rvaFile.getRva01(),"0",rvaFile.getRva06(),null,null);
    }
}
