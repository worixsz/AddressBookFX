package repository;

import fileService.FileService;
import model.Contact;
import service.CheckAction;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;


public class CheckActionMove implements CheckAction {

    private final FileService fileService;
    private final List<Contact> contacts;


    public CheckActionMove() {
        fileService = new FileService();
        this.contacts = fileService.read() != null ? fileService.read() : new ArrayList<>();

    }

    @Override
    public String formatPhoneNumber(String phone) {
        String formattedNumberKG = phone;
        try {
            String cleanPhone = phone.replaceAll("\\D", "");
            System.out.println(phone.length());
            if (cleanPhone.length() != 9) {
                throw new InputMismatchException("Phone format incorrect.");
            }
            formattedNumberKG = "+996 " + cleanPhone.replaceAll("(.{3})(.{3})(.{3})", "$1 $2 $3");
            formattedNumberKG = formattedNumberKG.trim();
            return formattedNumberKG;

        } catch (IllegalArgumentException e) {
            System.err.println("❌ Incorrect number format to save..\n");
        }
        return formattedNumberKG;
    }


    @Override
    public void regexName(String name) {
        String nameRegex = "[A-Za-z]+([ '-][A-Za-z]+)*$";

        if (name.trim().isEmpty()) {
            throw new InputMismatchException("The input cannot be empty.");
        } else if (!name.matches(nameRegex)) {
            throw new InputMismatchException("Incorrect format of name: " + name);
        }

    }

    @Override
    public void regexSurname(String surname) {
        String surnameRegex = "^[A-Za-z]+([ '-][A-Za-z]+)*$";

        if (surname.trim().isEmpty()) {
            throw new InputMismatchException("The input cannot be empty.");
        } else if (!surname.matches(surnameRegex)) {
            throw new InputMismatchException("Incorrect format of surname: " + surname);
        }


    }

    @Override
    public void regexAddress(String address) {
        String addressRegex = "^[\\w\\s,.-]+$";

        if (address.trim().isEmpty()) {
            throw new InputMismatchException("The input cannot be empty.");
        } else if (!address.matches(addressRegex)) {
            throw new InputMismatchException("Incorrect format of address: " + address);
        }

    }

    @Override
    public void regexPhoneNumber(String number) {
        String phoneRegex = "^([+]?\\d{1,3}[\\s\\-]?\\(?\\d{1,4}\\)?[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4})$";
        number = number.replaceAll("\\s+", "");
        if (number.trim().isEmpty()) {
            throw new InputMismatchException("The input cannot be empty.");
        } else if (!number.matches(phoneRegex)) {
            throw new InputMismatchException("Incorrect format of phone number: " + number);
        }

    }

    @Override
    public void checkNumberForSave(String number) {
        boolean exists = contacts.stream()
                .anyMatch(contact -> contact.getPhone().equals(number));
        if (exists) {
            throw new InputMismatchException("Phone number already busy.");
        }
    }


}

