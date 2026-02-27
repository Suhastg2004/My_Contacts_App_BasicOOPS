package com.yourcompany.mycontact.user.usercontactmanagement;

public class PhoneNumber {

    private String number;

    public PhoneNumber(String number) {
        if (!number.matches("[0-9]{10}"))
            throw new IllegalArgumentException("Invalid phone number!");
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}