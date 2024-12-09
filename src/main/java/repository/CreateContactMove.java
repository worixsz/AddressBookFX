package repository;

import fileService.FileService;
import model.Contact;
import service.CreateAction;

import java.util.ArrayList;
import java.util.List;

public class CreateContactMove implements CreateAction {

    private final FileService fileService;

    public CreateContactMove() {
        this.fileService = new FileService();
    }

    @Override
    public void createContact(List<Contact> contacts) {
        List<Contact> existingContacts = fileService.read();
        if (existingContacts == null) {
            existingContacts = new ArrayList<>();
        }
        existingContacts.addAll(contacts);
        fileService.write(existingContacts);
    }
}
