package com.example.tripservicekata.trip;

import com.example.tripservicekata.exception.UserNotLoggedInException;
import com.example.tripservicekata.user.User;
import com.example.tripservicekata.user.UserSession;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TripServiceTest {
    private static final User GUEST = null;
    private static final User REGISTERED = new User();
    private static final User A_FRIEND = new User();
    private static final User ANOTHER_FRIEND = new User();
    private static final Trip TO_SEOUL = new Trip();
    private static final Trip TO_TOKYO = new Trip();

    @Test
    void when_not_logged_in_should_throw_exception() {
        // given
        UserSession session = mockUserSession(GUEST);

        // when, then
        TripService tripService = new TripService(session);
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(new User()));
    }

    @Test
    void when_the_users_are_not_friends_should_not_return_userTrips() {
        // given
        User loggedUser = REGISTERED;
        UserSession session = mockUserSession(loggedUser);

        User user = new User();
        user.addFriend(A_FRIEND);
        user.addFriend(ANOTHER_FRIEND);
        user.addTrip(TO_SEOUL);
        user.addTrip(TO_TOKYO);

        // when, then
        TripService tripService = new TripService(session);
        assertEquals(0, tripService.getTripsByUser(user).size());
    }

    @Test
    void if_the_loggedUser_is_a_friend_of_the_user_return_userTrips() {
        // given
        User loggedUser = REGISTERED;
        UserSession session = mockUserSession(loggedUser);

        User user = new User();
        user.addFriend(A_FRIEND);
        user.addFriend(ANOTHER_FRIEND);
        user.addFriend(loggedUser);
        user.addTrip(TO_SEOUL);
        user.addTrip(TO_TOKYO);

        // when, then
        TripService tripService = new TripServiceForTest(session);
        assertEquals(2, tripService.getTripsByUser(user).size());
    }

    private UserSession mockUserSession(User of) {
        UserSession session = mock(UserSession.class);
        when(session.getLoggedUser()).thenReturn(of);
        return session;
    }

    private static class TripServiceForTest extends TripService {

        private TripServiceForTest(UserSession userSession) {
            super(userSession);
        }

        @Override
        protected List<Trip> retrieveTripsBy(User user) {
            return user.trips();
        }
    }
}
