package repository;

import fileService.FileService;
import model.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CreateContactMoveTest {

    private CreateContactMove createContactMove;
    private List<Contact> contacts;
    private FileService fileService;
    private File testFile;

    @BeforeEach
    void setUp() {
        testFile = new File("test_contacts.json");
        contacts = new ArrayList<>();
        createContactMove = new CreateContactMove();
        fileService = new FileService();


    }

    @Test
    @DisplayName("Test to create a contact and save to a temporary JSON file")
    public void createContactTest() {
        String input = "Azidin\nAmankulov\nJapan\n777 777 777\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        createContactMove.setScanner(new Scanner(System.in));

        createContactMove.createContact(contacts);

        assertEquals(1, contacts.size(), "List size should be 1 after creating a contact");
        assertEquals("Azidin", contacts.get(0).getName(), "Contact name should be 'Azidin'");
        assertEquals("Amankulov", contacts.get(0).getSurname(), "Contact last name should be 'Amankulov'");
        assertEquals("Japan", contacts.get(0).getAddress(), "Contact address should be 'Japan'");
        assertEquals("+996 777 777 777", contacts.get(0).getPhone(), "Contact phone should be '777 777 777'");

        fileService.write(contacts);

        List<Contact> readContacts = fileService.read();

        assertEquals(1, readContacts.size(), "File should contain exactly 1 contact");
        assertEquals("Azidin", readContacts.get(0).getName(), "Contact name in file should be 'Azidin'");
        assertEquals("Amankulov", readContacts.get(0).getSurname(), "Contact last name in file should be 'Amankulov'");
        assertEquals("Japan", readContacts.get(0).getAddress(), "Contact address in file should be 'Japan'");
        assertEquals("+996 777 777 777", readContacts.get(0).getPhone(), "Contact phone in file should be '777 777 777'");
    }


    @AfterEach
    void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}
