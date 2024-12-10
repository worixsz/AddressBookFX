package databil.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Contact;
import repository.CheckActionMove;
import repository.CreateContactMove;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class CreateContactForm extends VBox {

    private final Contact contacts;

    public CreateContactForm(CreateContactMove contactService, CheckActionMove checkActionMove) {
        this.contacts = new Contact();

        // Устанавливаем стили для панели
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #2C3E50; -fx-font-family: 'Segoe UI', sans-serif;");
        this.setSpacing(10); // Задаем расстояние между элементами

        // Добавляем отступы (контейнерные) с увеличением отступов по бокам
        this.setPadding(new Insets(20, 100, 20, 100)); // сверху, справа, снизу, слева

        // Сообщение об успешном сохранении
        Text successMessage = new Text();
        successMessage.setFont(Font.font("Segoe UI", 14));
        successMessage.setFill(Color.LIMEGREEN);
        successMessage.setVisible(false);

        // Создание и стилизация компонентов
        Label nameLabel = createStyledLabel("Name");
        Label surnameLabel = createStyledLabel("Surname");
        Label addressLabel = createStyledLabel("Address");
        Label phoneLabel = createStyledLabel("Phone");

        TextField nameField = new TextField();
        TextField surnameField = new TextField();
        TextField addressField = new TextField();
        TextField phoneField = new TextField();

        Label nameErrorLabel = createErrorLabel();
        Label surnameErrorLabel = createErrorLabel();
        Label addressErrorLabel = createErrorLabel();
        Label phoneErrorLabel = createErrorLabel();

        Button saveButton = createStyledButton("Save");
        Button cancelButton = createStyledButton("Cancel");

        // Контейнер для кнопок
        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Добавление компонентов в форму
        this.getChildren().addAll(
                nameLabel, nameField, nameErrorLabel,
                surnameLabel, surnameField, surnameErrorLabel,
                addressLabel, addressField, addressErrorLabel,
                phoneLabel, phoneField, phoneErrorLabel,
                buttonBox,
                successMessage
        );

        // При изменении номера телефона добавляем +996, если его нет
        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Добавляем код страны только если он отсутствует
            if (!newValue.startsWith("+996")) {
                phoneField.setText("+996" + newValue.replaceAll("[^\\d]", ""));  // Убираем все нецифровые символы
            }
        });

        // Логика обработки нажатий
        saveButton.addEventHandler(MOUSE_CLICKED, e -> {
            boolean isValid = true;

            nameErrorLabel.setText("");
            surnameErrorLabel.setText("");
            addressErrorLabel.setText("");
            phoneErrorLabel.setText("");
            successMessage.setVisible(false);

            try {
                String name = nameField.getText();
                checkActionMove.checkForValidName(name);
                contacts.setName(name);

            } catch (InputMismatchException ex) {
                nameErrorLabel.setText(ex.getMessage());
                isValid = false;
            }
            try {
                String surname = surnameField.getText();
                checkActionMove.checkForValidSurname(surname);
                contacts.setSurname(surname);

            } catch (InputMismatchException ex) {
                surnameErrorLabel.setText(ex.getMessage());
                isValid = false;
            }

            try {
                String address = addressField.getText();
                checkActionMove.checkForValidAddress(address);
                contacts.setAddress(address);

            } catch (InputMismatchException ex) {
                addressErrorLabel.setText(ex.getMessage());
                isValid = false;
            }

            try {
                String phone = phoneField.getText();

                // Убираем код страны перед сохранением, если он был добавлен
                if (phone.startsWith("+996")) {
                    phone = phone.substring(4);  // Убираем +996
                }

                checkActionMove.checkForValidPhoneNumber(phone);
                String validNumber = checkActionMove.formatPhoneNumber(phone);
                contacts.setPhone(validNumber);

            } catch (InputMismatchException ex) {
                phoneErrorLabel.setText(ex.getMessage());
                isValid = false;
            }

            if (isValid) {
                List<Contact> saveContact = new ArrayList<>();
                saveContact.add(contacts);
                contactService.createContact(saveContact);

                nameField.clear();
                surnameField.clear();
                addressField.clear();
                phoneField.clear();

                successMessage.setText("Success Saved");
                successMessage.setVisible(true);
            }
        });

        cancelButton.addEventHandler(MOUSE_CLICKED, e -> {
            nameField.clear();
            surnameField.clear();
            addressField.clear();
            phoneField.clear();
            nameErrorLabel.setText("");
            surnameErrorLabel.setText("");
            addressErrorLabel.setText("");
            phoneErrorLabel.setText("");
            successMessage.setVisible(false);
        });
    }


    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", 14));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private Label createErrorLabel() {
        Label label = new Label();
        label.setStyle("-fx-text-fill: red;");
        return label;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 12px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #34495E; -fx-text-fill: white;"));
        return button;
    }
}
