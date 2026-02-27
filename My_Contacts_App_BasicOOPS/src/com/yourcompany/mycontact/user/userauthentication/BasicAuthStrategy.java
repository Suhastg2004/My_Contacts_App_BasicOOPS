package com.yourcompany.mycontact.user.userauthentication;

import com.yourcompany.mycontact.user.usermanagement.*;

import java.util.Optional;

public class BasicAuthStrategy implements Authentication {

    private UserService userService;

    public BasicAuthStrategy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        String hashed = PasswordHasher.hash(password);

        return userService.getUsers().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email)
                        && u.getHashedPassword().equals(hashed))
                .findFirst();
    }
}
