package repository;

import fileService.FileService;
import model.Contact;
import service.CreateAction;

import java.util.ArrayList;
import java.util.List;

public class CreateContactMove implements CreateAction {

    private final FileService fileService;
    private final List<Contact> contactsList;

    public CreateContactMove() {
        this.fileService = new FileService();
        this.contactsList = fileService.read() != null ? fileService.read() : new ArrayList<>();
    }

    @Override
    public void createContact(List<Contact> contacts) {

        contactsList.addAll(contacts);
        fileService.write(contactsList);
    }
}
