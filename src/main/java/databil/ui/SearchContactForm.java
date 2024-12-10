package databil.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);

        resultArea.setEditable(false);

        Label nameLabel = new Label("Search by Name:");
        Button searchByNameButton = new Button("Search");
        searchByNameButton.setOnAction(e -> performSearch("name"));

        Label surnameLabel = new Label("Search by Surname:");
        Button searchBySurnameButton = new Button("Search");
        searchBySurnameButton.setOnAction(e -> performSearch("surname"));

        Label addressLabel = new Label("Search by Address:");
        Button searchByAddressButton = new Button("Search");
        searchByAddressButton.setOnAction(e -> performSearch("address"));

        Label phoneLabel = new Label("Search by Phone:");
        Button searchByPhoneButton = new Button("Search");
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
}
