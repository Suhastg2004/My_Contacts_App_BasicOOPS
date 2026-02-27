package com.yourcompany.mycontact.user.usercontactmanagement;

import com.yourcompany.mycontact.user.usermanagement.*;

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
    
    // Copy constructor ( used for modified copies )
    protected Contact(Contact other) {
        this.id = other.id;                                
        this.createdAt = other.createdAt;                     
        this.name = other.name;                               
        this.phone = new PhoneNumber(other.phone.getNumber()); 
        this.email = other.email;                             
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public PhoneNumber getPhone() { return phone; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    //Setters
     public void setName(String name) {
         if (name == null || name.isBlank())
             throw new IllegalArgumentException("Contact name cannot be empty");
         this.name = name.trim();
     }

     public void setEmail(String email) {
         // Treat blank as "no email"
         if (email == null || email.isBlank()) {
             this.email = null;
             return;
         }
         if (!EmailValidator.isValid(email))
             throw new IllegalArgumentException("Invalid contact email");
         this.email = email.trim();
     	}

     	public void setPhone(PhoneNumber phone) {
         if (phone == null)
             throw new IllegalArgumentException("Phone cannot be null");
         // Defensive copy (deep copy) in case PhoneNumber is extended later
         this.phone = new PhoneNumber(phone.getNumber());
     	}

    public abstract String getContactType();
    
    @Override
    public String toString() {
        return String.format(
                "=== Contact Details ===\n" +
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