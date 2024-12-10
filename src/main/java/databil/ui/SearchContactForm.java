package databil.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Contact;
import repository.CheckActionMove;
import repository.SearchActionMove;
import repository.SearchActionByPrefixMove;

import java.util.InputMismatchException;
import java.util.List;

public class SearchContactForm extends GridPane {

    private final SearchActionMove searchActionMove;
    private final SearchActionByPrefixMove searchByPrefix;
    private final CheckActionMove checkActionMove;

    private final TextField searchByNameField = new TextField();
    private final TextField searchBySurnameField = new TextField();
    private final TextField searchByAddressField = new TextField();
    private final TextField searchByPhoneField = new TextField();

    private final TextArea resultArea = new TextArea();

    public SearchContactForm(SearchActionMove searchActionMove, SearchActionByPrefixMove searchByPrefix, CheckActionMove checkActionMove1) {
        this.searchActionMove = searchActionMove;
        this.searchByPrefix = searchByPrefix;
        this.checkActionMove = checkActionMove1;

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(20);
        this.setStyle("-fx-background-color: #2C3E50; -fx-font-family: 'Segoe UI', sans-serif; -fx-border-radius: 15px;");

        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setStyle("-fx-background-color: #BDC3C7; -fx-border-radius: 10px; -fx-font-size: 14px; -fx-padding: 15px; -fx-text-fill: #333333;");

        // Update the label and button fonts to be slightly thicker
        Label nameLabel = createStyledLabel("Search by Name:");
        Button searchByNameButton = createStyledButton("Search");
        searchByNameButton.setOnAction(e -> performSearch("name"));

        Label surnameLabel = createStyledLabel("Search by Surname:");
        Button searchBySurnameButton = createStyledButton("Search");
        searchBySurnameButton.setOnAction(e -> performSearch("surname"));

        Label addressLabel = createStyledLabel("Search by Address:");
        Button searchByAddressButton = createStyledButton("Search");
        searchByAddressButton.setOnAction(e -> performSearch("address"));

        Label phoneLabel = createStyledLabel("Search by Phone:");
        Button searchByPhoneButton = createStyledButton("Search");
        searchByPhoneButton.setOnAction(e -> performSearch("phone"));

        searchByPhoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.startsWith("+996 ")) {
                searchByPhoneField.setText("+996 ");
            }
        });

        this.addRow(0, nameLabel, searchByNameField, searchByNameButton);
        this.addRow(1, surnameLabel, searchBySurnameField, searchBySurnameButton);
        this.addRow(2, addressLabel, searchByAddressField, searchByAddressButton);
        this.addRow(3, phoneLabel, searchByPhoneField, searchByPhoneButton);
        this.add(resultArea, 0, 4, 3, 1);
    }

    private void performSearch(String searchType) {
        String input;
        List<Contact> results;

        try {
            // Validate inputs and search for contact
            switch (searchType) {
                case "name" -> {
                    input = searchByNameField.getText();
                    checkActionMove.checkForValidName(input);
                }
                case "surname" -> {
                    input = searchBySurnameField.getText();
                    checkActionMove.checkForValidSurname(input);
                }
                case "address" -> {
                    input = searchByAddressField.getText();
                    checkActionMove.checkForValidAddress(input);
                }
                case "phone" -> {
                    input = searchByPhoneField.getText();
                    checkActionMove.checkForValidPhoneNumber(input);
                    if (input.startsWith("+996 ")) {
                        input = input.substring(5);
                    }
                }
                default -> throw new IllegalArgumentException("Invalid search type");
            }

            if (input.trim().isEmpty()) {
                resultArea.setText("❗ Please enter a value to search.");
                return;
            }

            // Perform search based on the type
            switch (searchType) {
                case "name" -> results = searchActionMove.searchContactByName(input);
                case "surname" -> results = searchActionMove.searchContactBySurname(input);
                case "address" -> results = searchActionMove.searchContactByAddress(input);
                case "phone" -> results = searchActionMove.searchContactByPhone(input);
                default -> throw new IllegalArgumentException("Invalid search type");
            }

            displayResults(input, results, searchType);

        } catch (InputMismatchException e) {
            resultArea.setText("❌ " + e.getMessage());
        } catch (IllegalArgumentException e) {
            resultArea.setText("❌ " + e.getMessage());
        }
    }

    private void displayResults(String input, List<Contact> results, String searchType) {
        StringBuilder resultsText = new StringBuilder();

        if (results.isEmpty()) {
            resultsText.append("No exact matches found for ").append(searchType).append(": ").append(input).append("\n");

            resultsText.append("Looking for similar results by parameters of contact...\n");

            List<Contact> prefixResults = switch (searchType) {
                case "name" -> searchByPrefix.findByNamePrefix(input);
                case "surname" -> searchByPrefix.findBySurnamePrefix(input);
                case "address" -> searchByPrefix.findByAddressPrefix(input);
                case "phone" -> searchByPrefix.findByPhonePrefix(input);
                default -> throw new IllegalArgumentException("Invalid search type");
            };

            if (prefixResults.isEmpty()) {
                resultsText.append("No similar contacts found by prefix: ").append(input);
            } else {
                resultsText.append("Similar contacts by prefix:\n");
                for (Contact contact : prefixResults) {
                    resultsText.append(contact).append("\n");
                }
            }
        } else {
            resultsText.append("Matches found for ").append(searchType).append(": ").append(input).append("\n");
            for (Contact contact : results) {
                resultsText.append(contact).append("\n");
            }
        }

        resultArea.setText(resultsText.toString());
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", javafx.scene.text.FontWeight.BOLD, 14));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 12px; -fx-border-color: #BDC3C7;");
        button.setPrefWidth(100);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 12px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 12px;"));
        return button;
    }
}
