package repository;

import fileService.FileService;
import model.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteActionMoveTest {

    private DeleteActionMove deleteActionMove;
    private FileService fileService;
    private File testFile;

    @BeforeEach
    void setUp() {
        testFile = new File("test_contacts.json");

        deleteActionMove = new DeleteActionMove();
        fileService = new FileService();

        List<Contact> contact = new ArrayList<>();
        contact.add(new Contact("Azidin", "Amankulov", "Japan", "+996 7777 777 777"));
        fileService.write(contact);
    }

    @Test
    @DisplayName("Test for valid index input to delete with real file interaction")
    public void deleteValidContactByContactTest() {
        String inputString = "1\n";
        System.setIn(new ByteArrayInputStream(inputString.getBytes()));
        deleteActionMove.setScanner(new Scanner(System.in));
        List<Contact> contactsFromFile = fileService.read();
        deleteActionMove.deleteContactByIndex(contactsFromFile);
        fileService.write(contactsFromFile);


        assertEquals(0, contactsFromFile.size(), "Contact should be deleted, list size should be 0 after deletion");
        assertFalse(contactsFromFile.stream()
                        .anyMatch(c -> c.getName().equals("Azidin")),
                "Contact 'Azidin' should have been deleted.");

        List<Contact> updatedContacts = fileService.read();
        assertEquals(0, updatedContacts.size(), "File should be empty after deletion");
    }

    @Test
    @DisplayName("Test for invalid index input to delete with real file interaction")
    public void deleteInvalidContactByContactTest() {
        String inputString = "5\n";
        System.setIn(new ByteArrayInputStream(inputString.getBytes()));
        deleteActionMove.setScanner(new Scanner(System.in));

        List<Contact> contactsFromFile = fileService.read();
        deleteActionMove.deleteContactByIndex(contactsFromFile);
        fileService.write(contactsFromFile);

        assertEquals(1, contactsFromFile.size(), "List size should remain the same after invalid input");
        assertTrue(contactsFromFile.stream()
                        .anyMatch(c -> c.getName().equals("Azidin")),
                "Contact 'Azidin' should still exist in the list.");

        List<Contact> updatedContacts = fileService.read();
        assertEquals(1, updatedContacts.size(), "File should still have 1 contact");
    }

    // После тестов удаляем временный файл
    @AfterEach
    void tearDown() {
        if (testFile.exists()) {
            testFile.delete(); // Удаляем временный файл после выполнения тестов
        }
    }
}
