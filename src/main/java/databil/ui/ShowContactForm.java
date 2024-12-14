package databil.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import model.Contact;
import repository.ShowActionMove;

import java.util.List;

public class ShowContactForm extends GridPane {

    private final ListView<Contact> contactListView = new ListView<>();
    private final ShowActionMove showActionMove;

    public ShowContactForm(ShowActionMove showActionMove) {
        this.showActionMove = showActionMove;
        this.setPadding(new Insets(100));
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #2C3E50; -fx-font-family: 'Segoe UI', sans-serif;");

        setupUI();
        refreshContacts();
    }

    private void setupUI() {
        contactListView.setStyle("-fx-background-color: #34495E; -fx-text-fill: black;");
        contactListView.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Contact contact, boolean empty) {
                super.updateItem(contact, empty);
                if (empty || contact == null) {
                    setText(null);
                } else {
                    setText("name: " + contact.getName() +
                            ", surname: " + contact.getSurname() +
                            ", address: " + contact.getAddress() +
                            ", phone number: " + contact.getPhone());
                    setStyle("-fx-font-size: 15;");
                }
            }
        });

        Label title = new Label("Contacts");
        title.setFont(Font.font("Segoe UI", 18));
        title.setTextFill(Color.WHITE);

        VBox container = new VBox(10, title, contactListView);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.CENTER);
        container.setPrefSize(2000, 400);

        this.add(container, 0, 0);
    }

    public void refreshContacts() {
        try {
            List<Contact> result = showActionMove.showContact();

            ObservableList<Contact> observableContacts = FXCollections.observableArrayList(result);
            contactListView.setItems(observableContacts);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("THERE ARE NO CONTACTS");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
