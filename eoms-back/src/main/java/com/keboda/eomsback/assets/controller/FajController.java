package com.keboda.eomsback.assets.controller;

import com.keboda.eomsback.assets.service.IFajService;
import com.keboda.eomsback.entity.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/faj")
public class FajController {

    @Autowired
    private IFajService iFajService;

    @RequestMapping("/alterDepartment")
    public BaseResult alterDepartment(MultipartFile file, String centre) throws IOException {
        iFajService.alterDepartment(file,centre);
        return BaseResult.success("固定资产部门批量修改成功");
    }
}
