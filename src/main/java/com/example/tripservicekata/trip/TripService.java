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
		boolean isFriend = false;
		if (loggedUser != null) {
			for (User friend : user.getFriends()) {
				if (friend.equals(loggedUser)) {
					isFriend = true;
					break;
				}
			}
			if (isFriend) {
				return TripDAO.findTripsByUser(user);
			}
			return new ArrayList<>();
		} else {
			throw new UserNotLoggedInException();
		}
	}
	
}
