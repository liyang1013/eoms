package com.keboda.eomsback.system.controller;

import com.keboda.eomsback.system.service.IGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gen")
public class GenController {

    @Autowired
    private IGenService iGenService;

}
