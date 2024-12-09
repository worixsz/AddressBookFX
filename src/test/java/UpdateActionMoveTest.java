package repository;

import fileService.FileService;
import model.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateActionMoveTest {


    private List<Contact> contactList;

    private UpdateActionMove updateActionMove;

    private SearchActionMove searchActionMove;

    private FileService fileService;

    private File testFile;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        testFile = new File("test_contacts.json");
        updateActionMove = new UpdateActionMove();
        searchActionMove = new SearchActionMove();
        contactList = List.of(
                new Contact("Aibek", "Mahronovich", "China", "+996 666 666 666")
        );
    }

    @Test
    @DisplayName("Test for update valid contact by name")
    void updateValidContactByNameTest() {

        String simulatedInput = """
                Aibek
                1
                Michael
                Williams
                France 93A
                555 555 555
                """;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Scanner sharedScanner = new Scanner(System.in);

        updateActionMove.setScanner(sharedScanner);
        searchActionMove.setScanner(sharedScanner);

        updateActionMove.setSearch(searchActionMove);

        updateActionMove.updateContactByName(contactList);
        fileService.write(contactList);


        assertEquals("Michael", contactList.get(0).getName());
        assertEquals("Williams", contactList.get(0).getSurname());
        assertEquals("France 93A", contactList.get(0).getAddress());
        assertEquals("+996 555 555 555", contactList.get(0).getPhone());

        List<Contact> readContacts = fileService.read();

        assertEquals(1, readContacts.size(), "File should contain exactly 1 contact");
        assertEquals("Michael", readContacts.get(0).getName(), "Contact name in file should be 'Azidin'");
        assertEquals("Williams", readContacts.get(0).getSurname(), "Contact last name in file should be 'Amankulov'");
        assertEquals("France 93A", readContacts.get(0).getAddress(), "Contact address in file should be 'Japan'");
        assertEquals("+996 555 555 555", readContacts.get(0).getPhone(), "Contact phone in file should be '777 777 777'");
    }


    @Test
    @DisplayName("Test for invalid input during update by 'name'")
    void updateInvalidContactByNameTest() {
        String simulatedInput = """
                Aibek
                2
                """;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Scanner sharedScanner = new Scanner(System.in);

        updateActionMove.setScanner(sharedScanner);
        searchActionMove.setScanner(sharedScanner);

        updateActionMove.setSearch(searchActionMove);

        Contact originalContact = contactList.get(0);
        updateActionMove.updateContactByName(contactList);
        fileService.write(contactList);


        assertEquals(originalContact.getName(), contactList.get(0).getName());
        assertEquals(originalContact.getSurname(), contactList.get(0).getSurname());
        assertEquals(originalContact.getAddress(), contactList.get(0).getAddress());
        assertEquals(originalContact.getPhone(), contactList.get(0).getPhone());

        List<Contact> readContacts = fileService.read();

        assertEquals(originalContact.getName(), readContacts.get(0).getName());
        assertEquals(originalContact.getSurname(), readContacts.get(0).getSurname());
        assertEquals(originalContact.getAddress(), readContacts.get(0).getAddress());
        assertEquals(originalContact.getPhone(), readContacts.get(0).getPhone());

    }

    @Test
    @DisplayName("Test for update valid contact by surname")
    void updateValidContactBySurnameTest() {

        String simulatedInput = """
                Mahronovich
                1
                Michael
                Williams
                France 93A
                555 555 555
                """;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Scanner sharedScanner = new Scanner(System.in);

        updateActionMove.setScanner(sharedScanner);
        searchActionMove.setScanner(sharedScanner);

        updateActionMove.setSearch(searchActionMove);

        updateActionMove.updateContactBySurname(contactList);
        fileService.write(contactList);

        assertEquals("Michael", contactList.get(0).getName());
        assertEquals("Williams", contactList.get(0).getSurname());
        assertEquals("France 93A", contactList.get(0).getAddress());
        assertEquals("+996 555 555 555", contactList.get(0).getPhone());

        List<Contact> readContacts = fileService.read();

        assertEquals("Michael", readContacts.get(0).getName());
        assertEquals("Williams", readContacts.get(0).getSurname());
        assertEquals("France 93A", readContacts.get(0).getAddress());
        assertEquals("+996 555 555 555", readContacts.get(0).getPhone());


    }

    @Test
    @DisplayName("Test for invalid input during update by 'surname'")
    void updateInvalidContactBySurnameTest() {
        String simulatedInput = """
                Aibek
                invalidIndex
                """;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Scanner sharedScanner = new Scanner(System.in);

        updateActionMove.setScanner(sharedScanner);
        searchActionMove.setScanner(sharedScanner);

        updateActionMove.setSearch(searchActionMove);

        Contact originalContact = contactList.get(0);
        updateActionMove.updateContactBySurname(contactList);
        fileService.write(contactList);


        assertEquals(originalContact.getName(), contactList.get(0).getName());
        assertEquals(originalContact.getSurname(), contactList.get(0).getSurname());
        assertEquals(originalContact.getAddress(), contactList.get(0).getAddress());
        assertEquals(originalContact.getPhone(), contactList.get(0).getPhone());

        List<Contact> foundContact = fileService.read();

        assertEquals(originalContact.getName(), foundContact.get(0).getName());
        assertEquals(originalContact.getSurname(), foundContact.get(0).getSurname());
        assertEquals(originalContact.getAddress(), foundContact.get(0).getAddress());
        assertEquals(originalContact.getPhone(), foundContact.get(0).getPhone());

    }

    @Test
    @DisplayName("Test for update valid contact by address")
    void updateValidContactByAddressTest() {

        String simulatedInput = """
                China
                1
                Michael
                Williams
                France 93A
                555 555 555
                """;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Scanner sharedScanner = new Scanner(System.in);

        updateActionMove.setScanner(sharedScanner);
        searchActionMove.setScanner(sharedScanner);

        updateActionMove.setSearch(searchActionMove);

        updateActionMove.updateContactByAddress(contactList);
        fileService.write(contactList);


        assertEquals("Michael", contactList.get(0).getName());
        assertEquals("Williams", contactList.get(0).getSurname());
        assertEquals("France 93A", contactList.get(0).getAddress());
        assertEquals("+996 555 555 555", contactList.get(0).getPhone());

        List<Contact> foundContact = fileService.read();

        assertEquals("Michael", foundContact.get(0).getName());
        assertEquals("Williams", foundContact.get(0).getSurname());
        assertEquals("France 93A", foundContact.get(0).getAddress());
        assertEquals("+996 555 555 555", foundContact.get(0).getPhone());
    }

    @Test
    @DisplayName("Test for invalid input during update by 'address'")
    void updateInvalidContactByAddressTest() {
        String simulatedInput = """
                Aibek
                invalidIndex
                """;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Scanner sharedScanner = new Scanner(System.in);

        updateActionMove.setScanner(sharedScanner);
        searchActionMove.setScanner(sharedScanner);

        updateActionMove.setSearch(searchActionMove);

        Contact originalContact = contactList.get(0);
        updateActionMove.updateContactByAddress(contactList);
        fileService.write(contactList);

        assertEquals(originalContact.getName(), contactList.get(0).getName());
        assertEquals(originalContact.getSurname(), contactList.get(0).getSurname());
        assertEquals(originalContact.getAddress(), contactList.get(0).getAddress());
        assertEquals(originalContact.getPhone(), contactList.get(0).getPhone());

        List<Contact> foundContact = fileService.read();

        assertEquals(originalContact.getName(), foundContact.get(0).getName());
        assertEquals(originalContact.getSurname(), foundContact.get(0).getSurname());
        assertEquals(originalContact.getAddress(), foundContact.get(0).getAddress());
        assertEquals(originalContact.getPhone(), foundContact.get(0).getPhone());


    }

    @Test
    @DisplayName("Test for update valid contact by phone number")
    void updateValidContactByPhoneNumberTest() {

        String simulatedInput = """
                666666666
                1
                Michael
                Williams
                France 93A
                555 555 555
                """;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Scanner sharedScanner = new Scanner(System.in);

        updateActionMove.setScanner(sharedScanner);
        searchActionMove.setScanner(sharedScanner);

        updateActionMove.setSearch(searchActionMove);

        updateActionMove.updateContactByPhone(contactList);
        fileService.write(contactList);

        assertEquals("Michael", contactList.get(0).getName());
        assertEquals("Williams", contactList.get(0).getSurname());
        assertEquals("France 93A", contactList.get(0).getAddress());
        assertEquals("+996 555 555 555", contactList.get(0).getPhone());

        List<Contact> foundContact = fileService.read();

        assertEquals("Michael", foundContact.get(0).getName());
        assertEquals("Williams", foundContact.get(0).getSurname());
        assertEquals("France 93A", foundContact.get(0).getAddress());
        assertEquals("+996 555 555 555", foundContact.get(0).getPhone());

    }

    @Test
    @DisplayName("Test for invalid input during update by 'phone number'")
    void updateInvalidContactByPhoneNumberTest() {
        String simulatedInput = """
                Aibek
                invalidIndex
                """;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Scanner sharedScanner = new Scanner(System.in);

        updateActionMove.setScanner(sharedScanner);
        searchActionMove.setScanner(sharedScanner);

        updateActionMove.setSearch(searchActionMove);

        Contact originalContact = contactList.get(0);
        updateActionMove.updateContactByPhone(contactList);
        fileService.write(contactList);

        assertEquals(originalContact.getName(), contactList.get(0).getName());
        assertEquals(originalContact.getSurname(), contactList.get(0).getSurname());
        assertEquals(originalContact.getAddress(), contactList.get(0).getAddress());
        assertEquals(originalContact.getPhone(), contactList.get(0).getPhone());

        List<Contact> foundContact = fileService.read();

        assertEquals(originalContact.getName(), foundContact.get(0).getName());
        assertEquals(originalContact.getSurname(), foundContact.get(0).getSurname());
        assertEquals(originalContact.getAddress(), foundContact.get(0).getAddress());
        assertEquals(originalContact.getPhone(), foundContact.get(0).getPhone());


    }

    @AfterEach
    void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

}