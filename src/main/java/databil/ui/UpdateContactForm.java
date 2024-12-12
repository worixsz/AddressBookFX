package databil.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Contact;
import repository.CheckActionMove;
import repository.UpdateByPhoneMove;

import java.util.InputMismatchException;
import java.util.List;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
public class UpdateContactForm extends GridPane {

    private final TextField searchField;
    private final ListView<Contact> contactListView;
    private final TextField nameField;
    private final TextField surnameField;
    private final TextField addressField;
    private final TextField phoneFieldTwo;

    private final UpdateByPhoneMove updateByPhoneMove;
    private final CheckActionMove checkActionMove;

    public UpdateContactForm(CheckActionMove checkActionMove) {
        this.updateByPhoneMove = new UpdateByPhoneMove();
        this.checkActionMove = checkActionMove;

        setPadding(new Insets(10));
        setVgap(10);
        setHgap(10);
        setAlignment(Pos.CENTER);

        this.setStyle("-fx-background-color: #34495E; -fx-font-family: 'Segoe UI', sans-serif;");

        Label searchLabel = createStyledLabel("Search by Phone:");
        searchField = new TextField();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.startsWith("+996 ")) {
                searchField.setText("+996 ");
            }
        });

        Button searchButton = createStyledButton("Search");
        searchButton.addEventHandler(MOUSE_CLICKED, e -> handleSearch());

        contactListView = new ListView<>();

        contactListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });

        Label nameLabel = createStyledLabel("Enter a new name:");
        nameField = new TextField();

        Label surnameLabel = createStyledLabel("Enter a new surname:");
        surnameField = new TextField();

        Label addressLabel = createStyledLabel("Enter a new address:");
        addressField = new TextField();

        Label phoneTwoLabel = createStyledLabel("Enter a new phone number:");
        phoneFieldTwo = new TextField();

        phoneFieldTwo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.startsWith("+996 ")) {
                phoneFieldTwo.setText("+996 ");
            }
        });

        Button updateButton = createStyledButton("Update Contact");
        updateButton.addEventHandler(MOUSE_CLICKED, e -> handleUpdate());

        VBox searchBox = new VBox(5, searchLabel, searchField, searchButton, contactListView);
        searchBox.setPadding(new Insets(10));

        add(searchBox, 0, 0, 2, 1);

        add(nameLabel, 0, 1);
        add(nameField, 1, 1);

        add(surnameLabel, 0, 2);
        add(surnameField, 1, 2);

        add(addressLabel, 0, 3);
        add(addressField, 1, 3);

        add(phoneTwoLabel, 0, 4);
        add(phoneFieldTwo, 1, 4);

        add(updateButton, 0, 5, 2, 1);
    }

    private void handleSearch() {
        String phone = searchField.getText().trim();
        if (phone.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Error", "Please enter a phone number.");
            return;
        }

        List<Contact> contacts = updateByPhoneMove.findAllByPhone(phone);

        if (contacts.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Results", "No contacts found for the given phone number.");
        } else {
            contactListView.getItems().setAll(contacts);
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
        String phone2 = phoneFieldTwo.getText().trim().replace("+996 ", ""); // Удаление префикса +996

        if (name.isEmpty() && surname.isEmpty() && address.isEmpty() && phone2.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Changes", "Please modify at least one field.");
            return;
        }

        try {
            if (!phone2.isEmpty()) {
                checkActionMove.checkForValidPhoneNumber(phone2);
                String validNumber = checkActionMove.formatPhoneNumber(phone2);
                phone2 = validNumber;
            }

            checkActionMove.checkForValidName(name);
            checkActionMove.checkForValidSurname(surname);
            checkActionMove.checkForValidAddress(address);

            Contact updatedContact = new Contact(
                    name.isEmpty() ? selectedContact.getName() : name,
                    surname.isEmpty() ? selectedContact.getSurname() : surname,
                    address.isEmpty() ? selectedContact.getAddress() : address,
                    phone2.isEmpty() ? selectedContact.getPhone() : phone2
            );

            updateByPhoneMove.update(selectedContact, updatedContact);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Contact updated successfully.");
            clearFields();

        } catch (InputMismatchException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        }
    }


    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", 14));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 12px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #34495E; -fx-text-fill: white;"));
        return button;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        searchField.clear();
        nameField.clear();
        surnameField.clear();
        addressField.clear();
        phoneFieldTwo.clear();
        contactListView.getItems().clear();
    }
}
