package repository;

import model.Contact;
import service.SearchAction;

import java.util.*;

public class SearchActionMove implements SearchAction {


    Scanner SC = new Scanner(System.in);
    private final SearchActionByPrefixMove searchByPrefix;
    private final CheckActionMove checkActionMove;

    public SearchActionMove() {
        searchByPrefix = new SearchActionByPrefixMove();
        checkActionMove = new CheckActionMove();
    }

    @Override
    public List<Contact> searchContactByName(List<Contact> contacts) {
        List<Contact> foundContacts = new ArrayList<>();
        try {
            System.out.print("Enter name to search: ");
            String next = SC.nextLine();
            checkActionMove.checkStringForEmpty(next);
            foundContacts = contacts.stream()
                    .filter(contact -> contact.getName().equals(next))
                    .toList();
            if (!foundContacts.isEmpty()) {

                foundContacts.forEach(contact -> System.out.println("🔍 Contact Found: " + contact));
            } else {
                System.out.println("❗No contact found with the such name: " + next);
                searchByPrefix.findByNamePrefix(contacts, next);
            }
        } catch (InputMismatchException e) {
            System.err.print("❌ Invalid input. Please enter the name to search.\n");
            e.printStackTrace();
        }
        return foundContacts;
    }


    @Override
    public List<Contact> searchContactBySurname(List<Contact> contacts) {
        List<Contact> foundContacts = new ArrayList<>();
        try {
            System.out.print("Enter surname to search: ");
            String next = SC.nextLine();
            checkActionMove.checkStringForEmpty(next);
            foundContacts = contacts.stream()
                    .filter(contact -> contact.getSurname().equals(next))
                    .toList();
            if (!foundContacts.isEmpty()) {

                foundContacts.forEach(contact -> System.out.println("🔍 Contact Found: " + contact));
            } else {
                System.out.println("❗No contact found with the such surname: " + next);
                searchByPrefix.findBySurnamePrefix(contacts, next);
            }
        } catch (InputMismatchException e) {
            System.err.print("❌ Invalid input. Please enter the surname to search.\n");
            e.printStackTrace();
        }
        return foundContacts;
    }

    @Override
    public List<Contact> searchContactByAddress(List<Contact> contacts) {
        List<Contact> foundContacts = new ArrayList<>();
        try {
            System.out.print("Enter address to search: ");
            String next = SC.nextLine();
            checkActionMove.checkStringForEmpty(next);
            foundContacts = contacts.stream()
                    .filter(contact -> contact.getAddress().equals(next))
                    .toList();
            if (!foundContacts.isEmpty()) {

                foundContacts.forEach(contact -> System.out.println("🔍 Contact Found: " + contact));
            } else {
                System.out.println("❗No contact found with the such address: " + next);
                searchByPrefix.findByAddressPrefix(contacts, next);
            }
        } catch (InputMismatchException e) {
            System.err.print("❌ Invalid input. Please enter the address to search.\n");
            e.printStackTrace();
        }


        return foundContacts;
    }

    @Override
    public List<Contact> searchContactByPhone(List<Contact> contacts) {
        List<Contact> foundContacts = new ArrayList<>();
        try {
            System.out.print("Enter phone number to search: +996 ");
            String next = SC.nextLine();
            checkActionMove.checkStringForEmpty(next);
            String cleanPhone = next.replaceAll("\\D", "");
            String finalFormattedPhone = "+996 " + cleanPhone.replaceAll("(.{3})(.{3})(.{3})", "$1 $2 $3");

            foundContacts = contacts.stream()
                    .filter(contact -> contact.getPhone().equals(finalFormattedPhone))
                    .toList();

            if (!foundContacts.isEmpty()) {
                foundContacts.forEach(contact -> System.out.println("🔍 Contact Found: " + contact));
            } else {
                System.out.println("❗No contact found with the phone number: " + finalFormattedPhone);
                String withoutCountryCode = finalFormattedPhone.replaceFirst("^\\+996\\s*", "");
                searchByPrefix.findByPhonePrefix(contacts, withoutCountryCode);
            }
        } catch (InputMismatchException e) {
            System.err.println("❌ Invalid input. Please enter the phone number to search.\n");
            e.printStackTrace();
        }
        return foundContacts;
    }

    public void setScanner(Scanner scanner) {
        this.SC = scanner;
    }
}
