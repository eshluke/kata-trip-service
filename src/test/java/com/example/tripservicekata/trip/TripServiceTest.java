package com.example.tripservicekata.trip;

import com.example.tripservicekata.exception.UserNotLoggedInException;
import com.example.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripServiceTest {
    private static final User GUEST = null;
    private static final User REGISTERED = new User();
    private static final User A_FRIEND = new User();
    private static final User ANOTHER_FRIEND = new User();
    private static final Trip TO_SEOUL = new Trip();
    private static final Trip TO_TOKYO = new Trip();

    private TripDAO tripDAO;

    @BeforeEach
    void setUp() {
        tripDAO = new FakeTripDAO();
    }

    @Test
    void when_not_logged_in_should_throw_exception() {
        // given
        User user = new User();

        // when, then
        TripService tripService = new TripService(tripDAO);
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(user, GUEST));
    }

    @Test
    void when_the_users_are_not_friends_should_not_return_userTrips() {
        // given
        User loggedUser = REGISTERED;

        User user = new User();
        user.addFriend(A_FRIEND);
        user.addFriend(ANOTHER_FRIEND);
        user.addTrip(TO_SEOUL);
        user.addTrip(TO_TOKYO);

        // when, then
        TripService tripService = new TripService(tripDAO);
        assertEquals(0, tripService.getTripsByUser(user, loggedUser).size());
    }

    @Test
    void when_the_loggedUser_is_a_friend_of_the_user_should_return_userTrips() {
        // given
        User loggedUser = REGISTERED;

        User user = new User();
        user.addFriend(A_FRIEND);
        user.addFriend(ANOTHER_FRIEND);
        user.addFriend(loggedUser);
        user.addTrip(TO_SEOUL);
        user.addTrip(TO_TOKYO);

        // when, then
        TripService tripService = new TripService(tripDAO);
        assertEquals(2, tripService.getTripsByUser(user, loggedUser).size());
    }

    private static class FakeTripDAO extends TripDAO {
        @Override
        public List<Trip> findTripsBy(User user) {
            return user.trips();
        }
    }
}
