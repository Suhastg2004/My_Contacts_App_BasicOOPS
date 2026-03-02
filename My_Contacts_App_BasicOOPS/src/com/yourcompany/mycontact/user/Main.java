//Version 8.0
//author - Suhas T G

package com.yourcompany.mycontact.user;

import com.yourcompany.mycontact.user.usermanagement.*;
import com.yourcompany.mycontact.user.userauthentication.*;
import com.yourcompany.mycontact.user.userprofilemanagement.*;
import com.yourcompany.mycontact.user.usercontactmanagement.*;

import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();

        try {
            System.out.println("=== User Registration ===");

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            System.out.print("User Type (FREE / PREMIUM): ");
            String type = scanner.nextLine();

            User user = userService.register(name, email, password, type);

            System.out.println("\nRegistered Successfully!");
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Type: " + user.getUserType());

            System.out.println("\n=== Login ===");

            AuthService authService;

            System.out.print("Email: ");
            String loginEmail = scanner.nextLine();

            System.out.print("Password: ");
            String loginPass = scanner.nextLine();

            authService = new AuthService(new BasicAuthStrategy(userService));

            var result = authService.login(loginEmail, loginPass);

            if (result.isPresent())
                System.out.println("Login Successful! Welcome " + result.get().getName());
            else
                System.out.println("Login Failed.");

            User loggedUser = SessionManager.getInstance().getLoggedInUser();

            CommandInvoker invoker = new CommandInvoker();

            System.out.println("\n=== Profile Management ===");
            System.out.println("1. Change Name");
            System.out.println("2. Change Email");
            System.out.println("3. Change Password");
            System.out.print("Choose option: ");

            int changeChoice = Integer.parseInt(scanner.nextLine());

            switch (changeChoice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    invoker.executeCommand(new UpdateNameCommand(loggedUser, newName));
                    break;
                case 2:
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    invoker.executeCommand(new UpdateEmailCommand(loggedUser, newEmail));
                    break;
                case 3:
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    invoker.executeCommand(new ChangePasswordCommand(loggedUser, newPassword));
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            System.out.println("\nProfile Updated Successfully!");
            System.out.println("Updated Name: " + loggedUser.getName());
            System.out.println("Updated Email: " + loggedUser.getEmail());

            System.out.println("\n=== Create Contact ===");

            System.out.print("Contact Name: ");
            String cName = scanner.nextLine();

            System.out.print("Phone Number (10 digits): ");
            String cPhone = scanner.nextLine();

            System.out.print("Email: ");
            String cEmail = scanner.nextLine();

            Contact contact = new PersonContact(cName, new PhoneNumber(cPhone), cEmail);

            System.out.println("\n=== UC-05: View Contact Details ===");
            System.out.println(contact);

            System.out.println("\n=== UC-06: Edit Contact ===");
            System.out.print("New Name: ");
            String eName = scanner.nextLine();
            System.out.print("New Phone (10 digits): ");
            String ePhone = scanner.nextLine();
            System.out.print("New Email: ");
            String eEmail = scanner.nextLine();

            PersonContact edited = (PersonContact) contact;
            boolean changed = false;

            try {
                if (!eName.isBlank()) { edited = edited.withName(eName); changed = true; }
                if (!ePhone.isBlank()) { edited = edited.withPhone(ePhone); changed = true; }
                if (!eEmail.isBlank()) { edited = edited.withEmail(eEmail); changed = true; }
            } catch (Exception ex) {
                System.out.println("Edit failed: " + ex.getMessage());
            }

            if (changed) {
                contact = edited;
                System.out.println("\nEdited Contact:");
                System.out.println(contact);
            }

            System.out.println("\n=== UC-07: Delete Contact ===");
            System.out.print("Delete? (Y/N): ");
            String d = scanner.nextLine();
            if (d.equalsIgnoreCase("Y")) {
                System.out.print("Soft or Hard? (S/H): ");
                String del = scanner.nextLine();
                if (del.equalsIgnoreCase("S")) {
                    contact.markDeleted();
                    System.out.println("Soft deleted.");
                } else if (del.equalsIgnoreCase("H")) {
                    contact = null;
                    System.out.println("Hard deleted.");
                }
            }

            System.out.println("\n=== UC-08: Bulk Operations ===");

            List<Contact> contacts = new ArrayList<>();
            if (contact != null) contacts.add(contact);
            contacts.add(new PersonContact("Alice", new PhoneNumber("8888888888"), "alice@mail.com"));
            contacts.add(new PersonContact("Bob", new PhoneNumber("9999999999"), ""));

            System.out.println("Contacts:");
            for (int i = 0; i < contacts.size(); i++)
                System.out.println((i + 1) + ") " + contacts.get(i).getName());

            System.out.print("Select multiple contacts (comma-separated): ");
            String in = scanner.nextLine();
            String[] nums = in.split(",");

            List<Integer> chosen = new ArrayList<>();
            for (String s : nums) {
                try { chosen.add(Integer.parseInt(s.trim()) - 1); } catch (Exception ignored) {}
            }

            System.out.println("1) Bulk Soft Delete");
            System.out.println("2) Bulk Hard Delete");
            System.out.println("3) Export Selected Contacts");
            System.out.print("Choose: ");
            String op = scanner.nextLine();

            if (op.equals("1")) {
                for (int idx : chosen) contacts.get(idx).markDeleted();
                System.out.println("Soft deleted selected contacts.");
            }
            else if (op.equals("2")) {
                chosen.sort(Collections.reverseOrder());
                for (int idx : chosen) contacts.remove(idx);
                System.out.println("Hard deleted selected contacts.");
            }
            else if (op.equals("3")) {
                try (PrintWriter out = new PrintWriter(new FileWriter("export.txt"))) {
                    for (int idx : chosen) out.println(contacts.get(idx));
                    System.out.println("Exported to export.txt");
                }
                catch (Exception ex) { System.out.println("Export failed."); }
            }

            System.out.println("\nUpdated List:");
            for (Contact c : contacts) System.out.println(c);
            
            System.out.println("\n=== UC-09: Search Contacts ===");

            System.out.println("Search by:");
            System.out.println("1) Name");
            System.out.println("2) Phone");
            System.out.println("3) Email");
            System.out.println("4) Tag");
            System.out.print("Choose: ");
            String searchChoice = scanner.nextLine();

            System.out.print("Enter keyword: ");
            String keyword = scanner.nextLine();

            SearchStrategy strategy = null;

            if (searchChoice.equals("1")) strategy = new NameSearch();
            else if (searchChoice.equals("2")) strategy = new PhoneSearch();
            else if (searchChoice.equals("3")) strategy = new EmailSearch();
            else if (searchChoice.equals("4")) strategy = new TagSearch();
            else {
                System.out.println("Invalid choice.");
            }

            if (strategy != null) {
                List<Contact> found = strategy.search(contacts, keyword);
                System.out.println("\nSearch Results:");

                if (found.isEmpty()) {
                    System.out.println("No matching contacts found.");
                } else {
                    for (Contact c : found) {
                        System.out.println(c);
                    }
                }
            }
            
           
        } catch (Exception e) {
            System.out.println("\nRegistration failed: " + e.getMessage());
        }
    }
}