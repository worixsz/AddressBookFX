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
import java.util.InputMismatchException;
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

        Label nameErrorLabel = new Label();
        Label surnameErrorLabel = new Label();
        Label addressErrorLabel = new Label();
        Label phoneErrorLabel = new Label();

        nameErrorLabel.setStyle("-fx-text-fill: red;");
        surnameErrorLabel.setStyle("-fx-text-fill: red;");
        addressErrorLabel.setStyle("-fx-text-fill: red;");
        phoneErrorLabel.setStyle("-fx-text-fill: red;");

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        add(nameLabel, 0, 0);
        add(nameField, 1, 0);
        add(nameErrorLabel, 2, 0);

        add(surnameLabel, 0, 1);
        add(surnameField, 1, 1);
        add(surnameErrorLabel, 2, 1);

        add(addressLabel, 0, 2);
        add(addressField, 1, 2);
        add(addressErrorLabel, 2, 2);

        add(phoneLabel, 0, 3);
        add(phoneField, 1, 3);
        add(phoneErrorLabel, 2, 3);

        add(saveButton, 0, 4);
        add(cancelButton, 1, 4);

        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            boolean isValid = true;

            nameErrorLabel.setText("");
            surnameErrorLabel.setText("");
            addressErrorLabel.setText("");
            phoneErrorLabel.setText("");

            try {
                String name = nameField.getText();
                checkActionMove.checkForValidName(name);
                contacts.setName(name);

            } catch (InputMismatchException ex) {
                nameErrorLabel.setText(ex.getMessage());
                isValid = false;
            }
            try {
                String surname = surnameField.getText();
                checkActionMove.checkForValidSurname(surname);
                contacts.setSurname(surname);

            } catch (InputMismatchException ex) {
                surnameErrorLabel.setText(ex.getMessage());
                isValid = false;
            }


            try {
                String address = addressField.getText();
                checkActionMove.checkForValidAddress(address);
                contacts.setAddress(address);

            } catch (InputMismatchException ex) {
                addressErrorLabel.setText(ex.getMessage());
                isValid = false;
            }

            try {
                String phone = phoneField.getText();
                checkActionMove.checkForValidPhoneNumber(phone);
                String validNumber = checkActionMove.formatPhoneNumber(phone);
                contacts.setPhone(validNumber);

            } catch (InputMismatchException ex) {
                phoneErrorLabel.setText(ex.getMessage());
                isValid = false;
            }

            if (isValid) {
                List<Contact> saveContact = new ArrayList<>();
                saveContact.add(contacts);
                contactService.createContact(saveContact);

                nameField.clear();
                surnameField.clear();
                addressField.clear();
                phoneField.clear();
            }
        });
    }
}