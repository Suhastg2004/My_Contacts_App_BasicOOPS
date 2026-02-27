package com.yourcompany.mycontact.user.usercontactmanagement;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Contact {

    private UUID id;
    private String name;
    private PhoneNumber phone;
    private String email;
    private LocalDateTime createdAt;

    public Contact(String name, PhoneNumber phone, String email) {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public PhoneNumber getPhone() { return phone; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public abstract String getContactType();
    

    @Override
    public String toString() {
        return String.format(
                "=== Contact Details ===\n" +
                "Type     : %s\n" +
                "Name     : %s\n" +
                "Phone    : %s\n" +
                "Email    : %s\n" +
                "Created  : %s\n",
                id,
                name,
                phone,
                email,
                createdAt
        );

    }
}