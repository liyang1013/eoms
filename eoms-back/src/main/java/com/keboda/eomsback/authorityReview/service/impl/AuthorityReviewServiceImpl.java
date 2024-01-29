package com.keboda.eomsback.authorityReview.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.authorityReview.pojo.Permission;
import com.keboda.eomsback.authorityReview.pojo.Position;
import com.keboda.eomsback.authorityReview.service.IAuthorityReviewService;
import com.keboda.eomsback.authorityReview.service.IPermissionService;
import com.keboda.eomsback.authorityReview.service.IPositionService;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.GazFile;
import com.keboda.eomsback.system.pojo.ZwFile;
import com.keboda.eomsback.system.service.IGazService;
import com.keboda.eomsback.system.service.IZwService;
import com.keboda.eomsback.system.service.IZxwService;
import com.keboda.eomsback.system.service.IZyService;
import com.keboda.eomsback.utils.ImportExcelUtil;
import com.keboda.eomsback.utils.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service
public class AuthorityReviewServiceImpl implements IAuthorityReviewService {

    @Resource
    private IPermissionService iPermissionService;
    @Resource
    private IPositionService iPositionService;
    @Resource
    private IZxwService iZxwService;
    @Resource
    private IGazService iGazService;
    @Resource
    private IZwService iZwService;

    @Override
    public Page<AuthorityRecordsVo> searchRecordsListPageHelper(SearchVo searchVo) {
        return iPositionService.searchRecordsListPageHelper(searchVo);
    }

    @Override
    @DSTransactional
    public String importRecords(MultipartFile file, String year) throws IOException {

        if (year == null || year.isEmpty() || year.equals("null")) throw new RuntimeException("导入目标年份不可为空");
        StringBuilder sb = new StringBuilder();
        StringBuilder failName = new StringBuilder();
        StringBuilder successName = new StringBuilder();
        int successCount = 0;
        int failCount = 0;

        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());

        for (int i = 0; i < reader.getSheets().size(); i++) {

            String positionName = reader.getSheets().get(i).getSheetName();
            String positionCode = iZxwService.selectPositionCodeByName(positionName);

            List<Map<String, String>> sheetData = ImportExcelUtil.getSheetData(reader.getSheets().get(i));

            if (positionCode != null && sheetData.size() > 0) {

                Position position = new Position();
                position.setYear(Integer.valueOf(year));
                position.setPositionCode(positionCode);
                position.setPositionName(positionName);

                Integer uuid = iPositionService.selectByYearWithCode(position);
                position.setUuid(uuid);
                if (uuid == null) {
                    iPositionService.insertSelective(position);
                }

                for (Map<String, String> map : sheetData) {

                    Permission permission = new Permission();
                    permission.setUuid(position.getUuid());
                    permission.setPermissionCode(map.get("作业代码"));

                    if (permission.getPermissionCode() != null && !permission.getPermissionCode().isEmpty()) {

                        GazFile gazFile = iGazService.selectByKey(permission.getPermissionCode());
                        if (gazFile != null) {

                            permission.setPermissionName(gazFile.getGaz03());
                            permission.setContainAmount(StringUtils.formatBoolean(map.get("金额")));
                            permission.setContainCreate(StringUtils.formatBoolean(map.get("新增")));
                            permission.setContainDelete(StringUtils.formatBoolean(map.get("删除")));
                            permission.setContainUpdate(StringUtils.formatBoolean(map.get("修改")));
                            permission.setContainRead(StringUtils.formatBoolean(map.get("查询")));
                            permission.setContainConfirm(StringUtils.formatBoolean(map.get("审核")));
                            permission.setContainUnConfirm(StringUtils.formatBoolean(map.get("撤销审核")));
                            permission.setContainVoid(StringUtils.formatBoolean(map.get("作废")));
                            permission.setContainUnVoid(StringUtils.formatBoolean(map.get("撤销作废")));
                            permission.setContainPost(StringUtils.formatBoolean(map.get("过账")));
                            permission.setContainUnPost(StringUtils.formatBoolean(map.get("撤销过账")));
                            permission.setContainPrint(StringUtils.formatBoolean(map.get("打印")));
                            permission.setContainExport(StringUtils.formatBoolean(map.get("数据导出")));

                            Permission isExit = iPermissionService.selectByKey(permission);
                            if (isExit == null) iPermissionService.insertSelective(permission);
                            else iPermissionService.updateSelective(permission);
                        }
                    }
                }

                successCount++;
                successName.append("  ").append(positionName);

            } else {
                failCount++;
                failName.append("  ").append(positionName);
            }
        }
        sb.append("导入成功:  ").append(successCount).append(successName).append("  ;导入失败:  ").append(failCount).append(failName);
        return sb.toString();
    }

    @Override
    public void deleteRecords(List<AuthorityRecordsVo> authorityRecordsVoList) {
        iPermissionService.deleteRecords(authorityRecordsVoList);
    }

    @Override
    public Map<String, List<AuthorityRecordsVo>> contrastRecords(SearchVo searchVo) {

        Map<String, List<AuthorityRecordsVo>> map = new HashMap<>();

        for (String code : searchVo.getCodes()) {
            StringBuilder sb = new StringBuilder();

            List<AuthorityRecordsVo> current = iZxwService.searchRecordsList(code);
            List<AuthorityRecordsVo> records = iPermissionService.searchRecordsList(searchVo.getYear(), code);

            sb.append("职位：").append(current.get(0).getPositionName());

            for (AuthorityRecordsVo vo : current) {
                vo.setColor("green");
                for (AuthorityRecordsVo record : records) {
                    if (vo.getPermissionCode().equals(record.getPermissionCode())) {

                        if (!vo.getIsCreate().equals(record.getIsCreate())) {
                            vo.setIsCreate(record.getIsCreate() + " => " + vo.getIsCreate());
                        }
                        if (!vo.getIsDelete().equals(record.getIsDelete())) {
                            vo.setIsDelete(record.getIsDelete() + " => " + vo.getIsDelete());
                        }
                        if (!vo.getIsUpdate().equals(record.getIsUpdate())) {
                            vo.setIsUpdate(record.getIsUpdate() + " => " + vo.getIsUpdate());
                        }
                        if (!vo.getIsRead().equals(record.getIsRead())) {
                            vo.setIsRead(record.getIsRead() + " => " + vo.getIsRead());
                        }
                        if (!vo.getIsConfirm().equals(record.getIsConfirm())) {
                            vo.setIsConfirm(record.getIsConfirm() + " => " + vo.getIsConfirm());
                        }
                        if (!vo.getIsUnConfirm().equals(record.getIsUnConfirm())) {
                            vo.setIsUnConfirm(record.getIsUnConfirm() + " => " + vo.getIsUnConfirm());
                        }
                        if (!vo.getIsVoid().equals(record.getIsVoid())) {
                            vo.setIsVoid(record.getIsVoid() + " => " + vo.getIsVoid());
                        }
                        if (!vo.getIsUnVoid().equals(record.getIsUnVoid())) {
                            vo.setIsUnVoid(record.getIsUnVoid() + " => " + vo.getIsUnVoid());
                        }
                        if (!vo.getIsPost().equals(record.getIsPost())) {
                            vo.setIsPost(record.getIsPost() + " => " + vo.getIsPost());
                        }
                        if (!vo.getIsUnPost().equals(record.getIsUnPost())) {
                            vo.setIsUnPost(record.getIsUnPost() + " => " + vo.getIsUnPost());
                        }
                        if (!vo.getIsExport().equals(record.getIsExport())) {
                            vo.setIsExport(record.getIsExport() + " => " + vo.getIsExport());
                        }
                        if (!vo.getIsPrint().equals(record.getIsPrint())) {
                            vo.setIsPrint(record.getIsPrint() + " => " + vo.getIsPrint());
                        }
                        vo.setColor("white");
                        records.remove(record);
                        break;
                    }
                }
            }

            current.stream().filter(e -> e.getIsCreate().length() > 1 ||
                    e.getIsDelete().length() > 1 || e.getIsUpdate().length() > 1 || e.getIsRead().length() > 1 ||
                    e.getIsPost().length() > 1 || e.getIsUnPost().length() > 1 || e.getIsVoid().length() > 1 ||
                    e.getIsUnVoid().length() > 1 || e.getIsConfirm().length() > 1 || e.getIsUnConfirm().length() > 1 ||
                    e.getIsPrint().length() > 1 || e.getIsExport().length() > 1).forEach(e -> e.setColor("yellow"));
            for (AuthorityRecordsVo record : records) {
                record.setColor("red");
                current.add(record);
            }
            sb.append(" 新增权限：").append(current.stream().filter(e -> e.getColor().equals("green")).count());
            sb.append(" 修改权限：").append(current.stream().filter(e -> e.getColor().equals("yellow")).count());
            sb.append(" 移除权限：").append(current.stream().filter(e -> e.getColor().equals("red")).count());
            map.put(sb.toString(), current);
        }
        return map;
    }

    @Override
    public void currentPermissionExcel(HttpServletResponse response) throws IOException {

        List<AuthorityRecordsVo> list = iZxwService.searchRecordsList(null);
        Map<String, List<AuthorityRecordsVo>> map = list.stream().collect(Collectors.groupingBy(AuthorityRecordsVo::getPositionName));
        ExcelWriter writer = ExcelUtil.getWriter(true);

        int i = 0;
        for (String sheetName : map.keySet()) {
            List<AuthorityRecordsVo> vo = map.get(sheetName);

            sheetName = sheetName.replace("/", "、");

            if (i == 0) writer.renameSheet(sheetName);
            else writer.setSheet(sheetName);

            writer.addHeaderAlias("positionCode", "职位代码");
            writer.addHeaderAlias("positionName", "职位");
            writer.addHeaderAlias("permissionCode", "作业代码");
            writer.addHeaderAlias("permissionName", "作业名称");
            writer.addHeaderAlias("isAmount", "金额");
            writer.addHeaderAlias("isCreate", "新增");
            writer.addHeaderAlias("isDelete", "删除");
            writer.addHeaderAlias("isUpdate", "更新");
            writer.addHeaderAlias("isRead", "查询");
            writer.addHeaderAlias("isConfirm", "审核");
            writer.addHeaderAlias("isUnConfirm", "撤销审核");
            writer.addHeaderAlias("isVoid", "作废");
            writer.addHeaderAlias("isUnVoid", "撤销作废");
            writer.addHeaderAlias("isPost", "过账");
            writer.addHeaderAlias("isUnPost", "撤销过账");
            writer.addHeaderAlias("isPrint", "打印");
            writer.addHeaderAlias("isExport", "数据导出");

            writer.setOnlyAlias(true);
            writer.write(vo, true);
            i++;
        }


        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=现行权限档案.xlsx");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
    }

    @Override
    public void contrastPermissionExcel(HttpServletResponse response, String year) {

        if (year == null || year.isEmpty() || year.equals("null")) throw new RuntimeException("目标年份不可为空");

        Integer iyear = Integer.valueOf(year);

        List<AuthorityRecordsVo> list = new ArrayList<>();
        List<ZwFile> zw = iZwService.searchZwList(null);

        List<Thread> threadSet = new ArrayList<>();

        for (ZwFile zwFile : zw) {
            Thread thread = new Thread(() ->{
                list.addAll(iZxwService.searchRecordsList(zwFile.getZw01()));
            });
            thread.start();
            threadSet.add(thread);

        }
        for (Thread thread : threadSet) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        Map<String, List<AuthorityRecordsVo>> map = list.stream().collect(Collectors.groupingBy(AuthorityRecordsVo::getPositionName));
        ExcelWriter writer = ExcelUtil.getWriter(true);


        int i = 0;
        for (String sheetName : map.keySet()) {
            List<AuthorityRecordsVo> current = map.get(sheetName);

            sheetName = sheetName.replace("/", "、");

            if (i == 0) writer.renameSheet(sheetName);
            else writer.setSheet(sheetName);

            List<AuthorityRecordsVo> records = iPermissionService.searchRecordsList(iyear, current.get(0).getPositionCode());

            for (AuthorityRecordsVo vo : current) {
                vo.setColor("green");
                for (AuthorityRecordsVo record : records) {
                    if (vo.getPermissionCode().equals(record.getPermissionCode())) {

                        if (!vo.getIsCreate().equals(record.getIsCreate())) {
                            vo.setIsCreate(record.getIsCreate() + " => " + vo.getIsCreate());
                        }
                        if (!vo.getIsDelete().equals(record.getIsDelete())) {
                            vo.setIsDelete(record.getIsDelete() + " => " + vo.getIsDelete());
                        }
                        if (!vo.getIsUpdate().equals(record.getIsUpdate())) {
                            vo.setIsUpdate(record.getIsUpdate() + " => " + vo.getIsUpdate());
                        }
                        if (!vo.getIsRead().equals(record.getIsRead())) {
                            vo.setIsRead(record.getIsRead() + " => " + vo.getIsRead());
                        }
                        if (!vo.getIsConfirm().equals(record.getIsConfirm())) {
                            vo.setIsConfirm(record.getIsConfirm() + " => " + vo.getIsConfirm());
                        }
                        if (!vo.getIsUnConfirm().equals(record.getIsUnConfirm())) {
                            vo.setIsUnConfirm(record.getIsUnConfirm() + " => " + vo.getIsUnConfirm());
                        }
                        if (!vo.getIsVoid().equals(record.getIsVoid())) {
                            vo.setIsVoid(record.getIsVoid() + " => " + vo.getIsVoid());
                        }
                        if (!vo.getIsUnVoid().equals(record.getIsUnVoid())) {
                            vo.setIsUnVoid(record.getIsUnVoid() + " => " + vo.getIsUnVoid());
                        }
                        if (!vo.getIsPost().equals(record.getIsPost())) {
                            vo.setIsPost(record.getIsPost() + " => " + vo.getIsPost());
                        }
                        if (!vo.getIsUnPost().equals(record.getIsUnPost())) {
                            vo.setIsUnPost(record.getIsUnPost() + " => " + vo.getIsUnPost());
                        }
                        if (!vo.getIsExport().equals(record.getIsExport())) {
                            vo.setIsExport(record.getIsExport() + " => " + vo.getIsExport());
                        }
                        if (!vo.getIsPrint().equals(record.getIsPrint())) {
                            vo.setIsPrint(record.getIsPrint() + " => " + vo.getIsPrint());
                        }
                        vo.setColor("white");
                        records.remove(record);
                        break;
                    }
                }
            }

            current.stream().filter(e -> e.getIsCreate().length() > 1 ||
                    e.getIsDelete().length() > 1 || e.getIsUpdate().length() > 1 || e.getIsRead().length() > 1 ||
                    e.getIsPost().length() > 1 || e.getIsUnPost().length() > 1 || e.getIsVoid().length() > 1 ||
                    e.getIsUnVoid().length() > 1 || e.getIsConfirm().length() > 1 || e.getIsUnConfirm().length() > 1 ||
                    e.getIsPrint().length() > 1 || e.getIsExport().length() > 1).forEach(e -> e.setColor("yellow"));
            for (AuthorityRecordsVo record : records) {
                record.setColor("red");
                current.add(record);
            }

            writer.addHeaderAlias("permissionCode", "作业代码");
            writer.addHeaderAlias("permissionName", "作业名称");
            writer.addHeaderAlias("isAmount", "金额");
            writer.addHeaderAlias("isCreate", "新增");
            writer.addHeaderAlias("isDelete", "删除");
            writer.addHeaderAlias("isUpdate", "更新");
            writer.addHeaderAlias("isRead", "查询");
            writer.addHeaderAlias("isConfirm", "审核");
            writer.addHeaderAlias("isUnConfirm", "撤销审核");
            writer.addHeaderAlias("isVoid", "作废");
            writer.addHeaderAlias("isUnVoid", "撤销作废");
            writer.addHeaderAlias("isPost", "过账");
            writer.addHeaderAlias("isUnPost", "撤销过账");
            writer.addHeaderAlias("isPrint", "打印");
            writer.addHeaderAlias("isExport", "数据导出");

            writer.setOnlyAlias(true);
            writer.write(current, true);

            int col = 17;
            current.forEach(e -> {

                if (e.getColor().equals("red")) {

                    CellStyle cellStyle = writer.createCellStyle(0, current.indexOf(e) + 1);
                    cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                }
                if (e.getColor().equals("green")) {

                        CellStyle cellStyle = writer.createCellStyle(0, current.indexOf(e) + 1);
                        cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                }
                if (e.getColor().equals("yellow")) {

                        CellStyle cellStyle = writer.createCellStyle(0, current.indexOf(e) + 1);

                        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                }

            });

            i++;
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=现行权限档案.xlsx");

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);

    }


}
