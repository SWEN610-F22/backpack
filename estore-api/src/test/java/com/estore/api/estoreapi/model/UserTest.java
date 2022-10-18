package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class UserTest {

    @Test
    void createUser() {
        User user = new User(1, "Regina", true);
        assertEquals(1, user.getId());
        assertEquals("Regina", user.getUsername());
        ;
    }

    @Test
    void equals() {

        User user1 = new User(2, "Vidit", true);
        User user2 = new User(2, "Vidit", true);

        assertTrue(user2.getId() == user1.getId());
        assertTrue(user2.getUsername() == user1.getUsername());
        assertTrue(user2.getAdmin() == user1.getAdmin());

    }

    @Test
    public String toString() {

        User user1 = new User(2, "Vidit", true);
        User user2 = new User(2, "Vidit", true);

        assertEquals(user2.toString(), user1.toString());

        assertTrue(user2.toString() == user1.toString());

        return null;

    }

}
