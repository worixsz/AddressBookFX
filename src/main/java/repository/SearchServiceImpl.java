package repository;

import fileService.FileService;
import model.Contact;
import service.SearchService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchServiceImpl implements SearchService {

    private final FileService fileService;
    private final List<Contact> contacts;

    public SearchServiceImpl() {
        this.fileService = new FileService();
        this.contacts = fileService.read() != null ? fileService.read() : new ArrayList<>();

    }

    @Override
    public List<Contact> searchContactByName(String name) {

        return contacts.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }


    @Override
    public List<Contact> searchContactBySurname(String surname) {


        return contacts.stream()
                .filter(contact -> contact.getSurname().equalsIgnoreCase(surname))
                .collect(Collectors.toList());
    }


    @Override
    public List<Contact> searchContactByAddress(String address) {

        return contacts.stream()
                .filter(contact -> contact.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());
    }


    @Override
    public List<Contact> searchContactByPhone(String phone) {


        String cleanPhone = phone.replaceAll("\\D", "");

        String finalFormattedPhone = "+996 " + cleanPhone.replaceAll("(.{3})(.{3})(.{3})", "$1 $2 $3");

        return contacts.stream()
                .filter(contact -> contact.getPhone().equalsIgnoreCase(finalFormattedPhone))
                .collect(Collectors.toList());


    }


}

