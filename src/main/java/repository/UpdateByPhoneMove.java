package repository;

import fileService.FileService;
import model.Contact;

import java.util.ArrayList;
import java.util.List;

public class UpdateByPhoneMove {

    private final FileService fileRepository;
    private final List<Contact> contacts;

    public UpdateByPhoneMove() {
        this.fileRepository = new FileService();
        this.contacts = fileRepository.read() != null ? fileRepository.read() : new ArrayList<>();
    }

    public void update(Contact oldContact, Contact newContact) {
        contacts.remove(oldContact);
        contacts.add(newContact);
        fileRepository.write(contacts);
    }


    public Contact findByPhone(String phone) {
        for (Contact contact : contacts) {
            if (contact.getPhone().equals(phone)) {
                return contact;
            }
        }

        return null;
    }
}
