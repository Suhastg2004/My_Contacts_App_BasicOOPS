package com.yourcompany.mycontact.user.userprofilemanagement;

import com.yourcompany.mycontact.user.usermanagement.*;

public class UpdateEmailCommand implements ProfileCommand {

    private User user;
    private String newEmail;

    public UpdateEmailCommand(User user, String newEmail) {
        this.user = user;
        this.newEmail = newEmail;
    }

    @Override
    public void execute() {
        user.setEmail(newEmail);
    }
}
