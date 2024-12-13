package repository;

import fileService.FileService;
import model.Contact;

import java.util.ArrayList;
import java.util.List;

public class UpdateActionMove {

    private final FileService fileRepository;
    private final List<Contact> contacts;

    public UpdateActionMove() {
        this.fileRepository = new FileService();
        this.contacts = fileRepository.read() != null ? fileRepository.read() : new ArrayList<>();
    }

    public void update(Contact oldContact, Contact newContact) {
        contacts.remove(oldContact);
        contacts.add(newContact);
        fileRepository.write(contacts);
    }

    public List<Contact> findAllByPhone(String phone) {
        List<Contact> matchingContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getPhone().equals(phone)) {
                matchingContacts.add(contact);
            }
        }
        return matchingContacts;
    }


    public List<Contact> findAllByName(String name) {
        List<Contact> matchingContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                matchingContacts.add(contact);
            }
        }
        return matchingContacts;
    }

    public List<Contact> findAllBySurname(String phone) {
        List<Contact> matchingContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getSurname().equals(phone)) {
                matchingContacts.add(contact);
            }
        }
        return matchingContacts;
    }

    public List<Contact> findAllByAddress(String phone) {
        List<Contact> matchingContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getAddress().equals(phone)) {
                matchingContacts.add(contact);
            }
        }
        return matchingContacts;
    }


}
