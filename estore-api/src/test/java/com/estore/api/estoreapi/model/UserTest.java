package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class UserTest {

    @Test
    void createUser() {
        User user = new User(1, "Regina");
        assertEquals(1, user.getId());
        assertEquals("Regina", user.getUsername());
        assertEquals(true, user.getIsAdmin());
    }
   
    
}
