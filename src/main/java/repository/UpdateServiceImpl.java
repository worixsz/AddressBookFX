package repository;

import fileService.FileService;
import model.Contact;
import service.UpdateService;

import java.util.ArrayList;
import java.util.List;

public class UpdateServiceImpl implements UpdateService {

    private final FileService fileService;

    public UpdateServiceImpl() {
        this.fileService = new FileService();
    }

    @Override
    public void update(Contact oldContact, Contact newContact) {
        List<Contact> contacts = fileService.read();
        newContact.setId(oldContact.getId());
        contacts.remove(oldContact);
        contacts.add(newContact);
        fileService.write(contacts);
    }


    @Override
    public List<Contact> findAllByName(String name) {
        List<Contact> contacts = fileService.read();

        List<Contact> matchingContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                matchingContacts.add(contact);
            }
        }
        return matchingContacts;
    }

    @Override
    public List<Contact> findAllBySurname(String surname) {
        List<Contact> contacts = fileService.read();

        List<Contact> matchingContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getSurname().equalsIgnoreCase(surname)) {
                matchingContacts.add(contact);
            }
        }
        return matchingContacts;
    }

    @Override
    public List<Contact> findAllByAddress(String address) {
        List<Contact> contacts = fileService.read();

        List<Contact> matchingContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getAddress().equalsIgnoreCase(address)) {
                matchingContacts.add(contact);
            }
        }
        return matchingContacts;
    }

    @Override
    public List<Contact> findAllByPhone(String phone) {
        List<Contact> contacts = fileService.read();
        String cleanPhone = phone.replaceAll("\\s", "");
        List<Contact> matchingContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getPhone().replaceAll("\\s", "").equalsIgnoreCase(cleanPhone)) {
                matchingContacts.add(contact);
            }
        }
        return matchingContacts;
    }


}
