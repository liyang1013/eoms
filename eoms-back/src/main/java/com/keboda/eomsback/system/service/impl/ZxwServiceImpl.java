package com.keboda.eomsback.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.enums.CentreOa;
import com.keboda.eomsback.system.mapper.ZxwFileMapper;
import com.keboda.eomsback.system.pojo.*;
import com.keboda.eomsback.system.service.IHrmResourceService;
import com.keboda.eomsback.system.service.IZxwService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZxwServiceImpl implements IZxwService {

    @Resource
    private ZxwFileMapper zxwFileMapper;
    @Resource
    private IHrmResourceService iHrmResourceService;


    @Override
    public Page<ZxwFile> searchZxwListPageHelper(SearchVo searchVo) {
        return zxwFileMapper.searchZxwListPageHelper(searchVo);
    }

    @Override
    public void authorityReview() {

        for (CentreOa centre : CentreOa.values()) {

            String code = centre.getCode();
            String name = centre.getName();
            String ds = centre.name();

            List<HrmResource> hr = iHrmResourceService.selectByDepartCode(code);
            Map<String, List<HrmResource>> groupByDep = hr.stream().collect(Collectors.groupingBy(HrmResource::getDepname));
            for (String departName : groupByDep.keySet()) {
                List<HrmResource> HrGroupByDep = groupByDep.get(departName);
                departName = departName.replace("/", "-");//去除非法字符串

                StringBuilder sb = new StringBuilder();
                HrGroupByDep.forEach(e -> sb.append("'").append(e.getWorkcode()).append("'").append(","));
                sb.deleteCharAt(sb.length() - 1);

                List<String> power = new ArrayList<>();
                List<Department> department = zxwFileMapper.searchDepartmentByWorkCode(sb.toString());
                for (Department e : department) {
                    e.setCentre(name);
                    e.setDepname(departName);
                    if(e.getZw02() != null)e.setZw02(e.getZw02().replace(",", "\r\n"));
                    if(e.getAzp02() != null )e.setAzp02(e.getAzp02().replace(",", "\r\n"));

                    String[] zw = e.getZw01().split(",");

                    for (String s : zw) {
                        if (!power.contains(s)) {
                            power.add(s);
                        }
                    }
                }
                if (department.size() > 0) {

                    String search = "";
                    String alterRvuDatesearch = "";
                    for (String s : power) {
                        search += "'" + s + "',";
                    }
                    search = search.substring(0, search.length() - 1);

                    ExcelWriter writer = ExcelUtil.getWriter("d:/erp/"+name+"/" + departName + ".xlsx", name + departName + "岗位");

                    writer.addHeaderAlias("zx01", "工号");
                    writer.addHeaderAlias("zx02", "姓名");
                    writer.addHeaderAlias("centre", "中心");
                    writer.addHeaderAlias("depname", "部门");
                    writer.addHeaderAlias("zw02", "岗位");
                    writer.addHeaderAlias("azp02", "账套");

                    writer.setOnlyAlias(true);
                    writer.write(department, true);


                    List<Authority> powerList = zxwFileMapper.authorityReview(search);
                    Map<String, List<Authority>> map = powerList.stream().collect(Collectors.groupingBy(Authority::getZw02));

                    for (String zw : map.keySet()) {

                        List<Authority> zy = map.get(zw);

                        int index = 1;
                        for (Authority authority : zy) {
                            authority.setIndex(index);
                            index++;
                        }

                        writer.setSheet(zw);

                        writer.addHeaderAlias("index", "序号");
                        writer.addHeaderAlias("zy02", "作业代码");
                        writer.addHeaderAlias("gaz03", "作业名称");
                        writer.addHeaderAlias("includeAmount", "是否有金额");
                        writer.addHeaderAlias("includeAdd", "新增");
                        writer.addHeaderAlias("includeQuery", "查询");
                        writer.addHeaderAlias("includeModify", "修改");
                        writer.addHeaderAlias("includeDelete", "删除");
                        writer.addHeaderAlias("includeConfirm", "审核");
                        writer.addHeaderAlias("includeUndoConfirm", "取消审核");
                        writer.addHeaderAlias("includeRelease", "发放");
                        writer.addHeaderAlias("includeUnRelease", "取消发放");
                        writer.addHeaderAlias("includeBlank", "作废");
                        writer.addHeaderAlias("includePost", "过账");
                        writer.addHeaderAlias("includeUndoPost", "过账还原");
                        writer.addHeaderAlias("includeInvoiceexport", "汇出EXCEL(数据导出）");
                        writer.addHeaderAlias("includePrint", "打印");

                        writer.setOnlyAlias(true);
                        writer.write(zy, true);

                    }
                    writer.close();
                }
            }
        }
    }
}
