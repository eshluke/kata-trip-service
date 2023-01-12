package com.example.tripservicekata.trip;

import com.example.tripservicekata.exception.UserNotLoggedInException;
import com.example.tripservicekata.user.User;
import com.example.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

	private UserSession userSession;
	private TripDAO tripDAO;

	public TripService(UserSession userSession, TripDAO tripDAO) {
		this.userSession = userSession;
		this.tripDAO = tripDAO;
	}

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		checkLoggedInStatus();
		if (user.hasFriend(getLoggedUser())) {
			return retrieveTripsBy(user);
		}
		return new ArrayList<>();
	}

	private User getLoggedUser() {
		return this.userSession.getLoggedUser();
	}

	private void checkLoggedInStatus() {
		if (getLoggedUser() == null) {
			throw new UserNotLoggedInException();
		}
	}

	protected List<Trip> retrieveTripsBy(User user) {
		return this.tripDAO.findTripsBy(user);
	}
}
