package com.yourcompany.mycontact.user.usermanagement;

public class PasswordValidator {

    public static boolean isValid(String password) {
        return password != null && password.length() >= 6;
    }
}
