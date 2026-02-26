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

    public String getName() {
    	return name;
    }

    public String getEmail() {
    	return email; 
    }

    public String getHashedPassword() { 
    	return hashedPassword;
    }
    
    public abstract String getUserType();
}
