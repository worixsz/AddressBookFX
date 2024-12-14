package repository;

import fileService.FileService;
import model.Contact;
import service.ShowAction;

import java.util.ArrayList;
import java.util.List;

public class ShowActionMove implements ShowAction {


    private final FileService fileService;

    private final List<Contact> contactList;

    public ShowActionMove() {
        fileService = new FileService();
        this.contactList = fileService.read() != null ? fileService.read() : new ArrayList<>();

    }

    @Override
    public List<Contact> showContact() {
        if (contactList.isEmpty()) {
            System.out.println("❌ No contacts available.\n");
        } else {
            System.out.println("\n--- LIST OF ALL CONTACTS ---");

        }
        return contactList;
    }

}
