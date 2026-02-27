package com.yourcompany.mycontact.user.usercontactmanagement;

import com.yourcompany.mycontact.user.usermanagement.EmailValidator;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Contact {

    private UUID id;
    private String name;
    private PhoneNumber phone;
    private String email;
    private LocalDateTime createdAt;

    private boolean deleted;
    private LocalDateTime deletedAt;

    public Contact(String name, PhoneNumber phone, String email) {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        setName(name);
        setPhone(phone);
        setEmail(email);
        this.deleted = false;
        this.deletedAt = null;
    }

    protected Contact(Contact other) {
        this.id = other.id;
        this.createdAt = other.createdAt;
        this.name = other.name;
        this.phone = new PhoneNumber(other.phone.getNumber());
        this.email = other.email;
        this.deleted = other.deleted;
        this.deletedAt = other.deletedAt;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public PhoneNumber getPhone() { return phone; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public boolean isDeleted() { return deleted; }
    public LocalDateTime getDeletedAt() { return deletedAt; }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Contact name cannot be empty");
        this.name = name.trim();
    }

    public void setEmail(String email) {
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
        this.phone = new PhoneNumber(phone.getNumber());
    }

    public void markDeleted() {
        if (!deleted) {
            this.deleted = true;
            this.deletedAt = LocalDateTime.now();
        }
    }

    public void restore() {
        if (deleted) {
            this.deleted = false;
            this.deletedAt = null;
        }
    }

    public abstract String getContactType();

    @Override
    public String toString() {
        return String.format(
                "=== Contact Details ===%n" +
                "Type     : %s%n" +
                "ID       : %s%n" +
                "Name     : %s%n" +
                "Phone    : %s%n" +
                "Email    : %s%n" +
                "Created  : %s%n" +
                "Deleted  : %s%n" +
                "DeletedAt: %s%n",
                getContactType(),
                id,
                name,
                phone.getNumber(),
                email == null ? "N/A" : email,
                createdAt,
                deleted ? "YES" : "NO",
                deletedAt == null ? "—" : deletedAt.toString()
        );
    }
}