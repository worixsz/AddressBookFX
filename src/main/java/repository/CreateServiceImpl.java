package repository;

import fileService.FileService;
import model.Contact;
import service.CreateService;

import java.util.ArrayList;
import java.util.List;

public class CreateServiceImpl implements CreateService {

    private final FileService fileService;
    private final List<Contact> contactsList;
    private final DataProcessorImpl dataProcessorImpl;

    public CreateServiceImpl() {
        this.fileService = new FileService();
        this.dataProcessorImpl = new DataProcessorImpl();
        this.contactsList = fileService.read() != null ? fileService.read() : new ArrayList<>();

    }


    @Override
    public void createContact(List<Contact> contacts) {
        for (Contact contact : contacts) {
            if (contact.getId() == 0) {
                contact.setId(dataProcessorImpl.generateUniqueId());
            }
        }
        contactsList.addAll(contacts);
        fileService.write(contactsList);
    }


}
