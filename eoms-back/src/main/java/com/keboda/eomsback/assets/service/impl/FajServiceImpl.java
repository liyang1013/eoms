package com.keboda.eomsback.assets.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.keboda.eomsback.assets.mapper.FajFileMapper;
import com.keboda.eomsback.assets.pojo.FajFile;
import com.keboda.eomsback.assets.service.IFajService;
import com.keboda.eomsback.stock.pojo.ImaFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class FajServiceImpl implements IFajService {

    @Resource
    private FajFileMapper fajFileMapper;

    @Override
    @Transactional
    public void alterDepartment(MultipartFile file, String centre) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<Map<String, Object>> read = reader.readAll();;
        for (Map<String, Object> map : read) {
            FajFile fajFile = new FajFile();
            fajFile.setCentre(centre);
            fajFile.setFaj02(map.get("财产编号").toString());
            if(map.get("保管部门代码") != null) {
                fajFile.setFaj20(map.get("保管部门代码").toString());
            }
            if(map.get("折旧部门代码") != null) {
                fajFile.setFaj24(map.get("折旧部门代码").toString());
            }
            fajFileMapper.updateByPrimaryKeySelective(fajFile);
        }
    }
}
