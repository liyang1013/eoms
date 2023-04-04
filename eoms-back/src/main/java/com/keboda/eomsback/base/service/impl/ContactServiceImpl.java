package com.keboda.eomsback.base.service.impl;

import com.keboda.eomsback.base.pojo.Contact;
import com.keboda.eomsback.base.service.IContactService;
import com.keboda.eomsback.config.ContactConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl  implements IContactService {

    @Override
    public List<Contact> searchContact(Contact contact) {
        return ContactConfig.getContact().stream().filter(e -> e.getCentre().equals(contact.getCentre())).filter(e->{
            if(contact.getName() != null && !contact.getName().trim().isEmpty()){
                return e.getName().contains(contact.getName());
            }
            return true;
        }).collect(Collectors.toList());
    }
}
