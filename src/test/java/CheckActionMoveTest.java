package repository;

import model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckActionMoveTest {

    private CheckActionMove validator;
    private List<Contact> contactList;

    @BeforeEach
    void setUp() {
        validator = new CheckActionMove();
        Contact contactOne = new Contact("Azidin", "Amankulov", "123 Street", "+996 777 777 777");
        Contact contactTwo = new Contact("Aibek", "Mahronovich", "113 Street", "+996 663 667 669");
        contactList = List.of(contactOne, contactTwo);
    }


    @Test
    @DisplayName("Tests to show exist contacts")
    public void showContactTest() {
        assertDoesNotThrow(() -> validator.showContact(contactList));
        List<Contact> emptyContacts = new ArrayList<>();
        assertDoesNotThrow(() -> validator.showContact(emptyContacts));
    }

    @Test
    public void testEmptyString() {
        InputMismatchException thrown = assertThrows(InputMismatchException.class, () -> validator.checkStringForEmpty("  "));
        assertEquals("The input cannot be empty.", thrown.getMessage());
    }


    @Test
    public void checkPhoneNumberTest() {

        assertDoesNotThrow(() -> validator.checkPhoneNumber(contactList.get(0).getPhone()));
        String invalidPhone = "500 344 919";
        InputMismatchException thrown = assertThrows(InputMismatchException.class,
                () -> validator.checkPhoneNumber(invalidPhone));
        assertEquals("❗Incorrect number format to save.", thrown.getMessage());

    }

    @Test
    @DisplayName("Test valid names")
    void checkForValidNameTest() {
        assertDoesNotThrow(() -> validator.checkForValidName("John"));
        assertDoesNotThrow(() -> validator.checkForValidName("Anne-Marie"));
        assertDoesNotThrow(() -> validator.checkForValidName("O'Connor"));
    }

    @Test
    @DisplayName("Test invalid names")
    void checkForInvalidNameTest() {
        assertThrows(InputMismatchException.class, () -> validator.checkForValidName(""));
        assertThrows(InputMismatchException.class, () -> validator.checkForValidName("123John"));
        assertThrows(InputMismatchException.class, () -> validator.checkForValidName("john"));
    }

    @Test
    @DisplayName("Test valid surnames")
    void checkForValidSurnameTest() {
        assertDoesNotThrow(() -> validator.checkForValidSurname("Smith"));
        assertDoesNotThrow(() -> validator.checkForValidSurname("Mc'Donald"));
        assertDoesNotThrow(() -> validator.checkForValidSurname("O'Neil"));
    }

    @Test
    @DisplayName("Test invalid surnames")
    void checkForInvalidSurnameTest() {
        assertThrows(InputMismatchException.class, () -> validator.checkForValidSurname(""));
        assertThrows(InputMismatchException.class, () -> validator.checkForValidSurname("Smith@"));
        assertThrows(InputMismatchException.class, () -> validator.checkForValidSurname("123Smith"));
    }

    @Test
    @DisplayName("Test valid addresses")
    void checkForValidAddress() {
        assertDoesNotThrow(() -> validator.checkForValidAddress("123 Main St."));
        assertDoesNotThrow(() -> validator.checkForValidAddress("Apartment"));
        assertDoesNotThrow(() -> validator.checkForValidAddress("Some Street, Block A, City"));
    }

    @Test
    @DisplayName("Test invalid addresses")
    void checkForInvalidAddress() {
        assertThrows(InputMismatchException.class, () -> validator.checkForValidAddress(""));
        assertThrows(InputMismatchException.class, () -> validator.checkForValidAddress("Main@Street"));
        assertThrows(InputMismatchException.class, () -> validator.checkForValidAddress("123<Street>"));
    }

    @Test
    @DisplayName("Test valid phone numbers")
    void checkForValidPhoneNumberTest() {
        assertDoesNotThrow(() -> validator.checkForValidPhoneNumber("+996 700 000 000"));
        assertDoesNotThrow(() -> validator.checkForValidPhoneNumber("+1-800-555-5555"));
        assertDoesNotThrow(() -> validator.checkForValidPhoneNumber("123456789"));
    }

    @Test
    @DisplayName("Test invalid phone numbers")
    void checkForInvalidPhoneNumberTest() {
        assertThrows(InputMismatchException.class, () -> validator.checkForValidPhoneNumber(""));
        assertThrows(InputMismatchException.class, () -> validator.checkForValidPhoneNumber("12345abc"));
        assertThrows(InputMismatchException.class, () -> validator.checkForValidPhoneNumber("++996 700 000 000"));
    }


}

