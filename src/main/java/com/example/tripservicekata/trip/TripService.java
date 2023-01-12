package com.example.tripservicekata.trip;

import com.example.tripservicekata.exception.UserNotLoggedInException;
import com.example.tripservicekata.user.User;
import com.example.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

	private UserSession userSession;

	public TripService(UserSession userSession) {
		this.userSession = userSession;
	}

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		User loggedUser = this.userSession.getLoggedUser();
		if (loggedUser == null) {
			throw new UserNotLoggedInException();
		}
		if (user.hasFriend(loggedUser)) {
			return TripDAO.findTripsByUser(user);
		}
		return new ArrayList<>();
	}
}
