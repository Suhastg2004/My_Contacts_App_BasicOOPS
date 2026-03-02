package com.yourcompany.mycontact.user.usercontactmanagement;

import com.yourcompany.mycontact.user.usermanagement.EmailValidator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class Contact {

    private final UUID id;
    private String name;
    private PhoneNumber phone;
    private String email;
    private final LocalDateTime createdAt;

    private boolean deleted;
    private LocalDateTime deletedAt;

    private final Set<String> tags = new HashSet<>();

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
        if (other == null) throw new IllegalArgumentException("Other contact cannot be null");
        this.id = other.id;
        this.createdAt = other.createdAt;
        this.name = other.name;
        this.phone = new PhoneNumber(other.phone.getNumber());
        this.email = other.email;
        this.deleted = other.deleted;
        this.deletedAt = other.deletedAt;
        this.tags.addAll(other.tags);
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public PhoneNumber getPhone() { return phone; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isDeleted() { return deleted; }
    public LocalDateTime getDeletedAt() { return deletedAt; }

    public Set<String> getTags() { return Collections.unmodifiableSet(tags); }
    public void addTag(String tag) {
        if (tag != null && !tag.isBlank()) tags.add(tag.trim().toLowerCase());
    }
    public void removeTag(String tag) {
        if (tag != null) tags.remove(tag.trim().toLowerCase());
    }
    
    public void addTags(Collection<String> labels) {
        if (labels == null) return;
        for (String lbl : labels) addTag(lbl);
    }

    public void removeTags(Collection<String> labels) {
        if (labels == null) return;
        for (String lbl : labels) removeTag(lbl);
    }

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
                "Tags     : %s%n" +
                "Created  : %s%n" +
                "Deleted  : %s%n" +
                "DeletedAt: %s%n",
                getContactType(),
                id,
                name,
                phone.getNumber(),
                email == null ? "N/A" : email,
                tags.isEmpty() ? "—" : String.join(",", tags),
                createdAt,
                deleted ? "YES" : "NO",
                deletedAt == null ? "—" : deletedAt
        );
    }
}