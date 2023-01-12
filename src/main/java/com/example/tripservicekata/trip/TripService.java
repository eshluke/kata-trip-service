package com.example.tripservicekata.trip;

import com.example.tripservicekata.exception.UserNotLoggedInException;
import com.example.tripservicekata.user.User;

import java.util.ArrayList;
import java.util.List;

public class TripService {

	private final TripDAO tripDAO;

	public TripService(TripDAO tripDAO) {
		this.tripDAO = tripDAO;
	}

	public List<Trip> getTripsByUser(User user, User logInUser) throws UserNotLoggedInException {
		checkLoggedInStatus(logInUser);
		if (user.hasFriend(logInUser)) {
			return retrieveTripsBy(user);
		}
		return new ArrayList<>();
	}

	private void checkLoggedInStatus(User logInUser) {
		if (logInUser == null) {
			throw new UserNotLoggedInException();
		}
	}

	protected List<Trip> retrieveTripsBy(User user) {
		return this.tripDAO.findTripsBy(user);
	}
}
