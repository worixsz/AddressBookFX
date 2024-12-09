package ui;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Contact;
import repository.CreateContactMove;

import java.util.ArrayList;
import java.util.List;

public class MainControlPane extends GridPane {

    private final List<Contact> contacts;
    private CreateContactMove createContactMove;

    ButtonBar buttonBar = new ButtonBar();


    Button newContactButton = new Button("New Contact");
    Button updateButton = new Button("Update Contact");
    Button deleteButton = new Button("Delete");
    Button searchButton = new Button("Search");

    public MainControlPane() {
        this.contacts = new ArrayList<>();
        createContactMove = new CreateContactMove();
        buttonBar.getButtons().addAll(newContactButton, updateButton, deleteButton, searchButton);
        this.add(buttonBar, 0, 0);
        newContactButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            add(new NewContactForm(createContactMove), 0, 1);
        });
//        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//            add(new FindContactPane(contactService), 0, 1);
//        });


    }


}
