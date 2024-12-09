package ui;


import fileService.FileService;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Contact;
import repository.CreateContactMove;

import java.util.ArrayList;
import java.util.List;

public class NewContactForm extends GridPane {

    private final CreateContactMove contactService;

    private final List<Contact> contacts;
    private final FileService fileService;

    public NewContactForm(CreateContactMove contactService1) {
        contacts = new ArrayList<>();
        contactService = contactService1;
        fileService = new FileService();
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        Label nameLabel = new Label("Name");
        Label surnameLabel = new Label("Surname");
        Label addressLabel = new Label("Address");
        Label phoneLabel = new Label("Phone");

        TextField nameField = new TextField();
        TextField surnameField = new TextField();
        TextField addressField = new TextField();
        TextField phoneField = new TextField();

        add(nameLabel, 0, 0);
        add(nameField, 1, 0);

        add(surnameLabel, 0, 1);
        add(surnameField, 1, 1);

        add(addressLabel, 0, 2);
        add(addressField, 1, 2);

        add(phoneLabel, 0, 3);
        add(phoneField, 1, 3);

        add(saveButton, 0, 4);
        add(cancelButton, 1, 4);

        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Contact contact = new Contact();
            contact.setName(nameField.getText());
            contact.setSurname(surnameField.getText());
            contact.setPhone(phoneField.getText());
            contacts.add(contact);
            contactService.createContact(contacts);
            fileService.write(contacts);


            nameField.clear();
            surnameField.clear();

            phoneField.clear();
        });
    }
}
