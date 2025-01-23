package databil.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Contact;
import repository.DataProcessorImpl;
import repository.SearchServiceImpl;
import repository.UpdateServiceImpl;

import java.util.InputMismatchException;
import java.util.List;

public class UpdateContactForm extends BorderPane {

    private TextField nameSearchField;
    private TextField surnameSearchField;
    private TextField phoneSearchField;
    private TextField addressSearchField;

    private TextField nameField;
    private TextField surnameField;
    private TextField addressField;
    private TextField phoneField;

    private final TableView<Contact> contactTableView;
    private final UpdateServiceImpl updateByPhoneMove;
    private final DataProcessorImpl dataProcessorImpl;
    private final ObservableList<Contact> contactList;
    private final SearchServiceImpl searchService;

    public UpdateContactForm(ObservableList<Contact> contactList,
                             DataProcessorImpl dataProcessorImpl,
                             SearchServiceImpl searchService) {
        this.updateByPhoneMove = new UpdateServiceImpl();
        this.searchService = searchService;
        this.dataProcessorImpl = dataProcessorImpl;
        this.contactList = contactList;

        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #1A2A36; -fx-font-family: 'Segoe UI', sans-serif;");

        VBox searchBox = createSearchBox();
        this.setTop(searchBox);

        contactTableView = new TableView<>();
        contactTableView.setStyle("-fx-background-color: #ECF0F1;" +
                " -fx-font-size: 14px; -fx-background-radius: 5px;");

        TableColumn<Contact, String> nameColumn =
                createTableColumn("Name", "name");
        nameColumn.setMinWidth(100);
        TableColumn<Contact, String> surnameColumn =
                createTableColumn("Surname", "surname");
        surnameColumn.setMinWidth(100);
        TableColumn<Contact, String> addressColumn =
                createTableColumn("Address", "address");
        addressColumn.setMinWidth(100);
        TableColumn<Contact, String> phoneColumn =
                createTableColumn("Phone", "phone");
        phoneColumn.setMinWidth(150);

        contactTableView.getColumns().addAll(nameColumn, surnameColumn, addressColumn, phoneColumn);
        contactTableView.getSelectionModel().selectedItemProperty().addListener((_, _, newSelection) -> {
            if (newSelection != null) populateFields(newSelection);
        });

        VBox centerBox = new VBox(contactTableView);
        centerBox.setPadding(new Insets(10));
        this.setCenter(centerBox);

        VBox bottomBox = createUpdateBox();
        this.setBottom(bottomBox);
    }

    private TableColumn<Contact, String> createTableColumn(String title, String property) {
        TableColumn<Contact, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    private VBox createSearchBox() {
        Label nameSearchLabel = createStyledLabel("Name:             ");
        nameSearchField = createInputField();

        Label surnameSearchLabel = createStyledLabel("Surname:        ");
        surnameSearchField = createInputField();

        Label addressSearchLabel = createStyledLabel("Address:          ");
        addressSearchField = createInputField();

        Label phoneSearchLabel = createStyledLabel("Phone (+996): ");
        phoneSearchField = createInputField();

        Button nameSearchButton = createStyledButton("🔍");
        nameSearchButton.setOnMouseClicked(_ ->
                handleSearch(nameSearchField.getText().trim(), "name"));
        Button surnameSearchButton = createStyledButton("🔍");
        surnameSearchButton.setOnMouseClicked(_ ->
                handleSearch(surnameSearchField.getText().trim(), "surname"));
        Button addressSearchButton = createStyledButton("🔍");
        addressSearchButton.setOnMouseClicked(_ ->
                handleSearch(addressSearchField.getText().trim(), "address"));
        Button phoneSearchButton = createStyledButton("🔍");
        phoneSearchButton.setOnMouseClicked(_ ->
                handleSearch(phoneSearchField.getText().trim(), "phone"));

        HBox nameRow = new HBox(10, nameSearchLabel, nameSearchField, nameSearchButton);
        HBox surnameRow = new HBox(10, surnameSearchLabel, surnameSearchField, surnameSearchButton);
        HBox phoneRow = new HBox(10, phoneSearchLabel, phoneSearchField, phoneSearchButton);
        HBox addressRow = new HBox(10, addressSearchLabel, addressSearchField, addressSearchButton);

        nameRow.setPrefWidth(100);
        surnameRow.setPrefWidth(100);
        phoneRow.setPrefWidth(100);
        addressRow.setPrefWidth(100);

        nameRow.setAlignment(Pos.CENTER_LEFT);
        surnameRow.setAlignment(Pos.CENTER_LEFT);
        phoneRow.setAlignment(Pos.CENTER_LEFT);
        addressRow.setAlignment(Pos.CENTER_LEFT);

        VBox searchLayout = new VBox(10, nameRow, surnameRow, addressRow, phoneRow);
        searchLayout.setAlignment(Pos.CENTER_LEFT);
        searchLayout.setPadding(new Insets(20));

        return searchLayout;
    }

    private VBox createUpdateBox() {
        Label nameLabel = createStyledLabel("New Name:");
        nameField = createInputField();

        Label surnameLabel = createStyledLabel("New Surname:");
        surnameField = createInputField();

        Label addressLabel = createStyledLabel("New Address:");
        addressField = createInputField();

        Label phoneLabel = createStyledLabel("New Phone (+996):");
        phoneField = createInputField();


        Button updateButton = createStyledButton("Update");
        updateButton.setOnMouseClicked(_ -> handleUpdate());

        GridPane updateFields = new GridPane();
        updateFields.setHgap(10);
        updateFields.setVgap(10);
        updateFields.setPadding(new Insets(20));
        updateFields.addRow(0, nameLabel, nameField);
        updateFields.addRow(1, surnameLabel, surnameField);
        updateFields.addRow(2, addressLabel, addressField);
        updateFields.addRow(3, phoneLabel, phoneField);

        VBox bottomBox = new VBox(10, updateFields, updateButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));
        return bottomBox;
    }

    private void handleSearch(String query, String type) {
        if (query.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Error",
                    "Please enter a search query.");
            return;
        }

        try {
            List<Contact> results;
            switch (type) {
                case "name" -> results = searchService.searchContactByName(query);
                case "surname" -> results = searchService.searchContactBySurname(query);
                case "address" -> results = searchService.searchContactByAddress(query);
                case "phone" -> results = searchService.searchContactByPhone(query);
                default -> throw new IllegalArgumentException("Invalid search type");
            }

            switch (type) {
                case "name" -> dataProcessorImpl.regexName(query);
                case "surname" -> dataProcessorImpl.regexSurname(query);
                case "address" -> dataProcessorImpl.regexAddress(query);
                case "phone" -> dataProcessorImpl.regexPhoneNumber(query);
                default -> throw new IllegalArgumentException("Invalid search type");
            }

            if (results.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No Results",
                        "No contacts found.");
            } else {
                contactTableView.getItems().setAll(results);
            }
        } catch (InputMismatchException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void handleUpdate() {
        Contact selectedContact = contactTableView.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection",
                    "Please select a contact to update.");
            return;
        }

        try {
            String name = nameField.getText().trim();
            String surname = surnameField.getText().trim();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();

            String formattedPhone = dataProcessorImpl.formatPhoneNumber(phone);

            Contact updatedContact = new Contact(
                    name.isEmpty() ? selectedContact.getName() : name,
                    surname.isEmpty() ? selectedContact.getSurname() : surname,
                    address.isEmpty() ? selectedContact.getAddress() : address,
                    phone.isEmpty() ? selectedContact.getPhone() : formattedPhone
            );

            if (!name.isEmpty()) dataProcessorImpl.regexName(name);
            if (!surname.isEmpty()) dataProcessorImpl.regexSurname(surname);
            if (!address.isEmpty()) dataProcessorImpl.regexAddress(address);
            if (!phone.isEmpty()) {
                dataProcessorImpl.regexPhoneNumber(phone);
                dataProcessorImpl.checkNumberForSave(formattedPhone);
            }

            updateByPhoneMove.update(selectedContact, updatedContact);
            contactList.remove(selectedContact);
            contactList.add(updatedContact);

            showAlert(Alert.AlertType.INFORMATION, "Success",
                    "Contact updated successfully.");
            clearFields();

        } catch (InputMismatchException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }


    private void populateFields(Contact contact) {
        nameField.setText(contact.getName());
        surnameField.setText(contact.getSurname());
        addressField.setText(contact.getAddress());


        String phoneWithoutPrefix = contact.getPhone().startsWith("+996")
                ? contact.getPhone().substring(4).trim()
                : contact.getPhone();
        phoneField.setText(phoneWithoutPrefix);
    }


    private void clearFields() {
        nameField.clear();
        surnameField.clear();
        phoneField.clear();
        addressField.clear();
        nameSearchField.clear();
        surnameSearchField.clear();
        phoneSearchField.clear();
        addressSearchField.clear();
        contactTableView.getItems().clear();
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 14));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private TextField createInputField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-background-color: #ECF0F1;" +
                " -fx-text-fill: #34495E; -fx-font-size: 14px;");
        return textField;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #2A3A50;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 14px; -fx-background-radius: 8px;" +
                " -fx-border-radius: 6px;");
        button.setPrefWidth(70);
        button.setPrefHeight(30);

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
