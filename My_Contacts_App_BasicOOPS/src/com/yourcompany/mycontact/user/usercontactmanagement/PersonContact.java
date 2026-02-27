package com.yourcompany.mycontact.user.usercontactmanagement;

public class PersonContact extends Contact {

    public PersonContact(String name, PhoneNumber phone, String email) {
        super(name, phone, email);
    }

    @Override
    public String getContactType() {
        return "PERSON";
    }
}
