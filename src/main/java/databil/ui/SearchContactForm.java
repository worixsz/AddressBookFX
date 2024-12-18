package databil.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Contact;
import repository.DataProcessorImpl;
import repository.SearchServiceImpl;
import repository.SearchPrefixServiceImpl;

import java.util.InputMismatchException;
import java.util.List;

public class SearchContactForm extends GridPane {

    private final SearchServiceImpl searchServiceImpl;
    private final SearchPrefixServiceImpl searchByPrefix;
    private final DataProcessorImpl dataProcessorImpl;

    private final TextField searchByNameField = new TextField();
    private final TextField searchBySurnameField = new TextField();
    private final TextField searchByAddressField = new TextField();
    private final TextField searchByPhoneField = new TextField();

    private final TextArea resultArea = new TextArea();

    public SearchContactForm(SearchServiceImpl searchServiceImpl, SearchPrefixServiceImpl searchByPrefix, DataProcessorImpl dataProcessorImpl1) {
        this.searchServiceImpl = searchServiceImpl;
        this.searchByPrefix = searchByPrefix;
        this.dataProcessorImpl = dataProcessorImpl1;

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(20);
        this.setStyle("-fx-background-color: #1A2A36; -fx-font-family: 'Segoe UI', sans-serif;");

        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setStyle("-fx-background-color: #BDC3C7; -fx-border-radius: 10px; -fx-font-size: 14px; -fx-padding: 15px; -fx-text-fill: #333333;");

        Label nameLabel = createStyledLabel("Search by Name:");
        Button searchByNameButton = createStyledButton();
        searchByNameButton.setOnAction(_ -> performSearch("name"));

        Label surnameLabel = createStyledLabel("Search by Surname:");
        Button searchBySurnameButton = createStyledButton();
        searchBySurnameButton.setOnAction(_ -> performSearch("surname"));

        Label addressLabel = createStyledLabel("Search by Address:");
        Button searchByAddressButton = createStyledButton();
        searchByAddressButton.setOnAction(_ -> performSearch("address"));

        Label phoneLabel = createStyledLabel("Search by Phone:");
        Button searchByPhoneButton = createStyledButton();
        searchByPhoneButton.setOnAction(_ -> performSearch("phone"));

        searchByPhoneField.textProperty().addListener((_, _, newValue) -> {
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
            switch (searchType) {
                case "name" -> {
                    input = searchByNameField.getText();
                    dataProcessorImpl.regexName(input);
                }
                case "surname" -> {
                    input = searchBySurnameField.getText();
                    dataProcessorImpl.regexSurname(input);
                }
                case "address" -> {
                    input = searchByAddressField.getText();
                    dataProcessorImpl.regexAddress(input);
                }
                case "phone" -> {
                    input = searchByPhoneField.getText();
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

            switch (searchType) {
                case "name" -> results = searchServiceImpl.searchContactByName(input);
                case "surname" -> results = searchServiceImpl.searchContactBySurname(input);
                case "address" -> results = searchServiceImpl.searchContactByAddress(input);
                case "phone" -> results = searchServiceImpl.searchContactByPhone(input);
                default -> throw new IllegalArgumentException("Invalid search type");
            }

            displayResults(input, results, searchType);

        } catch (InputMismatchException | IllegalArgumentException e) {
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

    private Button createStyledButton() {
        Button button = new Button("Search");
        button.setStyle("-fx-background-color: #34495E;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 12px; -fx-background-radius: 6px;" +
                " -fx-border-radius: 5px;");
        button.setPrefWidth(80);
        button.setPrefHeight(20);

        button.setOnMouseEntered(_ -> button.setStyle("-fx-background-color: #2C3E50;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 12px; -fx-background-radius: 6px;"));
        button.setOnMouseExited(_ -> button.setStyle("-fx-background-color: #34495E;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 12px; -fx-background-radius: 6px;"));
        button.setOnMouseClicked(_ -> button.setStyle("-fx-background-color: #2C3E50;" +
                " -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-font-size: 12px; -fx-background-radius: 6px;"));
        return button;
    }
}
