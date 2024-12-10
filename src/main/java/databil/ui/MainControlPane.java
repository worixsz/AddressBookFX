package databil.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import repository.CheckActionMove;
import repository.CreateContactMove;
import repository.SearchActionMove;
import repository.SearchActionByPrefixMove;

public class MainControlPane extends StackPane {

    private final CreateContactForm createContactForm;
    private final SearchContactForm searchContactForm;

    public MainControlPane(CreateContactMove contactService, CheckActionMove checkActionMove, SearchActionMove searchActionMove, SearchActionByPrefixMove searchActionByPrefixMove) {

        this.createContactForm = new CreateContactForm(contactService, checkActionMove);
        this.searchContactForm = new SearchContactForm(searchActionMove, searchActionByPrefixMove, checkActionMove);

        Button createButton = new Button("CREATE CONTACT");
        Button searchButton = new Button("SEARCH CONTACT");

        createContactForm.setVisible(false);
        searchContactForm.setVisible(false);

        VBox buttonBox = new VBox(10, createButton, searchButton);
        buttonBox.setAlignment(Pos.TOP_CENTER);

        this.getChildren().addAll(buttonBox, createContactForm, searchContactForm);

        createButton.setOnAction(_ -> {
            buttonBox.setVisible(false);
            createContactForm.setVisible(true);
            searchContactForm.setVisible(false);
        });

        searchButton.setOnAction(_ -> {
            buttonBox.setVisible(false);
            searchContactForm.setVisible(true);
            createContactForm.setVisible(false);
        });
    }
}
