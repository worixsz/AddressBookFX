package repository;

import model.Contact;
import service.DeleteAction;

import java.util.List;
import java.util.Scanner;

public class DeleteActionMove implements DeleteAction {

    Scanner SC = new Scanner(System.in);
    private final CheckActionMove checkMove;

    public DeleteActionMove() {
        this.checkMove = new CheckActionMove();
    }

    @Override
    public void deleteContactByIndex(List<Contact> contacts) {
        try {
            System.out.println("\n--- DELETE CONTACT ---");

            if (contacts.isEmpty()) {
                System.out.println("❌ No contacts available to delete.\n");
            } else {
                checkMove.showContact(contacts);

                System.out.print("Enter the index of the contact to delete (1 to " + contacts.size() + "): ");
                int index = Integer.parseInt(SC.nextLine()) - 1;

                if (index >= 0 && index < contacts.size()) {
                    contacts.remove(index);
                    System.out.println("🗑️ Contact deleted successfully.\n");
                } else {
                    System.err.println("❗ Invalid index. Please enter a valid index between 1 and " + contacts.size() + ".\n");
                }
            }

        } catch (NumberFormatException e) {
            System.out.print("\n❌ Incorrect input, Returning to the main menu...\n");
            e.printStackTrace();
        }

    }

    public void setScanner(Scanner scanner) {
        this.SC = scanner;
    }


}
