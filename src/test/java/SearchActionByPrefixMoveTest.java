package repository;

import model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchActionByPrefixMoveTest {

    private List<Contact> contactList;

    private SearchActionByPrefixMove prefixMove;

    @BeforeEach
    void setUp() {
        prefixMove = new SearchActionByPrefixMove();
        contactList = Arrays.asList(
                new Contact("Azidin", "Amankulov", "Japan", "996777777777"),
                new Contact("Aibek", "Mahronovich", "China", "9966666666"),
                new Contact("Azidin", "Mahronovich", "Japan", "996777777777"),
                new Contact("John", "Doe", "USA", "123456789"),
                new Contact("Jane", "Smith", "Canada", "987654321"),
                new Contact("Emily", "Johnson", "UK", "444555666"),
                new Contact("Michael", "Brown", "Australia", "555666777"),
                new Contact("Sophia", "Williams", "Germany", "888999000"),
                new Contact("Liam", "Miller", "France", "111222333"),
                new Contact("Olivia", "Davis", "Italy", "222333444")
        );

    }


    @Test
    @DisplayName("Test for checking valid contact by name prefix")
    public void findByValidNamePrefixTest() {
        String validInput = "Azi";

        prefixMove.findByNamePrefix(contactList, validInput);

        List<Contact> filteredContacts = contactList.stream()
                .filter(contact -> contact.getName().startsWith(validInput))
                .toList();

        assertEquals(2, filteredContacts.size(), "Should find two contacts with the name prefix 'A'");

        assertTrue(filteredContacts.get(0).getName().startsWith(validInput), "First contact should start with 'A'");
        assertTrue(filteredContacts.get(1).getName().startsWith(validInput), "Second contact should start with 'A'");

    }

    @Test
    @DisplayName("Test for checking invalid contact by name prefix")
    public void findByInvalidNamePrefixTest() {

        String invalidInput = "Unknown\n";
        prefixMove.findByNamePrefix(contactList, invalidInput);

        List<Contact> filteredContacts = contactList.stream()
                .filter(contact -> contact.getName().startsWith(invalidInput))
                .toList();

        assertEquals(0, filteredContacts.size(), "Should not find any contact for invalid input");

    }

    @Test
    @DisplayName("Test for checking valid contact by surname prefix")
    public void findByValidSurnamePrefixTest() {
        String validInput = "Sm";

        prefixMove.findBySurnamePrefix(contactList, validInput);

        List<Contact> filteredContacts = contactList.stream()
                .filter(contact -> contact.getSurname().startsWith(validInput))
                .toList();

        assertEquals(1, filteredContacts.size(), "Should find one contacts with the surname prefix 'Sm'");

        assertTrue(filteredContacts.getFirst().getSurname().startsWith(validInput), "First contact should start with 'Sm'");

    }

    @Test
    @DisplayName("Test for checking invalid contact by surname prefix")
    public void findByInvalidSurnamePrefixTest() {

        String invalidInput = "Unknown\n";
        prefixMove.findBySurnamePrefix(contactList, invalidInput);

        List<Contact> filteredContacts = contactList.stream()
                .filter(contact -> contact.getSurname().startsWith(invalidInput))
                .toList();

        assertEquals(0, filteredContacts.size(), "Should not find any contact for invalid input");

    }

    @Test
    @DisplayName("Test for checking valid contact by address prefix")
    public void findByValidAddressPrefixTest() {
        String validInput = "Ge";

        prefixMove.findByAddressPrefix(contactList, validInput);

        List<Contact> filteredContacts = contactList.stream()
                .filter(contact -> contact.getAddress().startsWith(validInput))
                .toList();

        assertEquals(1, filteredContacts.size(), "Should find one contacts with the address prefix 'Ge'");
        assertTrue(filteredContacts.getFirst().getAddress().startsWith(validInput), "First contact should start with 'Ge'");

    }

    @Test
    @DisplayName("Test for checking invalid contact by address prefix")
    public void findByInvalidAddressPrefixTest() {

        String invalidInput = "Unknown\n";
        prefixMove.findByAddressPrefix(contactList, invalidInput);

        List<Contact> filteredContacts = contactList.stream()
                .filter(contact -> contact.getAddress().startsWith(invalidInput))
                .toList();

        assertEquals(0, filteredContacts.size(), "Should not find any contact for invalid input");

    }


    @Test
    @DisplayName("Test for checking valid contact by phone prefix")
    public void findByValidPhonePrefixTest() {
        String validInput = "996";

        prefixMove.findByPhonePrefix(contactList, validInput);

        List<Contact> filteredContacts = contactList.stream()
                .filter(contact -> contact.getPhone().startsWith(validInput))
                .toList();

        assertEquals(3, filteredContacts.size(), "Should find three contacts with the phone prefix '996'");
        assertTrue(filteredContacts.get(0).getPhone().startsWith(validInput), "First contact should start with '996'");
        assertTrue(filteredContacts.get(1).getPhone().startsWith(validInput), "Second contact should start with '996'");
        assertTrue(filteredContacts.get(2).getPhone().startsWith(validInput), "Third contact should start with '996'");

    }


    @Test
    @DisplayName("Test for checking invalid contact by phone prefix")
    public void findByInvalidPhonePrefixTest() {

        String invalidInput = "Unknown\n";
        prefixMove.findByPhonePrefix(contactList, invalidInput);

        List<Contact> filteredContacts = contactList.stream()
                .filter(contact -> contact.getPhone().startsWith(invalidInput))
                .toList();

        assertEquals(0, filteredContacts.size(), "Should not find any contact for invalid input");

    }
}
