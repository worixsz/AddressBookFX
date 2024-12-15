package service;

import model.Contact;

import java.util.List;

public interface SearchPrefixService {

    List<Contact> findByNamePrefix(String namePrefix);

    List<Contact> findBySurnamePrefix(String surnamePrefix);

    List<Contact> findByAddressPrefix(String addressPrefix);

    List<Contact> findByPhonePrefix(String phonePrefix);
}
