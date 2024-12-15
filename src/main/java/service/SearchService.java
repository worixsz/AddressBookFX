package service;

import model.Contact;

import java.util.List;

public interface SearchService {

    List<Contact> searchContactByName(String next);

    List<Contact> searchContactBySurname(String next);

    List<Contact> searchContactByAddress(String next);

    List<Contact> searchContactByPhone(String next);


}
