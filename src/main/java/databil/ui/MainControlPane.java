package databil.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Contact;
import repository.CheckActionMove;
import repository.CreateContactMove;

import java.util.List;

public class MainControlPane extends GridPane {

    private final CreateContactMove createContactMove;
    private List<Contact> contacts;
    private final CheckActionMove checkActionMove;

    public MainControlPane() {
        this.createContactMove = new CreateContactMove();
        this.checkActionMove = new CheckActionMove();

        ButtonBar buttonBar = new ButtonBar();
        Button newContactButton = new Button("New Contact");
        buttonBar.getButtons().add(newContactButton);

        this.add(buttonBar, 0, 0);

        newContactButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            add(new NewContactForm(createContactMove, checkActionMove), 0, 1);
        });
    }
}
