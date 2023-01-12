package com.example.tripservicekata.trip;

import com.example.tripservicekata.exception.CollaboratorCallException;
import com.example.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TripDAOTest {

    @Test
    void when_retrieving_trips_should_throw_exception() {
        User user = new User();
        assertThrows(CollaboratorCallException.class,
                () -> new TripDAO().findTripsBy(user));
    }
}