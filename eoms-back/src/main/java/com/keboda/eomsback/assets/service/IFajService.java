package com.keboda.eomsback.assets.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFajService {
    void alterDepartment(MultipartFile file, String centre) throws IOException;
}
