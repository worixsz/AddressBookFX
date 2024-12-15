package databil.ui;

import javafx.collections.ObservableList;
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

import java.util.InputMismatchException;
import java.util.List;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class CreateContactForm extends VBox {

    private final TextField nameField = new TextField();
    private final TextField surnameField = new TextField();
    private final TextField addressField = new TextField();
    private String formatPhoneNumber;

    private final Contact contacts;

    public CreateContactForm(ObservableList<Contact> contactList, CreateContactMove createContactMove, CheckActionMove checkActionMove) {
        this.contacts = new Contact();
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #1A2A36; -fx-font-family: 'Segoe UI', sans-serif;");
        this.setSpacing(3);

        this.setPadding(new Insets(30, 100, 30, 100));

        Text successMessage = new Text();
        successMessage.setFont(Font.font("Segoe UI", 16));
        successMessage.setFill(Color.LIMEGREEN);
        successMessage.setVisible(false);

        Label nameLabel = createStyledLabel("Name");
        Label surnameLabel = createStyledLabel("Surname");
        Label addressLabel = createStyledLabel("Address");
        Label phoneLabel = createStyledLabel("Phone");

        Label nameErrorLabel = createErrorLabel();
        Label surnameErrorLabel = createErrorLabel();
        Label addressErrorLabel = createErrorLabel();
        Label phoneErrorLabel = createErrorLabel();

        Button saveButton = createStyledButton("Save");
        Button cancelButton = createStyledButton("Cancel");

        HBox phoneFieldContainer = new HBox();
        phoneFieldContainer.setSpacing(10);
        phoneFieldContainer.setStyle("-fx-background-color: #2A3A50; -fx-padding: 10; -fx-border-radius: 8px;");

        Label phoneIndicator = new Label("+996");
        phoneIndicator.setFont(Font.font("Segoe UI", 16));
        phoneIndicator.setTextFill(Color.WHITE);
        phoneIndicator.setAlignment(Pos.CENTER);

        TextField phoneInput = new TextField();
        phoneInput.setStyle("-fx-background-color: #1A2A36; -fx-text-fill: white; -fx-border-color: transparent;");
        phoneInput.setFont(Font.font("Segoe UI", 16));

        phoneFieldContainer.getChildren().addAll(phoneIndicator, phoneInput);

        HBox buttonBox = new HBox(15, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
                nameLabel, nameField, nameErrorLabel,
                surnameLabel, surnameField, surnameErrorLabel,
                addressLabel, addressField, addressErrorLabel,
                phoneLabel, phoneFieldContainer, phoneErrorLabel,
                buttonBox,
                successMessage
        );

        saveButton.addEventHandler(MOUSE_CLICKED, _ -> {
            boolean isValid = true;

            nameErrorLabel.setText("");
            surnameErrorLabel.setText("");
            addressErrorLabel.setText("");
            phoneErrorLabel.setText("");
            successMessage.setVisible(false);

            try {
                String name = nameField.getText();
                checkActionMove.regexName(name);
                contacts.setName(name);

            } catch (InputMismatchException ex) {
                nameErrorLabel.setText(ex.getMessage());
                isValid = false;
            }
            try {
                String surname = surnameField.getText();
                checkActionMove.regexSurname(surname);
                contacts.setSurname(surname);

            } catch (InputMismatchException ex) {
                surnameErrorLabel.setText(ex.getMessage());
                isValid = false;
            }

            try {
                String address = addressField.getText();
                checkActionMove.regexAddress(address);
                contacts.setAddress(address);

            } catch (InputMismatchException ex) {
                addressErrorLabel.setText(ex.getMessage());
                isValid = false;
            }

            try {
                String phone = checkActionMove.formatPhoneNumber(phoneInput.getText());
                checkActionMove.regexPhoneNumber(phone);
                checkActionMove.checkNumberForSave(phone);
                contacts.setPhone(phone);
                formatPhoneNumber = phone;

            } catch (InputMismatchException ex) {
                phoneErrorLabel.setText(ex.getMessage());
                isValid = false;
            }

            if (isValid) {
                Contact newContact = new Contact();
                newContact.setId(createContactMove.generateUniqueId());
                newContact.setName(nameField.getText().trim());
                newContact.setSurname(surnameField.getText().trim());
                newContact.setAddress(addressField.getText().trim());
                newContact.setPhone(formatPhoneNumber.trim());

                contactList.add(newContact);
                createContactMove.createContact(List.of(newContact));

                nameField.clear();
                surnameField.clear();
                addressField.clear();
                phoneInput.clear();

                successMessage.setText("Successfully Saved!");
                successMessage.setVisible(true);
            }
        });

        cancelButton.addEventHandler(MOUSE_CLICKED, _ -> {
            nameField.clear();
            surnameField.clear();
            addressField.clear();
            phoneInput.clear();
            nameErrorLabel.setText("");
            surnameErrorLabel.setText("");
            addressErrorLabel.setText("");
            phoneErrorLabel.setText("");
            successMessage.setVisible(false);
        });
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", 16));
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
        button.setStyle("-fx-background-color: #2A3A50;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 14px; -fx-background-radius: 8px;" +
                " -fx-border-radius: 6px;");
        button.setPrefWidth(150);
        button.setPrefHeight(20);

        button.setOnMouseEntered(_ -> button.setStyle("-fx-background-color: #1A2A36;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 14px; -fx-background-radius: 8px;"));
        button.setOnMouseExited(_ -> button.setStyle("-fx-background-color: #2A3A50;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 14px; -fx-background-radius: 8px;"));
        button.setOnMouseClicked(_ -> button.setStyle("-fx-background-color: #1A2A36;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 14px; -fx-background-radius: 8px;"));
        return button;
    }
}
