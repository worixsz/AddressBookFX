package databil.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import model.Contact;
import repository.ShowActionMove;

import java.util.ArrayList;
import java.util.List;

public class ShowContactForm extends GridPane {

    private final ListView<Contact> contactListView = new ListView<>();
    private final ShowActionMove showActionMove;
    private final List<Contact> contactList = new ArrayList<>();

    public ShowContactForm(ShowActionMove showActionMove) {
        this.showActionMove = showActionMove;
        setupUI();
        handleSearchByPhone();
    }

    private void setupUI() {

        contactListView.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Contact contact, boolean empty) {
                super.updateItem(contact, empty);
                if (empty || contact == null) {
                    setText(null);
                } else {
                    setText(contact.getName() + " - " + contact.getPhone());
                    setStyle("-fx-text-fill: black;"); // Стиль для черного текста
                }
            }
        });

    }


    private void handleSearchByPhone() {
        try {

            List<Contact> result = showActionMove.showContact(contactList);

            ObservableList<Contact> observableContacts = FXCollections.observableArrayList(result);
            contactListView.setItems(observableContacts);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить контакты");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
