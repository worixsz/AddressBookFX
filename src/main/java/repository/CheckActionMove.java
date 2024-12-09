package repository;

import model.Contact;
import service.CheckAction;

import java.util.InputMismatchException;
import java.util.List;


public class CheckActionMove implements CheckAction {


    public CheckActionMove() {

    }

    @Override
    public void showContact(List<Contact> contacts) {

        if (contacts == null || contacts.isEmpty()) {
            System.out.println("❌ No contacts available.\n");
        } else {
            System.out.println("\n--- LIST OF ALL CONTACTS ---");
            contacts.forEach(System.out::println);
        }
    }


    @Override
    public void checkPhoneNumber(String number) throws InputMismatchException {
        if (number.length() != 16) {
            throw new InputMismatchException("❗Incorrect number format to save.");
        }
    }

    @Override
    public String formatPhoneNumber(String phone) {
        String formattedNumberKG = phone;
        try {
            String cleanPhone = phone.replaceAll("\\D", "");
            formattedNumberKG = "+996 " + cleanPhone.replaceAll("(.{3})(.{3})(.{3})", "$1 $2 $3");
            formattedNumberKG = formattedNumberKG.trim();
            checkPhoneNumber(formattedNumberKG);
            return formattedNumberKG;

        } catch (IllegalArgumentException e) {
            System.err.println("❌ Incorrect number format to save..\n");
        }
        return formattedNumberKG;
    }

    @Override
    public void checkForValidName(String name) {
        String nameRegex = "^[A-Z][a-zA-Z '.-]*[A-Za-z]$";

        if (name.trim().isEmpty()) {
            throw new InputMismatchException("The input cannot be empty.");
        } else if (!name.matches(nameRegex)) {
            throw new InputMismatchException("Incorrect format of name: " + name);
        }

    }

    @Override
    public void checkForValidSurname(String surname) {
        String surnameRegex = "^[A-Za-z]+([ '-][A-Za-z]+)*$";

        if (surname.trim().isEmpty()) {
            throw new InputMismatchException("The input cannot be empty.");
        } else if (!surname.matches(surnameRegex)) {
            throw new InputMismatchException("Incorrect format of surname: " + surname);
        }


    }

    @Override
    public void checkForValidAddress(String address) {
        String addressRegex = "^[\\w\\s,.-]+$";

        if (address.trim().isEmpty()) {
            throw new InputMismatchException("The input cannot be empty.");
        } else if (!address.matches(addressRegex)) {
            throw new InputMismatchException("Incorrect format of address: " + address);
        }

    }

    @Override
    public void checkForValidPhoneNumber(String number) {
        String phoneRegex = "^([+]?\\d{1,3}[\\s\\-]?\\(?\\d{1,4}\\)?[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4})$";

        if (number.trim().isEmpty()) {
            throw new InputMismatchException("The input cannot be empty.");
        } else if (!number.matches(phoneRegex)) {
            throw new InputMismatchException("Incorrect format of phone number: " + number);
        }

    }

    @Override
    public void checkStringForEmpty(String input) {
        if (input.trim().isEmpty()) {
            throw new InputMismatchException("The input cannot be empty.");
        }
    }
}

