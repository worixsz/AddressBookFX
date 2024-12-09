package databil.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Contact;
import repository.CheckActionMove;
import repository.CreateContactMove;

import java.util.ArrayList;
import java.util.List;

public class NewContactForm extends GridPane {

    private final Contact contacts;

    public NewContactForm(CreateContactMove contactService, CheckActionMove checkActionMove) {
        this.contacts = new Contact();
        Label nameLabel = new Label("Name");
        Label surnameLabel = new Label("Surname");
        Label addressLabel = new Label("Address");
        Label phoneLabel = new Label("Phone");

        TextField nameField = new TextField();
        TextField surnameField = new TextField();
        TextField addressField = new TextField();
        TextField phoneField = new TextField();

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

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
            try {
                contacts.setName(nameField.getText());
                checkActionMove.checkForValidName(nameField.getText());
                contacts.setSurname(surnameField.getText());
                checkActionMove.checkForValidSurname(surnameField.getText());
                contacts.setAddress(addressField.getText());
                checkActionMove.checkForValidAddress(addressField.getText());
                checkActionMove.checkForValidPhoneNumber(phoneField.getText());
                String validNumber = checkActionMove.formatPhoneNumber(phoneField.getText());
                contacts.setPhone(validNumber);

                List<Contact> saveContact = new ArrayList<>();
                saveContact.add(contacts);
                contactService.createContact(saveContact);
                nameField.clear();
                surnameField.clear();
                addressField.clear();
                phoneField.clear();
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        });
    }
}
