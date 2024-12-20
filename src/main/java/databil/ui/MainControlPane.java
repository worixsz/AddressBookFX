package databil.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Contact;
import repository.*;

public class MainControlPane extends StackPane {

    private final CreateContactForm createContactForm;
    private final ShowContactForm showContactForm;
    private final SearchContactForm searchContactForm;
    private final UpdateContactForm updateContactForm;

    public MainControlPane(CreateServiceImpl contactCreateServiceImpl,
                           ViewerServiceImpl contactViewerServiceImpl,
                           DataProcessorImpl dataProcessorImpl,
                           SearchServiceImpl searchServiceImpl,
                           SearchPrefixServiceImpl contactSearcherPrefix,
                           UpdateServiceImpl updateByPhoneMove
    ) {

        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        this.createContactForm = new CreateContactForm(contactList, contactCreateServiceImpl, dataProcessorImpl);
        this.showContactForm = new ShowContactForm(contactList, contactViewerServiceImpl);
        this.searchContactForm = new SearchContactForm(searchServiceImpl, contactSearcherPrefix, dataProcessorImpl);
        this.updateContactForm = new UpdateContactForm(contactList, dataProcessorImpl);

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
        buttonBox.setAlignment(Pos.CENTER);

        StackPane.setAlignment(backButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(backButton, new Insets(10));

        Text title = new Text("ADDRESS BOOK");
        title.setFont(Font.font("Segoe UI", 50));
        title.setStyle("-fx-fill: linear-gradient(to bottom, #FFFFFF, #D0D0D0); -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 20), 10, 0, 0, 5);");
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(50, 0, 0, 0));
        this.setStyle("-fx-background-color: #1A2A36; -fx-font-family: 'Segoe UI', sans-serif;");

        this.getChildren().addAll(title, buttonBox, createContactForm, showContactForm, searchContactForm, updateContactForm, backButton);

        // Styling buttons
        String buttonStyle = "-fx-background-color: #2C3E50; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 18px; " +
                "-fx-background-radius: 8px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 10, 0, 0, 2);";

        createButton.setStyle(buttonStyle);
        showButton.setStyle(buttonStyle);
        searchButton.setStyle(buttonStyle);
        updateButton.setStyle(buttonStyle);

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
                " -fx-font-size: 14px; -fx-background-radius: 8px;" +
                " -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 10, 0, 0, 2);");
        button.setPrefWidth(80);
        button.setPrefHeight(30);
        button.setOnMouseEntered(_ -> button.setStyle("-fx-background-color: #2C3E50;" +
                " -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;" +
                " -fx-background-radius: 8px; -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 10, 0, 0, 2);"));
        button.setOnMouseExited(_ -> button.setStyle("-fx-background-color: #34495E;" +
                " -fx-text-fill: white;" +
                " -fx-font-weight: bold; -fx-font-size: 14px;" +
                " -fx-background-radius: 8px;"));
        return button;
    }
}
