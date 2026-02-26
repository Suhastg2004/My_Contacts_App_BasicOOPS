package com.yourcompany.mycontact.user.usermanagement;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final List<User> users = new ArrayList<>();
    
    public User register(String name, String email, String password, String type) throws Exception {

        if (!EmailValidator.isValid(email)) {
            throw new Exception("Invalid email format");
        }

        if (!PasswordValidator.isValid(password)) {
            throw new Exception("Password too short");
        }

        String hashed = PasswordHasher.hash(password);

        User user = new FreeUser(name, email, hashed);

        users.add(user);
        return user;
    }

    public List<User> getUsers() {
        return users;
    }
}