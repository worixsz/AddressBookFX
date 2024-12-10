package databil.ui;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Contact;
import repository.SearchActionByPrefixMove;
import repository.SearchActionMove;

import java.util.List;

public class SearchContactForm extends GridPane {

    private final SearchActionMove searchActionMove;
    private final SearchActionByPrefixMove searchByPrefix;
    private final TextField searchByNameField = new TextField();
    private final TextArea resultArea = new TextArea();

    public SearchContactForm() {
        searchActionMove = new SearchActionMove();
        searchByPrefix = new SearchActionByPrefixMove();

        resultArea.setEditable(false);

        Label nameLabel = new Label("Search by Name:");
        Button searchByNameButton = new Button("Search");

        this.addRow(0, nameLabel, searchByNameField, searchByNameButton);
        this.add(resultArea, 0, 1, 3, 1);

        searchByNameButton.setOnAction(_ -> performSearch());
    }

    private void performSearch() {
        String input = searchByNameField.getText();

        if (input.isEmpty()) {
            resultArea.setText("Please enter a value to search.");
            return;
        }

        // Основной поиск
        List<Contact> searchResults = searchActionMove.searchContactByName(input);

        StringBuilder resultsText = new StringBuilder();
        if (searchResults.isEmpty()) {
            resultsText.append("No exact matches found for: ").append(input).append("\n\n");

            // Поиск по префиксу
            resultsText.append("Looking for similar results by prefix...\n");

            List<Contact> prefixResults = searchByPrefix.findByNamePrefix(input);

            if (prefixResults.isEmpty()) {
                resultsText.append("No similar contacts found by prefix: ").append(input);
            } else {
                resultsText.append("Similar contacts by prefix:\n");
                for (Contact contact : prefixResults) {
                    resultsText.append(contact).append("\n");
                }
            }
        } else {
            resultsText.append("Exact matches found:\n");
            for (Contact contact : searchResults) {
                resultsText.append(contact).append("\n");
            }
        }

        resultArea.setText(resultsText.toString());
    }
}
