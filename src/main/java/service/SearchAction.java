package service;

import model.Contact;

import java.util.List;

public interface SearchAction {

    List<Contact> searchContactByName(String next);

    List<Contact> searchContactBySurname(String next);

    List<Contact> searchContactByAddress(String next);

    List<Contact> searchContactByPhone(String next);


}
