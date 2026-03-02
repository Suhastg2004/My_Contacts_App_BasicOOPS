package com.yourcompany.mycontact.user.usercontactmanagement;

import java.util.ArrayList;
import java.util.List;

public class PhoneSearch implements SearchStrategy {

    @Override
    public List<Contact> search(List<Contact> contacts, String keyword) {
        List<Contact> result = new ArrayList<>();
        for (Contact c : contacts) {
            if (c.getPhone().getNumber().contains(keyword)) {
                result.add(c);
            }
        }
        return result;
    }
}