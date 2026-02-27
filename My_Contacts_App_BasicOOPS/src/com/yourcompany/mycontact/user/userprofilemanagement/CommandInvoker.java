package com.yourcompany.mycontact.user.userprofilemanagement;

public class CommandInvoker {

    public void executeCommand(ProfileCommand command) {
        command.execute();
    }
}
