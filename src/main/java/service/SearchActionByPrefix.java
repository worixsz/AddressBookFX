package service;

import model.Contact;

import java.util.List;

public interface SearchActionByPrefix {

    List<Contact> findByNamePrefix(String namePrefix);

    void findBySurnamePrefix(List<Contact> contacts, String surnamePrefix);

    void findByAddressPrefix(List<Contact> contacts, String addressPrefix);

    void findByPhonePrefix(List<Contact> contacts, String phonePrefix);
}
