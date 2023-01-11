package com.example.tripservicekata.user;

import com.example.tripservicekata.exception.CollaboratorCallException;

public class UserSession {

	public User getLoggedUser() {
		throw new CollaboratorCallException(
				"UserSession.getLoggedUser() should not be called in an unit test");
	}

}
