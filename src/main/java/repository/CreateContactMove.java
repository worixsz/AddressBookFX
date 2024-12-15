package repository;

import fileService.FileService;
import model.Contact;
import service.CreateAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CreateContactMove implements CreateAction {

    private final FileService fileService;
    private final List<Contact> contactsList;
    private final Set<Long> existingIds;

    public CreateContactMove() {
        this.fileService = new FileService();
        this.contactsList = fileService.read() != null ? fileService.read() : new ArrayList<>();
        this.existingIds = new HashSet<>();

        for (Contact contact : contactsList) {
            existingIds.add(contact.getId());
        }
    }


    @Override
    public void createContact(List<Contact> contacts) {
        for (Contact contact : contacts) {
            if (contact.getId() == 0) {
                contact.setId(generateUniqueId());
            }
        }
        contactsList.addAll(contacts);
        fileService.write(contactsList);
    }

    public long generateUniqueId() {
        Random random = new Random();
        long uniqueId;
        do {
            uniqueId = 1_000_000_000L + (long) (random.nextDouble() * 9_000_000_000L);
        } while (existingIds.contains(uniqueId));

        existingIds.add(uniqueId);
        return uniqueId;
    }
}
