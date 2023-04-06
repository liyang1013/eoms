package com.keboda.eomsback.base.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.base.pojo.ImaFile;
import com.keboda.eomsback.common.SearchVo;
import com.keboda.eomsback.common.pojo.ImgFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IImaService {

    List<ImgFile> imgArr(String ima01, String centre);

    Page<ImaFile> searchImaList(SearchVo searchVo);

    void alterStockParameter(MultipartFile file,String centre) throws IOException;
}
