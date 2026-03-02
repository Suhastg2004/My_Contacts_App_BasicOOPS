package com.yourcompany.mycontact.user.usercontactmanagement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateAfterFilter implements ContactFilter {
    private final LocalDate from; // inclusive
    public DateAfterFilter(LocalDate from) { this.from = from; }

    @Override
    public List<Contact> apply(List<Contact> contacts) {
        List<Contact> out = new ArrayList<>();
        for (Contact c : contacts) {
            if (!c.isDeleted() && !c.getCreatedAt().toLocalDate().isBefore(from)) {
                out.add(c);
            }
        }
        return out;
    }
}