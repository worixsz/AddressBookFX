package service;

import model.Contact;

import java.util.List;

public interface SearchAction {

    List<Contact> searchContactByName(List<Contact> contacts);

    List<Contact> searchContactBySurname(List<Contact> contacts);

    List<Contact> searchContactByAddress(List<Contact> contacts);

    List<Contact> searchContactByPhone(List<Contact> contacts);



}
