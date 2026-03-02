package com.yourcompany.mycontact.user.usercontactmanagement;

import java.util.List;

public interface SearchStrategy {
    List<Contact> search(List<Contact> contacts, String keyword);
}