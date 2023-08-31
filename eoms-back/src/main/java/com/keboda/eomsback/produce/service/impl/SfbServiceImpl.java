package com.keboda.eomsback.produce.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.produce.pojo.vo.SfbModify;
import com.keboda.eomsback.produce.pojo.vo.SfbStatusVo;
import com.keboda.eomsback.produce.service.ISfbService;
import com.keboda.eomsback.produce.mapper.*;
import com.keboda.eomsback.produce.pojo.*;
import com.keboda.eomsback.stock.mapper.TlfFileMapper;
import com.keboda.eomsback.stock.service.ITlfService;
import com.keboda.eomsback.system.mapper.SmaFileMapper;
import com.keboda.eomsback.system.pojo.SmaFile;
import com.keboda.eomsback.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SfbServiceImpl implements ISfbService {

    @Resource
    private SfbFileMapper sfbFileMapper;
    @Resource
    private SfaFileMapper sfaFileMapper;
    @Resource
    private SfpFileMapper sfpFileMapper;
    @Resource
    private ShbFileMapper shbFileMapper;
    @Resource
    private QcfFileMapper qcfFileMapper;
    @Resource
    private SfuFileMapper sfuFileMapper;
    @Resource
    private SmaFileMapper smaFileMapper;
    @Resource
    private TlfFileMapper tlfFileMapper;
    @Resource
    private ITlfService iTlfService;

    /*
     查询工单单号,每次100条
     */
    @Override
    public List<SfbFile> searchSfbList(SearchVo searchVo) {
        return sfbFileMapper.searchSfbList(searchVo);
    }

    @Override
    public SfbStatusVo searchSfbStatus(SearchVo searchVo) throws RuntimeException {

        SfbFile sfb = sfbFileMapper.searchSfb(searchVo);
        if (sfb == null) throw new RuntimeException("查询不到对应工单信息");
        List<SfpFile> sfp = sfpFileMapper.searchSfpList(searchVo);
        List<ShbFile> shb = shbFileMapper.searchShbList(searchVo);
        List<QcfFile> qcf = qcfFileMapper.searchQcfList(searchVo);
        List<SfuFile> sfu = sfuFileMapper.searchSfuList(searchVo);

        return SfbStatusVo.builder().sfb(sfb).sfp(sfp).shb(shb).qcf(qcf).sfu(sfu).build();
    }

    @Override
    public Page<SfbFile> searchSfbListPageHelper(SearchVo searchVo) {
        return sfbFileMapper.searchSfbListPageHelper(searchVo);
    }

    @Override
    @Transactional
    public void modifySfbDate(SfbModify sfbModify) {

        Date ddate = sfbModify.getDdate();
        //是否记账，记账了修改日期是否超过关帐日期
        SmaFile sma = smaFileMapper.select(sfbModify.getCentre());
        if (sma.getSma53().compareTo(ddate) > 0)
            throw new RuntimeException("修改日期成本已关帐,请重新设置关帐日期asmp620");

        for (SfbFile sfbFile : sfbModify.getSfbArr()) {

            SearchVo searchVo = new SearchVo();
            searchVo.setCentre(sfbModify.getCentre());
            searchVo.setCode(sfbFile.getSfb01());
            SfbFile sfb = sfbFileMapper.searchSfb(searchVo);
            if (sfb == null) throw new RuntimeException("查询不到对应工单信息");

            //工单日期修改
            if ((sfb.getSfb81() != null && ((sfbModify.getFlag() ? sfb.getSfb81().compareTo(ddate) < 0: sfb.getSfb81().compareTo(ddate) > 0)))
                    || (sfb.getSfb13() != null && (sfbModify.getFlag() ? sfb.getSfb13().compareTo(ddate) < 0 : sfb.getSfb13().compareTo(ddate) > 0))
                    || (sfb.getSfb15() != null && (sfbModify.getFlag() ? sfb.getSfb15().compareTo(ddate) < 0 : sfb.getSfb15().compareTo(ddate) > 0))
                    || (sfb.getSfb25() != null && (sfbModify.getFlag() ? sfb.getSfb25().compareTo(ddate) < 0 : sfb.getSfb25().compareTo(ddate) > 0))
                    || (sfb.getSfb36() != null && (sfbModify.getFlag() ? sfb.getSfb36().compareTo(ddate) < 0 : sfb.getSfb36().compareTo(ddate) > 0))
                    || (sfb.getSfb38() != null && (sfbModify.getFlag() ? sfb.getSfb38().compareTo(ddate) < 0 : sfb.getSfb38().compareTo(ddate) > 0))
            ) sfbFileMapper.updateDate(sfb, sfbModify.getCentre(), ddate, sfbModify.getFlag());

            //发料日期修改
            List<SfpFile> sfpArr = sfpFileMapper.searchSfpList(searchVo);
            if (sfpArr.size() > 0) {
                for (SfpFile sfp : sfpArr) {
                    if ((sfp.getSfp02() != null && (sfbModify.getFlag() ? sfp.getSfp02().compareTo(ddate) < 0 : sfp.getSfp02().compareTo(ddate) > 0))
                            || (sfp.getSfp03() != null && (sfbModify.getFlag() ?  sfp.getSfp03().compareTo(ddate) < 0 : sfp.getSfp03().compareTo(ddate) > 0))
                    ) {
                        sfpFileMapper.updateDate(sfp, sfbModify.getCentre(), ddate, sfbModify.getFlag());
                    }
                    //发料异动记录
                    if (sfp.getTlf06() != null && ((sfbModify.getFlag() ? sfp.getTlf06().compareTo(ddate) < 0 : sfp.getTlf06().compareTo(ddate) > 0))) {
                        tlfFileMapper.updateDate(sfb.getSfb01(), sfp.getSfp01(), sfbModify.getCentre(), ddate);//只修改了发料单
                    }
                }
            }

            //报工日期修改
            List<ShbFile> shbArr = shbFileMapper.searchShbList(searchVo);
            if (shbArr.size() > 0) {
                for (ShbFile shb : shbArr) {
                    if ((shb.getShb02() != null && (sfbModify.getFlag() ?  shb.getShb02().compareTo(ddate) < 0 : shb.getShb02().compareTo(ddate) > 0))
                            || (shb.getShb03() != null && (sfbModify.getFlag() ? shb.getShb03().compareTo(ddate) < 0 : shb.getShb03().compareTo(ddate) > 0))
                            || (shb.getShb32() != null && (sfbModify.getFlag() ? shb.getShb32().compareTo(ddate) < 0 : shb.getShb32().compareTo(ddate) > 0))
                    ) {
                        shbFileMapper.updateDate(shb, sfbModify.getCentre(), ddate, sfbModify.getFlag());
                    }
                }
            }

            // FQC日期修改
            List<QcfFile> qcfArr = qcfFileMapper.searchQcfList(searchVo);
            if (qcfArr.size() > 0) {
                for (QcfFile qcf : qcfArr) {
                    if ((qcf.getQcf04() != null && (sfbModify.getFlag() ? qcf.getQcf04().compareTo(ddate) < 0 : qcf.getQcf04().compareTo(ddate) > 0))
                            || (qcf.getQcf15() != null && (sfbModify.getFlag() ? qcf.getQcf15().compareTo(ddate) < 0 : qcf.getQcf15().compareTo(ddate) > 0))
                    ) {
                        qcfFileMapper.updateDate(qcf, sfbModify.getCentre(), ddate, sfbModify.getFlag());
                    }
                }
            }

            //入库日期修改
            List<SfuFile> sfuArr = sfuFileMapper.searchSfuList(searchVo);
            if (sfuArr.size() > 0) {
                for (SfuFile sfu : sfuArr) {
                    if ((sfu.getSfu02() != null && (sfbModify.getFlag() ? sfu.getSfu02().compareTo(ddate) < 0 : sfu.getSfu02().compareTo(ddate) > 0))
                            || (sfu.getSfu14() != null && (sfbModify.getFlag() ? sfu.getSfu14().compareTo(ddate) < 0 : sfu.getSfu14().compareTo(ddate) > 0))
                    ) {
                        sfuFileMapper.updateDate(sfu, sfbModify.getCentre(), ddate, sfbModify.getFlag());
                    }
                    //入库异动记录
                    if (sfu.getTlf06() != null && (sfbModify.getFlag() ? sfu.getTlf06().compareTo(ddate) < 0 : sfu.getTlf06().compareTo(ddate) > 0)) {
                        tlfFileMapper.updateDate(sfb.getSfb01(), sfu.getSfu01(), sfbModify.getCentre(), ddate);
                    }
                }
            }

        }
    }

    @Override
    @Transactional
    public void closeOut(SfbModify sfbModify) {
        for (SfbFile sfb : sfbModify.getSfbArr()) {
            sfbFileMapper.closeOut(sfbModify.getCentre(),sfb.getSfb01(), DateUtils.parseDate(DateUtils.formatDate(new Date())));
        }
    }

    @Override
    public List<SfaFile> searchSfaList(SearchVo searchVo) {
        return sfaFileMapper.searchSfaList(searchVo);
    }
}
