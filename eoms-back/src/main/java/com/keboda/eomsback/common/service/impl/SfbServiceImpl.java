package com.keboda.eomsback.common.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.common.mapper.*;
import com.keboda.eomsback.common.pojo.*;
import com.keboda.eomsback.common.pojo.vo.SfbModify;
import com.keboda.eomsback.common.pojo.vo.SfbProcessVo;
import com.keboda.eomsback.common.pojo.vo.SfbSearchVo;
import com.keboda.eomsback.common.service.ISfbService;
import com.keboda.eomsback.stock.mapper.TlfFileMapper;
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

    /*
     查询工单单号,每次100条
     */
    @Override
    public List<SfbFile> sfb01Arr(String queryStr, String centre) {
        return sfbFileMapper.sfb01Arr(queryStr, centre);
    }

    @Override
    public SfbProcessVo sfbProcess(String sfb01, String centre) throws RuntimeException {

        SfbFile sfb = sfbFileMapper.selectDateByCode(sfb01, centre);
        if (sfb == null) throw new RuntimeException("查询不到对应工单信息");
        List<SfpFile> sfp = sfpFileMapper.selectDateByCode(sfb01, centre);
        List<ShbFile> shb = shbFileMapper.selectDateByCode(sfb01, centre);
        List<QcfFile> qcf = qcfFileMapper.selectDateByCode(sfb01, centre);
        List<SfuFile> sfu = sfuFileMapper.selectDateByCode(sfb01, centre);

        return SfbProcessVo.builder().sfb(sfb).sfp(sfp).shb(shb).qcf(qcf).sfu(sfu).build();
    }

    @Override
    public Page<SfbFile> sfbArr(SfbSearchVo searchVo) {
        return sfbFileMapper.sfbArr(searchVo);
    }

    @Override
    @Transactional
    public void modifyDate(SfbModify sfbModify) {

        Date ddate = sfbModify.getDdate();
        //是否记账，记账了修改日期是否超过关帐日期
        SmaFile sma = smaFileMapper.select(sfbModify.getCentre());
        if (sma.getSma53().compareTo(ddate) > 0)
            throw new RuntimeException("修改日期成本已关帐,请重新设置关帐日期asmp620");

        for (SfbFile sfbFile : sfbModify.getSfbArr()) {

            SfbFile sfb = sfbFileMapper.selectDateByCode(sfbFile.getSfb01(), sfbModify.getCentre());
            if (sfb == null) throw new RuntimeException("查询不到对应工单信息");

            //工单日期修改
            if ((sfb.getSfb81() != null && sfb.getSfb81().compareTo(ddate) != 0)
                    || (sfb.getSfb13() != null && (sfbModify.getFlag() ? sfb.getSfb13().compareTo(ddate) < 0 : sfb.getSfb13().compareTo(ddate) > 0))
                    || (sfb.getSfb15() != null && (sfbModify.getFlag() ? sfb.getSfb15().compareTo(ddate) < 0 : sfb.getSfb15().compareTo(ddate) > 0))
                    || (sfb.getSfb25() != null && (sfbModify.getFlag() ? sfb.getSfb25().compareTo(ddate) < 0 : sfb.getSfb25().compareTo(ddate) > 0))
                    || (sfb.getSfb36() != null && (sfbModify.getFlag() ? sfb.getSfb36().compareTo(ddate) < 0 : sfb.getSfb36().compareTo(ddate) > 0))
                    || (sfb.getSfb38() != null && (sfbModify.getFlag() ? sfb.getSfb38().compareTo(ddate) < 0 : sfb.getSfb38().compareTo(ddate) > 0))
            ) sfbFileMapper.updateDate(sfb, sfbModify.getCentre(), ddate, sfbModify.getFlag());

            //发料日期修改
            List<SfpFile> sfpArr = sfpFileMapper.selectDateByCode(sfbFile.getSfb01(), sfbModify.getCentre());
            if (sfpArr.size() > 0) {
                for (SfpFile sfp : sfpArr) {
                    if ((sfp.getSfp02() != null && (sfbModify.getFlag() ? sfp.getSfp02().compareTo(ddate) < 0 : sfp.getSfp02().compareTo(ddate) > 0))
                            || (sfp.getSfp03() != null && (sfbModify.getFlag() ?  sfp.getSfp03().compareTo(ddate) < 0 : sfp.getSfp03().compareTo(ddate) > 0))
                    ) {
                        sfpFileMapper.updateDate(sfp, sfbModify.getCentre(), ddate, sfbModify.getFlag());
                    }
                    //发料异动记录
                    System.out.println(sfp.getTlf06() != null);
                    if (sfp.getTlf06() != null && ((sfbModify.getFlag() ? sfp.getTlf06().compareTo(ddate) < 0 : sfp.getTlf06().compareTo(ddate) > 0))) {
                        tlfFileMapper.updateDate(sfb.getSfb01(), sfp.getSfp01(), sfbModify.getCentre(), ddate);
                    }
                }
            }

            //报工日期修改
            List<ShbFile> shbArr = shbFileMapper.selectDateByCode(sfbFile.getSfb01(), sfbModify.getCentre());
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
            List<QcfFile> qcfArr = qcfFileMapper.selectDateByCode(sfbFile.getSfb01(), sfbModify.getCentre());
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
            List<SfuFile> sfuArr = sfuFileMapper.selectDateByCode(sfbFile.getSfb01(), sfbModify.getCentre());
            if (sfuArr.size() > 0) {
                for (SfuFile sfu : sfuArr) {
                    if ((sfu.getSfu02() != null && (sfbModify.getFlag() ? sfu.getSfu02().compareTo(ddate) < 0 : sfu.getSfu02().compareTo(ddate) > 0))
                            || (sfu.getSfu14() != null && (sfbModify.getFlag() ? sfu.getSfu14().compareTo(ddate) < 0 : sfu.getSfu14().compareTo(ddate) > 0))
                    ) {
                        sfuFileMapper.updateDate(sfu, sfbModify.getCentre(), ddate, sfbModify.getFlag());
                    }

                    //入库异动记录
                    if (sfu.getTlf06() != null && (sfbModify.getFlag() ? sfu.getTlf06().compareTo(ddate) < 0 : sfu.getTlf06().compareTo(ddate) > 0)) {
                        tlfFileMapper.updateDate(sfu.getSfu01(), sfb.getSfb01(), sfbModify.getCentre(), ddate);
                    }
                }
            }

        }
    }
}
