package repository;

import model.Contact;
import service.UpdateAction;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class UpdateActionMove implements UpdateAction {

    private SearchActionMove search;

    private final CheckActionMove checkActionMove;

    Scanner SC = new Scanner(System.in);

    public UpdateActionMove() {
        search = new SearchActionMove();
        checkActionMove = new CheckActionMove();
    }

    @Override
    public void updateContactByName(List<Contact> contacts) {
        try {
            List<Contact> foundContacts = search.searchContactByName(contacts);
            if (foundContacts.isEmpty()) {
                throw new NoSuchElementException("❗We cannot find the contact which you had searching");
            }

            System.out.print("Enter the index of the contact to update (1 to " + foundContacts.size() + "): ");
            if (!SC.hasNextInt()) {
                SC.nextLine();
                throw new InputMismatchException("❗Invalid input of contact to update.");
            }

            int userIndex = SC.nextInt() - 1;
            SC.nextLine();
            if (userIndex < 0 || userIndex >= foundContacts.size()) {
                throw new IndexOutOfBoundsException("❗Invalid index of contact to update.");
            }

            foundContacts.stream()
                    .skip(userIndex)
                    .findFirst()
                    .ifPresent(contact -> updateContact(contacts, contacts.indexOf(contact)));

        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("❗An unexpected error occurred: " + e.getMessage());
            System.out.println("❗Incorrect input, Returning to the main menu...\n");
            e.printStackTrace();
        }
    }


    @Override
    public void updateContactBySurname(List<Contact> contacts) {
        try {
            List<Contact> foundContacts = search.searchContactBySurname(contacts);
            if (foundContacts.isEmpty()) {
                throw new NoSuchElementException("❗We cannot find the contact which you had searching");
            }

            System.out.print("Enter the index of the contact to update (1 to " + foundContacts.size() + "): ");
            if (!SC.hasNextInt()) {
                SC.nextLine();
                throw new InputMismatchException("❗Invalid input of contact to update.");
            }

            int userIndex = SC.nextInt() - 1;
            SC.nextLine();
            if (userIndex < 0 || userIndex >= foundContacts.size()) {
                throw new IndexOutOfBoundsException("❗Invalid index of contact to update.");
            }

            foundContacts.stream()
                    .skip(userIndex)
                    .findFirst()
                    .ifPresent(contact -> updateContact(contacts, contacts.indexOf(contact)));

        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("❗An unexpected error occurred: " + e.getMessage());
            System.out.println("❗Incorrect input, Returning to the main menu...\n");
            e.printStackTrace();
        }
    }

    @Override
    public void updateContactByAddress(List<Contact> contacts) {
        try {
            List<Contact> foundContacts = search.searchContactByAddress(contacts);
            if (foundContacts.isEmpty()) {
                throw new NoSuchElementException("❗We cannot find the contact which you had searching");
            }

            System.out.print("Enter the index of the contact to update (1 to " + foundContacts.size() + "): ");
            if (!SC.hasNextInt()) {
                SC.nextLine();
                throw new InputMismatchException("❗Invalid input of contact to update.");
            }

            int userIndex = SC.nextInt() - 1;
            SC.nextLine();
            if (userIndex < 0 || userIndex >= foundContacts.size()) {
                throw new IndexOutOfBoundsException("❗Invalid index of contact to update.");
            }

            foundContacts.stream()
                    .skip(userIndex)
                    .findFirst()
                    .ifPresent(contact -> updateContact(contacts, contacts.indexOf(contact)));

        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("❗An unexpected error occurred: " + e.getMessage());
            System.out.println("❗Incorrect input, Returning to the main menu...\n");
            e.printStackTrace();
        }
    }


    @Override
    public void updateContactByPhone(List<Contact> contacts) {
        try {
            List<Contact> foundContacts = search.searchContactByPhone(contacts);
            if (foundContacts.isEmpty()) {
                throw new NoSuchElementException("❗We cannot find the contact which you had searching");
            }

            System.out.print("Enter the index of the contact to update (1 to " + foundContacts.size() + "): ");
            if (!SC.hasNextInt()) {
                SC.nextLine();
                throw new InputMismatchException("❗Invalid input of contact to update.");
            }

            int userIndex = SC.nextInt() - 1;
            SC.nextLine();
            if (userIndex < 0 || userIndex >= foundContacts.size()) {
                throw new IndexOutOfBoundsException("❗Invalid index of contact to update.");
            }

            foundContacts.stream()
                    .skip(userIndex)
                    .findFirst()
                    .ifPresent(contact -> updateContact(contacts, contacts.indexOf(contact)));

        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("❗An unexpected error occurred: " + e.getMessage());
            System.out.println("❗Incorrect input, Returning to the main menu...\n");
            e.printStackTrace();
        }
    }


    @Override
    public void updateContact(List<Contact> contacts, int indexForSave) {
        Contact contactToUpdate = contacts.get(indexForSave);

        try {
            System.out.print("Enter the new name: ");
            String newName = SC.nextLine();
            checkActionMove.checkForValidName(newName);

            System.out.print("Enter the new surname: ");
            String newSurname = SC.nextLine();
            checkActionMove.checkForValidSurname(newSurname);

            System.out.print("Enter the new address: ");
            String newAddress = SC.nextLine();
            checkActionMove.checkForValidAddress(newAddress);


            System.out.print("Enter the new phone number: +996 ");
            String newPhone = SC.nextLine();
            checkActionMove.checkForValidPhoneNumber(newPhone);
            String validNumber = checkActionMove.formatPhoneNumber(newPhone);

            contactToUpdate.setName(newName);
            contactToUpdate.setSurname(newSurname);
            contactToUpdate.setAddress(newAddress);
            contactToUpdate.setPhone(validNumber);
            System.out.println("✅ Contact added successfully!\n");

        } catch (InputMismatchException e) {
            System.err.println("\n❌ Error: " + e.getMessage() + " Contact not updated.\n");
            System.out.println("❗Incorrect input, Returning to the main menu...\n");
            e.printStackTrace();
        }
    }


    public void setScanner(Scanner scanner) {
        this.SC = scanner;
    }

    public void setSearch(SearchActionMove search) {
        this.search = search;
    }

}
