package repository;

import fileService.FileService;
import model.Contact;
import service.SearchActionByPrefix;

import java.util.List;

public class SearchActionByPrefixMove implements SearchActionByPrefix {

    private final FileService fileService;

    public SearchActionByPrefixMove() {
        this.fileService = new FileService();
    }

    @Override
    public List<Contact> findByNamePrefix(String namePrefix) {
        List<Contact> contacts = fileService.read();
        return contacts.stream()
                .filter(contact -> contact.getName().startsWith(namePrefix))
                .toList();
    }

    @Override
    public List<Contact> findBySurnamePrefix(String surnamePrefix) {
        List<Contact> contacts = fileService.read();
        return contacts.stream()
                .filter(contact -> contact.getSurname().startsWith(surnamePrefix))
                .toList();
    }

    @Override
    public List<Contact> findByAddressPrefix(String addressPrefix) {
        List<Contact> contacts = fileService.read();
        return contacts.stream()
                .filter(contact -> contact.getAddress().startsWith(addressPrefix))
                .toList();
    }

    @Override
    public List<Contact> findByPhonePrefix(String phonePrefix) {
        List<Contact> contacts = fileService.read();
        String cleanPhonePrefix = phonePrefix.replace("+996 ", "");

        return contacts.stream()
                .filter(contact -> contact.getPhone().startsWith("+996 " + cleanPhonePrefix))
                .toList();
    }

}
