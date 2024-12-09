package service;

import model.Contact;

import java.util.List;

public interface SearchActionByPrefix {

    void findByNamePrefix(List<Contact> contacts, String namePrefix);

    void findBySurnamePrefix(List<Contact> contacts, String surnamePrefix);

    void findByAddressPrefix(List<Contact> contacts, String addressPrefix);

    void findByPhonePrefix(List<Contact> contacts, String phonePrefix);
}
