package repository;

import fileService.FileService;
import model.Contact;
import service.UpdateAction;

import java.util.ArrayList;
import java.util.List;

public class UpdateActionMove implements UpdateAction {

    private final FileService fileRepository;
    private final List<Contact> contacts;

    public UpdateActionMove() {
        this.fileRepository = new FileService();
        this.contacts = fileRepository.read() != null ? fileRepository.read() : new ArrayList<>();
    }

    @Override
    public void update(Contact oldContact, Contact newContact) {
        contacts.remove(oldContact);
        contacts.add(newContact);
        fileRepository.write(contacts);
    }


    @Override
    public List<Contact> findAllByName(String name) {
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
        List<Contact> matchingContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getPhone().equals(phone)) {
                matchingContacts.add(contact);
            }
        }
        return matchingContacts;
    }


}
