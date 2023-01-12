package com.example.tripservicekata.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    void when_the_user_does_not_have_friend_cathy() {
        User user = new User();
        User ben = new User();
        User cathy = new User();

        user.addFriend(ben);

        assertFalse(user.hasFriend(cathy));
    }

    @Test
    void when_cathy_has_the_user_as_friend_but_not_vice_versa() {
        User user = new User();
        User ben = new User();
        User cathy = new User();

        user.addFriend(ben);
        cathy.addFriend(user);

        assertFalse(user.hasFriend(cathy));
    }

    @Test
    void when_the_user_have_friend_cathy() {
        User user = new User();
        User ben = new User();
        User cathy = new User();

        user.addFriend(ben);
        user.addFriend(cathy);

        assertTrue(user.hasFriend(cathy));
    }
}
