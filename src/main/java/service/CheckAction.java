package service;

import model.Contact;

import java.util.List;

public interface CheckAction {

    void showContact(List<Contact> contacts);

    void checkPhoneNumber(String number);

    String formatPhoneNumber(String number);

    void checkForValidName(String name);

    void checkForValidSurname(String surname);

    void checkForValidAddress(String address);

    void checkForValidPhoneNumber(String number);

    void checkStringForEmpty(String input);


}
