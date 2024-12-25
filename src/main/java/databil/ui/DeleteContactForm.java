package databil.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Contact;
import repository.DeleteServiceImpl;

import java.util.List;

public class DeleteContactForm extends GridPane {

    private final TableView<Contact> contactTableView = new TableView<>();
    private final DeleteServiceImpl deleteService;
    private final ObservableList<Contact> contactList;

    public DeleteContactForm(ObservableList<Contact> contactList, DeleteServiceImpl deleteService) {
        this.contactList = contactList;
        this.deleteService = deleteService;
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #1A2A36; -fx-font-family: 'Segoe UI', sans-serif;");

        setupUI();
    }

    private void setupUI() {
        TableColumn<Contact, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(50);
        nameColumn.setStyle("-fx-font-family: 'Segoe UI', sans-serif; -fx-text-fill: #1A2A36;");

        TableColumn<Contact, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameColumn.setMinWidth(50);
        surnameColumn.setStyle("-fx-font-family: 'Segoe UI', sans-serif; -fx-text-fill: #1A2A36;");

        TableColumn<Contact, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setMinWidth(50);
        addressColumn.setStyle("-fx-font-family: 'Segoe UI', sans-serif; -fx-text-fill: #1A2A36;");

        TableColumn<Contact, String> phoneColumn = new TableColumn<>("Phone Number");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneColumn.setMinWidth(150);
        phoneColumn.setStyle("-fx-font-family: 'Segoe UI', sans-serif; -fx-text-fill: #1A2A36;");

        TableColumn<Contact, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = createStyledButton();

            {
                deleteButton.setOnAction(e -> {
                    Contact contact = getTableView().getItems().get(getIndex());
                    handleDelete(contact);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });

        contactTableView.getColumns().addAll(nameColumn, surnameColumn, addressColumn, phoneColumn, deleteColumn);
        contactTableView.setItems(contactList);
        contactTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        contactTableView.setStyle(
                "-fx-border-color: transparent; " +
                        "-fx-border-radius: 15px; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-padding: 10;"
        );

        VBox container = new VBox(30, contactTableView);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.CENTER);

        this.add(container, 0, 0);
    }


    private Button createStyledButton() {
        Button button = new Button("🗑");
        button.setStyle("-fx-background-color: #34495E;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 12px; -fx-background-radius: 6px;" +
                " -fx-border-radius: 5px;");
        button.setPrefWidth(80);
        button.setPrefHeight(20);

        button.setOnMouseEntered(_ -> button.setStyle("-fx-background-color: #2C3E50;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 12px; -fx-background-radius: 6px;"));
        button.setOnMouseExited(_ -> button.setStyle("-fx-background-color: #34495E;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 12px; -fx-background-radius: 6px;"));
        button.setOnMouseClicked(_ -> button.setStyle("-fx-background-color: #2C3E50;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 12px; -fx-background-radius: 6px;"));
        return button;
    }

    private void handleDelete(Contact contact) {
        deleteService.deleteContact(List.of(contact));
        contactList.remove(contact);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Contact successfully deleted.");
    }


    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
