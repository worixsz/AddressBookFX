package databil.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import repository.CheckActionMove;
import repository.CreateContactMove;

public class MainControlPane extends StackPane {

    private final Button newContactButton;
    private final NewContactForm newContactForm;

    public MainControlPane(CreateContactMove contactService, CheckActionMove checkActionMove) {
        newContactButton = new Button("New Contact");


        newContactForm = new NewContactForm(contactService, checkActionMove);
        newContactForm.setVisible(false);

        this.getChildren().addAll(newContactButton, newContactForm);

        newContactButton.setOnAction(_ -> {
            newContactButton.setVisible(false);
            newContactForm.setVisible(true);
        });
    }

}
