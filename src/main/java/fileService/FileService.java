package fileService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Contact;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    private final ObjectMapper objectMapper;

    public FileService() {
        objectMapper = new ObjectMapper();

    }

    public List<Contact> read() {
        try {
            File file = new File("contacts.json");
            if (!file.exists()) {
                return new ArrayList<>();
            }

            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode contactsNode = rootNode.get("contacts");

            if (contactsNode != null && contactsNode.isArray()) {
                return objectMapper.convertValue(
                        contactsNode,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Contact.class)
                );
            }

            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read contacts from file", e);
        }
    }


    public void write(List<Contact> contacts) {
        try {
            File file = new File("contacts.json");

            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.set("contacts", objectMapper.valueToTree(contacts));

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, rootNode);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write contacts to file", e);
        }
    }

}
