	package com.yourcompany.mycontact.user.usermanagement;
	
	public class FreeUser extends User {
	
	    public FreeUser(String name, String email, String hashedPassword) {
	        super(name, email, hashedPassword);
	    }
	
	    @Override
	    public String getUserType() {
	        return "FREE";
	    }
	}
