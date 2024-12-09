package repository;

import model.Contact;
import service.CreateAction;

import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class CreateContactMove implements CreateAction {

    Scanner SC = new Scanner(System.in);
    CheckActionMove checkActionMove;

    public CreateContactMove() {
        checkActionMove = new CheckActionMove();
    }

    @Override
    public void createContact(List<Contact> contacts) {
        System.out.println("\n--- CREATE NEW CONTACT ---");

        try {
            System.out.print("Enter your name: ");
            String name = SC.nextLine();
            checkActionMove.checkForValidName(name);

            System.out.print("Enter your surname: ");
            String surname = SC.nextLine();
            checkActionMove.checkForValidSurname(surname);

            System.out.print("Enter your address: ");
            String address = SC.nextLine();
            checkActionMove.checkForValidAddress(address);

            System.out.print("Enter your phone number: +996 ");
            String phone = SC.nextLine();
            checkActionMove.checkForValidPhoneNumber(phone);
            String validNumber = checkActionMove.formatPhoneNumber(phone);

            Contact contact = new Contact(name, surname, address, validNumber);

            contacts.add(contact);
            System.out.println("✅ Contact added successfully!\n");
        } catch (InputMismatchException e) {
            System.err.println("\n❌ Error: " + e.getMessage() + " Contact not added.\n");
            System.out.print("❗Incorrect input, Returning to the main menu...\n");
            e.printStackTrace();
        }
    }


    public void setScanner(Scanner scanner) {
        this.SC = scanner;
    }


}
