package databil.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Contact;
import repository.CheckActionMove;
import repository.CreateContactMove;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class CreateContactForm extends VBox {

    private final Contact contacts;

    public CreateContactForm(CreateContactMove contactService, CheckActionMove checkActionMove) {
        this.contacts = new Contact();

        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #2C3E50; -fx-font-family: 'Segoe UI', sans-serif;");
        this.setSpacing(10);

        this.setPadding(new Insets(20, 100, 20, 100));

        Text successMessage = new Text();
        successMessage.setFont(Font.font("Segoe UI", 14));
        successMessage.setFill(Color.LIMEGREEN);
        successMessage.setVisible(false);

        Label nameLabel = createStyledLabel("Name");
        Label surnameLabel = createStyledLabel("Surname");
        Label addressLabel = createStyledLabel("Address");
        Label phoneLabel = createStyledLabel("Phone");

        TextField nameField = new TextField();
        TextField surnameField = new TextField();
        TextField addressField = new TextField();
        TextField phoneField = new TextField();

        Label nameErrorLabel = createErrorLabel();
        Label surnameErrorLabel = createErrorLabel();
        Label addressErrorLabel = createErrorLabel();
        Label phoneErrorLabel = createErrorLabel();

        Button saveButton = createStyledButton("Save");
        Button cancelButton = createStyledButton("Cancel");

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
                nameLabel, nameField, nameErrorLabel,
                surnameLabel, surnameField, surnameErrorLabel,
                addressLabel, addressField, addressErrorLabel,
                phoneLabel, phoneField, phoneErrorLabel,
                buttonBox,
                successMessage
        );


        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.startsWith("+996 ")) {
                phoneField.setText("+996" + " ");
            }
        });


        saveButton.addEventHandler(MOUSE_CLICKED, e -> {
            boolean isValid = true;

            nameErrorLabel.setText("");
            surnameErrorLabel.setText("");
            addressErrorLabel.setText("");
            phoneErrorLabel.setText("");
            successMessage.setVisible(false);

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


                if (phone.startsWith("+996 ")) {
                    phone = phone.substring(5);
                }

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

                successMessage.setText("Success Saved");
                successMessage.setVisible(true);
            }
        });

        cancelButton.addEventHandler(MOUSE_CLICKED, e -> {
            nameField.clear();
            surnameField.clear();
            addressField.clear();
            phoneField.clear();
            nameErrorLabel.setText("");
            surnameErrorLabel.setText("");
            addressErrorLabel.setText("");
            phoneErrorLabel.setText("");
            successMessage.setVisible(false);
        });
    }


    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", 14));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private Label createErrorLabel() {
        Label label = new Label();
        label.setStyle("-fx-text-fill: red;");
        return label;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 12px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #34495E; -fx-text-fill: white;"));
        return button;
    }
}
