package service;

import model.Contact;

import java.util.List;

public interface SearchAction {

    List<Contact> searchContactByName(String next);

    List<Contact> searchContactBySurname(List<Contact> contacts);

    List<Contact> searchContactByAddress(List<Contact> contacts);

    List<Contact> searchContactByPhone(List<Contact> contacts);


}
