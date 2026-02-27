package com.yourcompany.mycontact.user;

import com.yourcompany.mycontact.user.usermanagement.*;
import com.yourcompany.mycontact.user.userauthentication.*;
import java.util.Scanner;

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

            // ========= LOGIN =========
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


        } catch (Exception e) {
            System.out.println("\nRegistration failed: " + e.getMessage());
        }
    }
}