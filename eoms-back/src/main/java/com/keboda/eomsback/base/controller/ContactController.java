package com.keboda.eomsback.base.controller;

import com.keboda.eomsback.base.pojo.Contact;
import com.keboda.eomsback.base.service.IContactService;
import com.keboda.eomsback.base.pojo.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private IContactService iContactService;

    @RequestMapping("/searchContact")
    public BaseResult searchContact(@RequestBody Contact contact){
        List<Contact> list = iContactService.searchContact(contact);
        return BaseResult.success(list);
    }

}
