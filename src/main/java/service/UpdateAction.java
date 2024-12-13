package service;

import model.Contact;

import java.util.List;

public interface UpdateAction {

    void update(Contact oldContact, Contact newContact);

    List<Contact> findAllByPhone(String phone);

    List<Contact> findAllByName(String name);

    List<Contact> findAllBySurname(String phone);

    List<Contact> findAllByAddress(String phone);
}
