package repository;

import service.CheckAction;
import java.util.InputMismatchException;



public class CheckActionMove implements CheckAction {



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
        String nameRegex = "[A-Za-z]+([ '-][A-Za-z]+)*$";

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
        number = number.replaceAll("\\s+", "");
        if (number.trim().isEmpty()) {
            throw new InputMismatchException("The input cannot be empty.");
        } else if (!number.matches(phoneRegex)) {
            throw new InputMismatchException("Incorrect format of phone number: " + number);
        }

    }

    @Override
    public String normalizePhoneNumber(String phone) {
        String cleanPhone = phone.replaceAll("\\D", "");


        if (cleanPhone.length() != 12 || !cleanPhone.startsWith("996")) {
            throw new InputMismatchException("Phone format incorrect.");
        }

        return "+996 " + cleanPhone.substring(3, 6) + " " + cleanPhone.substring(6, 9) + " " + cleanPhone.substring(9);
    }


}

