package com.keboda.eomsback.common.service;

import com.keboda.eomsback.common.pojo.ImaFile;
import com.keboda.eomsback.common.pojo.ImgFile;

import java.util.List;

public interface IImaService {
    List<ImaFile> imaArr(String queryStr, String centre);

    List<ImgFile> imgArr(String ima01, String centre);
}
