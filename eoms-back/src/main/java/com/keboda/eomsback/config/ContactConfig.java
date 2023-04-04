package com.keboda.eomsback.config;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.keboda.eomsback.base.pojo.Contact;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@Component
public class ContactConfig {

    private static final List<Contact> CONTACTS;

    static {
        String json = ResourceUtil.readUtf8Str("json/contact.json");
        CONTACTS = JSONUtil.toList(json, Contact.class);
    }

    public static List<Contact> getContact(){
        return CONTACTS;
    }

    public static void addContact(Contact contact){
        CONTACTS.add(contact);
    }

    public static void removeContact(Contact contact){
        CONTACTS.remove(contact);
    }
}
