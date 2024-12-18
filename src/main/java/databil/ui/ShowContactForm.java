package databil.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Contact;
import repository.ViewerServiceImpl;

import java.util.List;

public class ShowContactForm extends GridPane {

    private final TableView<Contact> contactTableView = new TableView<>();
    private final ViewerServiceImpl contactViewerServiceImpl;
    private final ObservableList<Contact> contactList;

    public ShowContactForm(ObservableList<Contact> contactList, ViewerServiceImpl contactViewerServiceImpl) {
        this.contactList = contactList;
        this.contactViewerServiceImpl = contactViewerServiceImpl;
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #1A2A36; -fx-font-family: 'Segoe UI', sans-serif;");

        setupUI();
        refreshContacts();
    }

    private void setupUI() {
        TableColumn<Contact, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(100);

        TableColumn<Contact, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameColumn.setMinWidth(100);

        TableColumn<Contact, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setMinWidth(100);

        TableColumn<Contact, String> phoneColumn = new TableColumn<>("Phone Number");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneColumn.setMinWidth(150);

        contactTableView.getColumns().addAll(nameColumn, surnameColumn, addressColumn, phoneColumn);
        contactTableView.setItems(contactList);
        contactTableView.refresh();
        contactTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox container = new VBox(10, contactTableView);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.CENTER);
        container.setPrefSize(600, 400);

        this.add(container, 0, 0);
    }

    public void refreshContacts() {
        try {
            List<Contact> result = contactViewerServiceImpl.showContact();
            contactList.setAll(result);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There are no contacts");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
