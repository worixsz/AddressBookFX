package repository;

import fileService.FileService;
import model.Contact;
import service.ViewerService;

import java.util.ArrayList;
import java.util.List;

public class ViewerServiceImpl implements ViewerService {


    private final List<Contact> contactList;

    public ViewerServiceImpl() {
        FileService fileService = new FileService();
        this.contactList = fileService.read() != null ? fileService.read() : new ArrayList<>();

    }

    @Override
    public List<Contact> showContact() {
        if (contactList.isEmpty()) {
            System.out.println("❌ No contacts available.\n");
        }
        return contactList;
    }

}
