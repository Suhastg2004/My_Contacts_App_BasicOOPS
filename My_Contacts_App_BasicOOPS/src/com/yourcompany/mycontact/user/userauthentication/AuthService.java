package com.yourcompany.mycontact.user.userauthentication;

import com.yourcompany.mycontact.user.usermanagement.*;

import java.util.Optional;

public class AuthService {

    private Authentication strategy;

    public AuthService(Authentication strategy) {
        this.strategy = strategy;
    }

    public Optional<User> login(String identifier, String password) {

        Optional<User> userOpt = strategy.authenticate(identifier, password);

        userOpt.ifPresent(u -> {
            SessionManager.getInstance().startSession(u);
        });

        return userOpt;
    }
}