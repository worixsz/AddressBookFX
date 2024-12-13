package databil.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Contact;
import repository.CheckActionMove;
import repository.UpdateActionMove;

import java.util.InputMismatchException;
import java.util.List;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class UpdateContactForm extends GridPane {

    private final TextField searchFieldByName;
    private final TextField searchFieldBySurname;
    private final TextField searchFieldByPhone;
    private final ListView<Contact> contactListView;
    private final TextField nameField;
    private final TextField surnameField;
    private final TextField addressField;
    private final TextField phoneFieldTwo;

    private final UpdateActionMove updateByPhoneMove;
    private final CheckActionMove checkActionMove;

    public UpdateContactForm(CheckActionMove checkActionMove) {
        this.updateByPhoneMove = new UpdateActionMove();
        this.checkActionMove = checkActionMove;

        setPadding(new Insets(10));
        setVgap(10);
        setHgap(10);
        setAlignment(Pos.CENTER);

        this.setStyle("-fx-background-color: #34495E; -fx-font-family: 'Segoe UI', sans-serif;");

        // Поля и кнопки поиска
        searchFieldByName = new TextField();
        Button searchButtonName = createStyledButton("Search");
        searchButtonName.addEventHandler(MOUSE_CLICKED, e -> handleSearchByName());
        HBox searchByNameBox = createHorizontalBox("Search By Name:", searchFieldByName, searchButtonName);
        searchByNameBox.setAlignment(Pos.CENTER_RIGHT); // Устанавливаем выравнивание влево
        searchByNameBox.setPadding(new Insets(10));

        searchFieldBySurname = new TextField();
        Button searchButtonSurname = createStyledButton("Search");
        searchButtonSurname.addEventHandler(MOUSE_CLICKED, e -> handleSearchBySurname());
        HBox searchBySurnameBox = createHorizontalBox("Search By Surname:", searchFieldBySurname, searchButtonSurname);
        searchBySurnameBox.setAlignment(Pos.CENTER_RIGHT); // Устанавливаем выравнивание влево
        searchBySurnameBox.setPadding(new Insets(10)); // Устанавливаем отступы


        searchFieldByPhone = new TextField();
        searchFieldByPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.startsWith("+996 ")) {
                searchFieldByPhone.setText("+996 ");
            }
        });
        Button searchButtonPhone = createStyledButton("Search");
        searchButtonPhone.addEventHandler(MOUSE_CLICKED, e -> handleSearch());
        HBox searchByPhoneBox = createHorizontalBox("Search By Phone:", searchFieldByPhone, searchButtonPhone);
        searchByPhoneBox.setAlignment(Pos.CENTER_RIGHT); // Устанавливаем выравнивание влево
        searchByPhoneBox.setPadding(new Insets(10));

        // Список контактов
        contactListView = new ListView<>();
        contactListView.setPrefHeight(100);
        contactListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });

        // Поля для обновления контакта
        nameField = new TextField();
        surnameField = new TextField();
        addressField = new TextField();
        phoneFieldTwo = new TextField();
        phoneFieldTwo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.startsWith("+996 ")) {
                phoneFieldTwo.setText("+996 ");
            }
        });
        Button updateButton = createStyledButton("Update Contact");
        updateButton.addEventHandler(MOUSE_CLICKED, e -> handleUpdate());

        GridPane updateFields = new GridPane();
        updateFields.setHgap(10);
        updateFields.setVgap(5);
        updateFields.setPadding(new Insets(10));
        updateFields.add(createStyledLabel("New Name:"), 0, 0);
        updateFields.add(nameField, 1, 0);
        updateFields.add(createStyledLabel("New Surname:"), 0, 1);
        updateFields.add(surnameField, 1, 1);
        updateFields.add(createStyledLabel("New Address:"), 0, 2);
        updateFields.add(addressField, 1, 2);
        updateFields.add(createStyledLabel("New Phone:"), 0, 3);
        updateFields.add(phoneFieldTwo, 1, 3);

        VBox mainContainer = new VBox(10, searchByNameBox, searchBySurnameBox, searchByPhoneBox, contactListView, updateFields, updateButton);
        mainContainer.setPadding(new Insets(10));
        mainContainer.setStyle("-fx-background-color: #2C3E50; -fx-background-radius: 5px;");
        add(mainContainer, 0, 0);
    }

    private HBox createHorizontalBox(String labelText, TextField textField, Button button) {
        Label label = createStyledLabel(labelText);

        // Create vertical boxes for label, textField, and button
        VBox labelVBox = new VBox(label);
        VBox textFieldVBox = new VBox(textField);
        VBox buttonVBox = new VBox(button);

        // Determine spacing based on the length of the label
        int spacing = (int) Math.max(15, labelText.length() * 0.5); // Adjust spacing dynamically based on label length
        HBox hBox = new HBox(spacing, labelVBox, textFieldVBox, buttonVBox);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(10));
        return hBox;
    }



    private void handleSearch() {
        String phone = searchFieldByPhone.getText().trim();

        if (phone.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Error", "Please enter a phone number.");
            return;
        }

        try {
            phone = checkActionMove.normalizePhoneNumber(phone);
            List<Contact> contacts = updateByPhoneMove.findAllByPhone(phone);

            if (contacts.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No Results", "No contacts found for the given phone number.");
            } else {
                contactListView.getItems().setAll(contacts);
            }
        } catch (InputMismatchException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        }
    }

    private void handleSearchByName() {
        String name = searchFieldByName.getText().trim();

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Error", "Please enter a name.");
            return;
        }

        try {
            List<Contact> contacts = updateByPhoneMove.findAllByName(name);

            if (contacts.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No Results", "No contacts found for the given name.");
            } else {
                contactListView.getItems().setAll(contacts);
            }
        } catch (InputMismatchException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        }
    }

    private void handleSearchBySurname() {
        String name = searchFieldBySurname.getText().trim();

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Error", "Please enter a surname.");
            return;
        }

        try {
            List<Contact> contacts = updateByPhoneMove.findAllBySurname(name);

            if (contacts.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No Results", "No contacts found for the given surname.");
            } else {
                contactListView.getItems().setAll(contacts);
            }
        } catch (InputMismatchException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        }

    }


    private void populateFields(Contact contact) {
        nameField.setText(contact.getName());
        surnameField.setText(contact.getSurname());
        addressField.setText(contact.getAddress());
        phoneFieldTwo.setText(contact.getPhone());
    }

    private void handleUpdate() {
        Contact selectedContact = contactListView.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a contact to update.");
            return;
        }

        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        String address = addressField.getText().trim();
        String phone2 = phoneFieldTwo.getText().trim();

        try {
            if (phone2.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Error", "Please enter a new phone number.");
                return;
            }

            phone2 = checkActionMove.normalizePhoneNumber(phone2);
            checkActionMove.checkForValidPhoneNumber(phone2);

            checkActionMove.checkForValidName(name);
            checkActionMove.checkForValidSurname(surname);
            checkActionMove.checkForValidAddress(address);

            Contact updatedContact = new Contact(
                    name.isEmpty() ? selectedContact.getName() : name,
                    surname.isEmpty() ? selectedContact.getSurname() : surname,
                    address.isEmpty() ? selectedContact.getAddress() : address,
                    phone2
            );

            updateByPhoneMove.update(selectedContact, updatedContact);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Contact updated successfully.");
            clearFields();

        } catch (InputMismatchException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", 14));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 12px;");
        return button;
    }

    private void clearFields() {
        searchFieldByPhone.clear();
        searchFieldByName.clear();
        nameField.clear();
        surnameField.clear();
        addressField.clear();
        phoneFieldTwo.clear();
        contactListView.getItems().clear();
    }
}
