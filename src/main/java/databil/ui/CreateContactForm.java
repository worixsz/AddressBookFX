package databil.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Contact;
import repository.DataProcessorImpl;
import repository.CreateServiceImpl;

import java.util.InputMismatchException;
import java.util.List;

public class CreateContactForm extends VBox {

    private final TextField nameField = new TextField();
    private final TextField surnameField = new TextField();
    private final TextField addressField = new TextField();
    private final TextField phoneField = new TextField();

    private final Contact contacts;

    public CreateContactForm(ObservableList<Contact> contactList, CreateServiceImpl contactCreateServiceImpl, DataProcessorImpl dataProcessorImpl) {
        this.contacts = new Contact();
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #1A2A36; -fx-font-family: 'Segoe UI', sans-serif;");
        this.setSpacing(17);

        this.setPadding(new Insets(30, 100, 30, 100));

        Text successMessage = new Text();
        successMessage.setFont(Font.font("Segoe UI", 16));
        successMessage.setFill(Color.LIMEGREEN);
        successMessage.setVisible(false);

        HBox nameRow = createInputRow("Name:                 ", nameField);
        HBox surnameRow = createInputRow("Surname:           ", surnameField);
        HBox addressRow = createInputRow("Address:            ", addressField);
        HBox phoneRow = createInputRow("Phone(+996): ", phoneField);

        Button saveButton = createStyledButton("Save");
        Button cancelButton = createStyledButton("Cancel");

        HBox buttonBox = new HBox(15, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
                nameRow,
                surnameRow,
                addressRow,
                phoneRow,
                buttonBox,
                successMessage
        );

        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> handleSave(contactList, contactCreateServiceImpl, dataProcessorImpl, successMessage));

        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> handleCancel(nameField, surnameField, addressField, phoneField, successMessage));
    }

    private HBox createInputRow(String labelText, TextField textField) {
        Label label = createStyledLabel(labelText);
        label.setAlignment(Pos.CENTER_LEFT);

        textField.setAlignment(Pos.CENTER_LEFT);

        HBox row = new HBox(10, label, textField);
        row.setAlignment(Pos.CENTER_LEFT);

        return row;
    }

    private void handleSave(ObservableList<Contact> contactList, CreateServiceImpl contactCreateServiceImpl, DataProcessorImpl dataProcessorImpl, Text successMessage) {
        String formatPhoneNumber = "";
        boolean isValid = true;

        successMessage.setVisible(false);

        try {
            String name = nameField.getText();
            dataProcessorImpl.regexName(name);
            contacts.setName(name);

        } catch (InputMismatchException ex) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", ex.getMessage());
            isValid = false;
        }
        try {
            String surname = surnameField.getText();
            dataProcessorImpl.regexSurname(surname);
            contacts.setSurname(surname);

        } catch (InputMismatchException ex) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", ex.getMessage());
            isValid = false;
        }

        try {
            String address = addressField.getText();
            dataProcessorImpl.regexAddress(address);
            contacts.setAddress(address);

        } catch (InputMismatchException ex) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", ex.getMessage());
            isValid = false;
        }

        try {
            formatPhoneNumber = dataProcessorImpl.formatPhoneNumber(phoneField.getText());
            dataProcessorImpl.regexPhoneNumber(formatPhoneNumber);
            dataProcessorImpl.checkNumberForSave(formatPhoneNumber);
            contacts.setPhone(formatPhoneNumber);

        } catch (InputMismatchException ex) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", ex.getMessage());
            isValid = false;
        }

        if (isValid) {
            Contact newContact = new Contact();
            newContact.setId(dataProcessorImpl.generateUniqueId());
            newContact.setName(nameField.getText().trim());
            newContact.setSurname(surnameField.getText().trim());
            newContact.setAddress(addressField.getText().trim());
            newContact.setPhone(formatPhoneNumber);

            contactList.add(newContact);
            contactCreateServiceImpl.createContact(List.of(newContact));

            nameField.clear();
            surnameField.clear();
            addressField.clear();
            phoneField.clear();

            showAlert(Alert.AlertType.CONFIRMATION, "Success", "Contact  successfully saved.");
        }
    }

    private void handleCancel(TextField nameField, TextField surnameField, TextField addressField, TextField phoneField, Text successMessage) {
        nameField.clear();
        surnameField.clear();
        addressField.clear();
        phoneField.clear();

        successMessage.setVisible(false);
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", 16));
        label.setTextFill(Color.WHITE);
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

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
