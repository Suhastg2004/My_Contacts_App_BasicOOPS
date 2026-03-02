package com.yourcompany.mycontact.user.usercontactmanagement;

import java.util.List;

public interface ContactFilter {
    List<Contact> apply(List<Contact> contacts);
}