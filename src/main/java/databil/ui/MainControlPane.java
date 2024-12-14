package databil.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import repository.*;

public class MainControlPane extends StackPane {

    private final CreateContactForm createContactForm;
    private final ShowContactForm showContactForm;
    private final SearchContactForm searchContactForm;
    private final UpdateContactForm updateContactForm;

    public MainControlPane(CreateContactMove createContactMove,
                           ShowActionMove showActionMove,
                           CheckActionMove checkActionMove,
                           SearchActionMove searchActionMove,
                           SearchActionByPrefixMove searchActionByPrefixMove,
                           UpdateActionMove updateByPhoneMove
    ) {

        this.createContactForm = new CreateContactForm(createContactMove, checkActionMove);
        this.showContactForm = new ShowContactForm(showActionMove);
        this.searchContactForm = new SearchContactForm(searchActionMove, searchActionByPrefixMove, checkActionMove);
        this.updateContactForm = new UpdateContactForm(checkActionMove);

        Button createButton = new Button("CREATE CONTACT");
        Button showButton = new Button("  SHOW CONTACT  ");
        Button searchButton = new Button("SEARCH CONTACT");
        Button updateButton = new Button("UPDATE CONTACT");
        Button backButton = createStyledButton();

        createContactForm.setVisible(false);
        showContactForm.setVisible(false);
        searchContactForm.setVisible(false);
        updateContactForm.setVisible(false);

        backButton.setVisible(false);

        VBox buttonBox = new VBox(15, createButton, showButton, searchButton, updateButton);
        buttonBox.setAlignment(Pos.TOP_CENTER);

        StackPane.setAlignment(backButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(backButton, new Insets(5));

        this.getChildren().addAll(buttonBox, createContactForm, showContactForm, searchContactForm, updateContactForm, backButton);

        createButton.setOnAction(_ -> {
            buttonBox.setVisible(false);
            createContactForm.setVisible(true);
            searchContactForm.setVisible(false);
            updateContactForm.setVisible(false);
            backButton.setVisible(true);
        });

        showButton.setOnAction(_ -> {
            buttonBox.setVisible(false);
            createContactForm.setVisible(false);
            showContactForm.setVisible(true);
            searchContactForm.setVisible(false);
            updateContactForm.setVisible(false);
            backButton.setVisible(true);
        });

        searchButton.setOnAction(_ -> {
            buttonBox.setVisible(false);
            searchContactForm.setVisible(true);
            createContactForm.setVisible(false);
            updateContactForm.setVisible(false);
            backButton.setVisible(true);
        });

        updateButton.setOnAction(_ -> {
            buttonBox.setVisible(false);
            updateContactForm.setVisible(true);
            createContactForm.setVisible(false);
            searchContactForm.setVisible(false);
            backButton.setVisible(true);
        });

        backButton.setOnAction(_ -> {
            createContactForm.setVisible(false);
            showContactForm.setVisible(false);
            searchContactForm.setVisible(false);
            updateContactForm.setVisible(false);
            backButton.setVisible(false);
            buttonBox.setVisible(true);
        });
    }

    private Button createStyledButton() {
        Button button = new Button("BACK");
        button.setStyle("-fx-background-color: #34495E;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 12px; -fx-background-radius: 6px;" +
                " -fx-border-radius: 5px;");
        button.setPrefWidth(50);
        button.setPrefHeight(10);
        button.setOnMouseEntered(_ -> button.setStyle("-fx-background-color: #2C3E50;" +
                " -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;" +
                " -fx-background-radius: 6px;"));
        button.setOnMouseExited(_ -> button.setStyle("-fx-background-color: #34495E;" +
                " -fx-text-fill: white;" +
                " -fx-font-weight: bold; -fx-font-size: 12px;" +
                " -fx-background-radius: 6px;"));
        return button;
    }
}
