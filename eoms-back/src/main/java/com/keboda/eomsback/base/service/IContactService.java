package com.keboda.eomsback.base.service;

import com.keboda.eomsback.base.pojo.Contact;

import java.util.List;

public interface IContactService {
    List<Contact> searchContact(Contact contact);
}
