//Version 6.0
//author - Suhas T G

package com.yourcompany.mycontact.user;

import com.yourcompany.mycontact.user.usermanagement.*;
import com.yourcompany.mycontact.user.userauthentication.*;
import com.yourcompany.mycontact.user.userprofilemanagement.*;
import com.yourcompany.mycontact.user.usercontactmanagement.*;

import java.util.Scanner;

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
            System.out.println("Enter new values (leave blank to keep current)");

            System.out.print("New Name: ");
            String eName = scanner.nextLine();

            System.out.print("New Phone (10 digits): ");
            String ePhone = scanner.nextLine();

            System.out.print("New Email: ");
            String eEmail = scanner.nextLine();

            PersonContact edited = (PersonContact) contact;
            boolean changed = false;

            try {
                if (!eName.isBlank()) {
                    edited = edited.withName(eName);
                    changed = true;
                }
                if (!ePhone.isBlank()) {
                    edited = edited.withPhone(ePhone);
                    changed = true;
                }
                if (!eEmail.isBlank()) {
                    edited = edited.withEmail(eEmail);
                    changed = true;
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Edit failed: " + ex.getMessage());
            }

            if (changed) {
                System.out.println("\nOriginal Contact:");
                System.out.println(contact);

                System.out.println("\nEdited Contact (new copy):");
                System.out.println(edited);

                contact = edited;
            } else {
                System.out.println("\nNo changes made.");
            }

            System.out.println("\n=== UC-07: Delete Contact ===");
            System.out.print("Do you want to delete this contact? (Y/N): ");
            String confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("Y")) {
                System.out.print("Soft delete or Hard delete? (S/H): ");
                String mode = scanner.nextLine().trim();

                try {
                    if (mode.equalsIgnoreCase("S")) {
                        contact.markDeleted();
                        System.out.println("Soft-deleted successfully.");
                        System.out.println("\nAfter soft delete:");
                        System.out.println(contact);
                    } else if (mode.equalsIgnoreCase("H")) {
                        contact = null;
                        System.out.println("Hard-deleted successfully.");
                        System.out.println("Exists after hard delete? NO");
                    } else {
                        System.out.println("Unknown option. Skipping delete.");
                    }
                } catch (Exception ex) {
                    System.out.println("Delete failed: " + ex.getMessage());
                }
            } else {
                System.out.println("Delete canceled.");
            }

        } catch (Exception e) {
            System.out.println("\nRegistration failed: " + e.getMessage());
        }
    }
}