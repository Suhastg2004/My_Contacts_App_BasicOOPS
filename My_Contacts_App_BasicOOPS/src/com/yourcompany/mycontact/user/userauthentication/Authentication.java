package com.yourcompany.mycontact.user.userauthentication;

import java.util.Optional;

//import the files from the UC1 package
import com.yourcompany.mycontact.user.usermanagement.*;

public interface Authentication {
    Optional<User> authenticate(String identifier, String password);
}