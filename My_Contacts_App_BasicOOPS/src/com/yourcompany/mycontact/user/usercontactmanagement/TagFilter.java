package com.yourcompany.mycontact.user.usercontactmanagement;

import java.util.ArrayList;
import java.util.List;

public class TagFilter implements ContactFilter {
    private final String tag;
    public TagFilter(String tag) { this.tag = tag == null ? "" : tag.trim().toLowerCase(); }

    @Override
    public List<Contact> apply(List<Contact> contacts) {
        List<Contact> out = new ArrayList<>();
        for (Contact c : contacts) {
            if (!c.isDeleted() && c.getTags().contains(tag)) {
                out.add(c);
            }
        }
        return out;
    }
}