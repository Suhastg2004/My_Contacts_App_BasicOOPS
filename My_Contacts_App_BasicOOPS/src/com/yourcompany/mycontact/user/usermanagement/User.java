package com.yourcompany.mycontact.user.usermanagement;

public abstract class User {

    private String name;
    private String email;
    private String hashedPassword;

    protected User(String name, String email, String hashedPassword) {
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getHashedPassword() { return hashedPassword; }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be empty");
        this.name = name;
    }

    public void setEmail(String email) {
        if (!EmailValidator.isValid(email))
            throw new IllegalArgumentException("Invalid email format");
        this.email = email;
    }

    public void changePassword(String newPassword) {
        if (!PasswordValidator.isValid(newPassword))
            throw new IllegalArgumentException("Password too weak");

        this.hashedPassword = PasswordHasher.hash(newPassword);
    }


    public abstract String getUserType();
}
