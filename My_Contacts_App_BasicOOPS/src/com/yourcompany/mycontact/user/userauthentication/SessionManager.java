package com.yourcompany.mycontact.user.userauthentication;

import com.yourcompany.mycontact.user.usermanagement.*;

public class SessionManager {

    private static SessionManager instance;
    private User loggedInUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null)
            instance = new SessionManager();
        return instance;
    }

    public void startSession(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void endSession() {
        loggedInUser = null;
    }
}
