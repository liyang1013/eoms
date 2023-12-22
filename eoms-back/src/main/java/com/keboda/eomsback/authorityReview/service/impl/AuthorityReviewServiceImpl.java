package com.keboda.eomsback.authorityReview.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.authorityReview.pojo.Permission;
import com.keboda.eomsback.authorityReview.pojo.Position;
import com.keboda.eomsback.authorityReview.service.IAuthorityReviewService;
import com.keboda.eomsback.authorityReview.service.IPermissionService;
import com.keboda.eomsback.authorityReview.service.IPositionService;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.service.IZxwService;
import com.keboda.eomsback.system.service.IZyService;
import com.keboda.eomsback.utils.ImportExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorityReviewServiceImpl implements IAuthorityReviewService {

    @Resource
    private IPermissionService iPermissionService;
    @Resource
    private IPositionService iPositionService;
    @Resource
    private IZyService iZyService;
    @Resource
    private IZxwService iZxwService;

    @Override
    public Page<AuthorityRecordsVo> searchRecordsListPageHelper(SearchVo searchVo) {
        Page<AuthorityRecordsVo> page = iPositionService.searchRecordsListPageHelper(searchVo);
        Map<String, AuthorityRecordsVo> map = new HashMap<>();
        page.getResult().forEach(e -> {
            if (!map.containsKey(e.getPermissioncode())) {
                AuthorityRecordsVo vo = iZyService.selectPermissionByCode(e.getPermissioncode());
                map.put(e.getPermissioncode(), vo);
            }
        });
        page.getResult().forEach(e -> {
            AuthorityRecordsVo vo = map.get(e.getPermissioncode());
            e.setPermissionname(vo.getPermissionname());
            e.setContainAmount(vo.getContainAmount());
        });
        return page;
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
                position.setPositioncode(positionCode);
                position.setPositionname(positionName);

                Integer uuid = iPositionService.selectByYearWithCode(position);
                position.setUuid(uuid);
                if (uuid == null) {
                    iPositionService.insertSelective(position);
                }

                for (Map<String, String> map : sheetData) {

                    Permission permission = new Permission();
                    permission.setUuid(position.getUuid());
                    permission.setPermissioncode(map.get("作业代码"));

                    if (permission.getPermissioncode() != null && !permission.getPermissioncode().isEmpty()) {

                        AuthorityRecordsVo authorityRecordsVo = iZyService.selectPermissionByCode(permission.getPermissioncode());
                        permission.setPermissionname(authorityRecordsVo.getPermissionname());

                        Permission isExit = iPermissionService.selectByKey(permission);
                        if (isExit == null) iPermissionService.insertSelective(permission);
                    }
                }

                successCount++;
                successName.append(" ").append(positionName);

            } else {
                failCount++;
                failName.append(" ").append(positionName);
            }
        }
        sb.append("导入成功：").append(successCount).append(successName).append(" ;导入失败：").append(failCount).append(failName);
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

            sb.append("职位：").append(current.get(0).getPositionname());

            for (AuthorityRecordsVo vo : current) {
                vo.setColor("green");
                for (AuthorityRecordsVo record : records) {
                    if (vo.getPermissioncode().equals(record.getPermissioncode())) {
                        vo.setColor("white");
                        records.remove(record);
                        break;
                    }
                }
            }
            for (AuthorityRecordsVo record : records) {
                AuthorityRecordsVo authorityRecordsVo = iZyService.selectPermissionByCode(record.getPermissioncode());
                record.setContainAmount(authorityRecordsVo.getContainAmount());
                record.setColor("red");
                current.add(record);
            }
            sb.append(" 新增权限：").append(current.stream().filter(e -> e.getColor().equals("green")).count());
            sb.append(" 移除权限：").append(current.stream().filter(e -> e.getColor().equals("red")).count());
            map.put(sb.toString(), current);
        }
        return map;
    }

    @Override
    public void currentPermissionExcel(HttpServletResponse response) throws IOException {

        List<AuthorityRecordsVo> list = iZxwService.searchRecordsList(null);
        Map<String, List<AuthorityRecordsVo>> map = list.stream().collect(Collectors.groupingBy(AuthorityRecordsVo::getPositionname));
        ExcelWriter writer = ExcelUtil.getWriter(true);

        int i = 0;
        for (String sheetName : map.keySet()) {
            List<AuthorityRecordsVo> vo = map.get(sheetName);

            sheetName = sheetName.replace("/", "、");

            if (i == 0) writer.renameSheet(sheetName);
            else writer.setSheet(sheetName);

            writer.addHeaderAlias("positioncode", "职位代码");
            writer.addHeaderAlias("positionname", "职位");
            writer.addHeaderAlias("permissioncode", "作业代码");
            writer.addHeaderAlias("permissionname", "作业名称");
            writer.addHeaderAlias("containAmount", "是否有金额");

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
}
