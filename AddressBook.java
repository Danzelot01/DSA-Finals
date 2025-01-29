import java.util.*;

class Contact implements Comparable<Contact> {
    String name;
    String phone;
    String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phone + ", Email: " + email;
    }

    @Override
    public int compareTo(Contact other) {
        return this.name.compareToIgnoreCase(other.name); // Case-insensitive comparison
    }
}

public class AddressBook {
    private List<Contact> contacts;
    private int currentContactIndex;
    private Scanner scanner;

    public AddressBook() {
        contacts = new ArrayList<>();
        currentContactIndex = -1;
        scanner = new Scanner(System.in);
    }

    public void addContact(String name, String phone, String email) {
        contacts.add(new Contact(name, phone, email));
        Collections.sort(contacts);
        if (currentContactIndex == -1) {
            currentContactIndex = 0;
        } else {
            currentContactIndex = contacts.indexOf(new Contact(name, phone, email));
        }
    }

    public void deleteContact(String name) {
        if (contacts.removeIf(c -> c.name.equalsIgnoreCase(name))) {
            System.out.println("Contact deleted successfully.");
            Collections.sort(contacts);
            if (contacts.isEmpty()) {
                currentContactIndex = -1;
            } else if (currentContactIndex >= contacts.size()) {
                currentContactIndex = contacts.size() - 1;
            }
        } else {
            System.out.println("Contact not found.");
        }
    }

    public List<Contact> searchContact(String name) {
        List<Contact> results = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.name.toLowerCase().contains(name.toLowerCase())) {
                results.add(contact);
            }
        }
        return results;
    }

    public void updateContact(String oldName, String newName, String newPhone, String newEmail) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).name.equalsIgnoreCase(oldName)) {
                contacts.set(i, new Contact(newName, newPhone, newEmail));
                Collections.sort(contacts);
                System.out.println("Contact updated successfully.");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    public void navigateForward() {
        if (!contacts.isEmpty() && currentContactIndex < contacts.size() - 1) {
            currentContactIndex++;
        } else if (contacts.isEmpty()){
          System.out.println("No contacts in the address book.");
        } else {
          System.out.println("You are at the end of the list.");
        }
    }

    public void navigateBackward() {
        if (currentContactIndex > 0) {
            currentContactIndex--;
        } else if (contacts.isEmpty()){
          System.out.println("No contacts in the address book.");
        } else {
          System.out.println("You are at the beginning of the list.");
        }
    }

    public void displayCurrentContact() {
        if (currentContactIndex != -1 && !contacts.isEmpty()) {
            System.out.println("Current Contact:\n" + contacts.get(currentContactIndex));
        } else {
            System.out.println("No contacts in the address book.");
        }
    }
    public void displayAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts in the address book.");
            return;
        }

        System.out.println("Available Contacts:");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i));
        }
    }

    public void navigateToContact(int index) {
        try {
            if (index > 0 && index <= contacts.size()) {
                System.out.println("Selected Contact:\n" + contacts.get(index - 1));
                currentContactIndex = index -1; //update current index
            } else {
                System.out.println("Invalid index. Please enter a number between 1 and " + contacts.size());
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index."); // Should not happen with the prior check, but good to have.
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        Scanner scanner = new Scanner(System.in); // Main scanner

        while (true) {
            System.out.println("\nAddress Book Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. Delete Contact");
            System.out.println("3. Search Contact");
            System.out.println("4. Update Contact");
            System.out.println("5. Navigate Forward");
            System.out.println("6. Navigate Backward");
            System.out.println("7. Display Current Contact");
            System.out.println("8. Display All Contacts and Navigate"); 
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    addressBook.addContact(name, phone, email);
                    break;
                case 2:
                    System.out.print("Enter name to delete: ");
                    String nameToDelete = scanner.nextLine();
                    addressBook.deleteContact(nameToDelete);
                    break;
                case 3:
                    System.out.print("Enter name to search: ");
                    String nameToSearch = scanner.nextLine();
                    List<Contact> searchResults = addressBook.searchContact(nameToSearch);
                    if (searchResults.isEmpty()) {
                        System.out.println("No contacts found.");
                    } else {
                        System.out.println("Search Results:");
                        for (Contact c : searchResults) {
                            System.out.println(c);
                        }
                    }
                    break;
                case 4:
                  System.out.print("Enter the name of the contact to update: ");
                  String oldName = scanner.nextLine();
                  System.out.print("Enter new name: ");
                  String newName = scanner.nextLine();
                  System.out.print("Enter new phone: ");
                  String newPhone = scanner.nextLine();
                  System.out.print("Enter new email: ");
                  String newEmail = scanner.nextLine();
                  addressBook.updateContact(oldName, newName, newPhone, newEmail);
                  break;
                case 5:
                    addressBook.navigateForward();
                    addressBook.displayCurrentContact();
                    break;
                case 6:
                    addressBook.navigateBackward();
                    addressBook.displayCurrentContact();
                    break;
                case 7:
                    addressBook.displayCurrentContact();
                    break;
                case 8:
                    addressBook.displayAllContacts();
                    if(!addressBook.contacts.isEmpty()){
                        System.out.print("Enter the number of the contact to view: ");
                        try {
                            int contactIndex = scanner.nextInt();
                            addressBook.navigateToContact(contactIndex);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            scanner.next(); // Clear the invalid input from the scanner
                        }
                    }
                    break;
                case 9:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}