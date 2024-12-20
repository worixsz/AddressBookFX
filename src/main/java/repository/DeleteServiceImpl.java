package repository;

import fileService.FileService;
import model.Contact;
import service.DeleteService;

import java.util.ArrayList;
import java.util.List;

public class DeleteServiceImpl implements DeleteService {

    private final FileService fileService;
    private final List<Contact> contactList;

    public DeleteServiceImpl() {
        this.fileService = new FileService();
        this.contactList = fileService.read() != null ? fileService.read() : new ArrayList<>();
    }

    @Override
    public void deleteContact(List<Contact> contacts) {
        for (Contact contact : contacts) {
            contactList.remove(contact);
        }
        fileService.write(contactList);
    }
}
