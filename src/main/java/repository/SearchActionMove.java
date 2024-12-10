package repository;

import fileService.FileService;
import model.Contact;
import service.SearchAction;
import java.util.List;
import java.util.stream.Collectors;

public class SearchActionMove implements SearchAction {

    private final FileService fileService;


    public SearchActionMove() {
        fileService = new FileService();

    }

    @Override
    public List<Contact> searchContactByName(String name) {
        List<Contact> existingContacts = fileService.read();
        if (existingContacts == null || existingContacts.isEmpty()) {
            return List.of();
        }

        return existingContacts.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }


    @Override
    public List<Contact> searchContactBySurname(String surname) {
        List<Contact> existingContacts = fileService.read();
        if (existingContacts == null || existingContacts.isEmpty()) {
            return List.of();
        }

        return existingContacts.stream()
                .filter(contact -> contact.getSurname().equalsIgnoreCase(surname))
                .collect(Collectors.toList());
    }


    @Override
    public List<Contact> searchContactByAddress(String address) {
        List<Contact> existingContacts = fileService.read();
        if (existingContacts == null || existingContacts.isEmpty()) {
            return List.of();
        }

        return existingContacts.stream()
                .filter(contact -> contact.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());
    }


    @Override
    public List<Contact> searchContactByPhone(String phone) {

        List<Contact> existingContacts = fileService.read();

        if (existingContacts == null || existingContacts.isEmpty()) {
            return List.of();
        }
        String cleanPhone = phone.replaceAll("\\D", "");

        String finalFormattedPhone = "+996 " + cleanPhone.replaceAll("(.{3})(.{3})(.{3})", "$1 $2 $3");

        return existingContacts.stream()
                .filter(contact -> contact.getPhone().equals(finalFormattedPhone))
                .collect(Collectors.toList());


    }


}

