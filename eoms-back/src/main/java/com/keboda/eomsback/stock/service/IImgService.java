package com.keboda.eomsback.stock.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.base.pojo.SearchVo;
import com.keboda.eomsback.stock.pojo.ImgFile;

public interface IImgService {
    Page<ImgFile> searchImgList(SearchVo searchVo);
}
