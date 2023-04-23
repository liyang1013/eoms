package com.keboda.eomsback.stock.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.stock.pojo.ImaFile;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.ImgFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImaService {

    List<ImgFile> imgArr(String ima01, String centre);

    Page<ImaFile> searchImaList(SearchVo searchVo);

    void alterStockParameter(MultipartFile file,String centre) throws IOException;

    void imaPackage(MultipartFile file, String centre) throws IOException;
}
