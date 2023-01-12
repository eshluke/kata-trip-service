package com.example.tripservicekata.trip;

import com.example.tripservicekata.exception.UserNotLoggedInException;
import com.example.tripservicekata.user.User;
import com.example.tripservicekata.user.UserSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TripServiceTest {
    @Test
    void if_the_loggedUser_is_null_throw_exception() {
        // given
        UserSession session = mockUserSession(null);

        // when, then
        TripService tripService = new TripService(session);
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(new User()));
    }

    @Test
    void if_the_user_has_no_friend_return_empty_userTrips() {
        // given
        User user = new User();
        User loggedUser = new User();
        UserSession session = mockUserSession(loggedUser);

        // when, then
        TripService tripService = new TripService(session);
        assertEquals(0, tripService.getTripsByUser(user).size());
    }

    @Test
    void if_the_user_and_the_loggedUser_are_not_friends_return_empty_userTrips() {
        // given
        User user = new User();
        user.addFriend(new User());
        user.addFriend(new User());
        User loggedUser = new User();
        UserSession session = mockUserSession(loggedUser);

        // when, then
        TripService tripService = new TripService(session);
        assertEquals(0, tripService.getTripsByUser(user).size());
    }

    @Test
    void if_the_user_has_the_loggedUser_as_a_friend_return_userTrips() {
        // given
        User user = new User();
        User loggedUser = new User();
        user.addFriend(new User());
        user.addFriend(loggedUser);
        List<Trip> userTrips = Arrays.asList(new Trip(), new Trip());
        UserSession session = mockUserSession(loggedUser);

        try (MockedStatic<TripDAO> tripDao = Mockito.mockStatic(TripDAO.class)) {
            tripDao.when(() -> TripDAO.findTripsByUser(user)).thenReturn(userTrips);

            // when, then
            TripService tripService = new TripService(session);
            assertEquals(userTrips.size(), tripService.getTripsByUser(user).size());
        }
    }

    @Test
    void if_the_loggedUser_has_the_user_as_a_friend_but_not_vice_versa_return_empty_userTrips() {
        // given
        User user = new User();
        user.addFriend(new User());
        User loggedUser = new User();
        loggedUser.addFriend(user);
        List<Trip> userTrips = Arrays.asList(new Trip(), new Trip());
        UserSession session = mockUserSession(loggedUser);

        try (MockedStatic<TripDAO> tripDao = Mockito.mockStatic(TripDAO.class)) {
            tripDao.when(() -> TripDAO.findTripsByUser(user)).thenReturn(userTrips);

            // when, then
            TripService tripService = new TripService(session);
            assertEquals(0, tripService.getTripsByUser(user).size());
        }
    }

    private UserSession mockUserSession(User of) {
        UserSession session = mock(UserSession.class);
        when(session.getLoggedUser()).thenReturn(of);
        return session;
    }
}
