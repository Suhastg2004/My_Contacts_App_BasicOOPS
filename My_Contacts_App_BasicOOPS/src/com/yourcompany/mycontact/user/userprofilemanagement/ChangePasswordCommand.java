package com.yourcompany.mycontact.user.userprofilemanagement;

import com.yourcompany.mycontact.user.usermanagement.*;
public class ChangePasswordCommand implements ProfileCommand {

    private User user;
    private String newPassword;

    public ChangePasswordCommand(User user, String newPassword) {
        this.user = user;
        this.newPassword = newPassword;
    }

    @Override
    public void execute() {
        user.changePassword(newPassword);
    }
}
