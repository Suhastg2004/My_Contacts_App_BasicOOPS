package com.yourcompany.mycontact.user.usercontactmanagement;

public class PersonContact extends Contact {

    public PersonContact(String name, PhoneNumber phone, String email) {
        super(name, phone, email);
    }

    // Copy constructor (delegates to Contact.copy-ctor)
    public PersonContact(PersonContact other) {
        super(other);
    }

    @Override
    public String getContactType() {
        return "PERSON";
    }

    // Convenience "with" methods (produce modified copies)
    public PersonContact withName(String newName) {
        PersonContact copy = new PersonContact(this);
        copy.setName(newName);                 // uses validation
        return copy;
    }

    public PersonContact withPhone(String newPhoneDigits) {
        PersonContact copy = new PersonContact(this);
        copy.setPhone(new PhoneNumber(newPhoneDigits));   // validates 10 digits
        return copy;
    }

    public PersonContact withEmail(String newEmail) {
        PersonContact copy = new PersonContact(this);
        copy.setEmail(newEmail);               // uses EmailValidator and blank handling
        return copy;
    }
}