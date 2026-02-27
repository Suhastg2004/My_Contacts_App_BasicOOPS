package com.yourcompany.mycontact.user.usercontactmanagement;

public class PersonContact extends Contact {

    public PersonContact(String name, PhoneNumber phone, String email) {
        super(name, phone, email);
    }

    public PersonContact(PersonContact other) {
        super(other);
    }

    @Override
    public String getContactType() {
        return "PERSON";
    }

    public PersonContact withName(String newName) {
        PersonContact copy = new PersonContact(this);
        copy.setName(newName);
        return copy;
    }

    public PersonContact withPhone(String newPhoneDigits) {
        PersonContact copy = new PersonContact(this);
        copy.setPhone(new PhoneNumber(newPhoneDigits));
        return copy;
    }

    public PersonContact withEmail(String newEmail) {
        PersonContact copy = new PersonContact(this);
        copy.setEmail(newEmail);
        return copy;
    }
}