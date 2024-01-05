package com.keboda.eomsback.purchase.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.mapper.RvuFileMapper;
import com.keboda.eomsback.purchase.mapper.RvvFileMapper;
import com.keboda.eomsback.purchase.pojo.RvuFile;
import com.keboda.eomsback.purchase.pojo.RvvFile;
import com.keboda.eomsback.purchase.service.IRvuService;
import com.keboda.eomsback.stock.service.ITlfService;
import com.keboda.eomsback.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RvuServiceImpl implements IRvuService {

    @Resource
    private RvuFileMapper rvuFileMapper;
    @Resource
    private RvvFileMapper rvvFileMapper;
    @Resource
    private ITlfService iTlfService;

    @Override
    public Page<RvuFile> searchRvuListPageHelper(SearchVo searchVo) {
        return rvuFileMapper.searchRvuListPageHelper(searchVo);
    }

    @Override
    public List<RvvFile> searchRvvList(SearchVo searchVo) {
        return rvvFileMapper.searchRvvList(searchVo);
    }

    /**
     * 根据入库单号 修改rvu、rvv、tlf表相关的时间字段
     * @param rvuFile 要修改的入库单
     */
    @Override
    @Transactional
    public void alterRvuDate(RvuFile rvuFile) {
        rvuFileMapper.alterRvuDate(rvuFile);
        rvvFileMapper.alterRvvDate(rvuFile);
        if(rvuFile.getRvu00().equals("1")){
            iTlfService.alterByCode(rvuFile.getCentre(),rvuFile.getRvu02(),rvuFile.getRvu01(),"1",rvuFile.getRvu03(),null,null);
        }else{
            iTlfService.alterByCode(rvuFile.getCentre(),rvuFile.getRvu01(),rvuFile.getRvu02(),"-1",rvuFile.getRvu03(),null,null);
        }
    }
}
