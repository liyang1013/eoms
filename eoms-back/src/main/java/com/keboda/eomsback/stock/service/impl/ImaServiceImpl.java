package com.keboda.eomsback.stock.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.github.pagehelper.Page;
import com.keboda.eomsback.stock.mapper.ImaFileMapper;
import com.keboda.eomsback.stock.pojo.ImaFile;
import com.keboda.eomsback.base.pojo.SearchVo;
import com.keboda.eomsback.stock.pojo.ImgFile;
import com.keboda.eomsback.stock.service.IImaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ImaServiceImpl implements IImaService {
    @Resource
    private ImaFileMapper imaFileMapper;

    @Override
    public List<ImgFile> imgArr(String ima01, String centre) {
        return imaFileMapper.imgArr(ima01,centre);
    }

    @Override
    public Page<ImaFile> searchImaList(SearchVo searchVo) {
        return imaFileMapper.searchImaList(searchVo);
    }

    @Override
    @Transactional
    public void alterStockParameter(MultipartFile file,String centre) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<Map<String,Object>> read = reader.readAll();
        for (Map<String, Object> map : read) {
            imaFileMapper.alterStockParameter(centre,map.get("料号").toString(),new BigDecimal(map.get("安全库存").toString()),new BigDecimal(map.get("最低库存").toString()),new BigDecimal(map.get("最高库存").toString()));
        }
    }
}
