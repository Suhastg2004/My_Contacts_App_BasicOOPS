package com.yourcompany.mycontact.user.usercontactmanagement;

import java.util.ArrayList;
import java.util.List;

public class TagSearch implements SearchStrategy {

    @Override
    public List<Contact> search(List<Contact> contacts, String keyword) {
        List<Contact> result = new ArrayList<>();
        for (Contact c : contacts) {
            if (c.getTags().contains(keyword.toLowerCase())) {
                result.add(c);
            }
        }
        return result;
    }
}