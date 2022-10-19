package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class UserTest {
    User user = new User(2, "Dachu", true);

    @Test
    void createUser() {
        User user = new User(1, "Regina", true);
        assertEquals(1, user.getId());
        assertEquals("Regina", user.getUsername());
        assertEquals(true, user.getIsAdmin());
    }
    @Test
    void setUsername(){
        user.setUsername("Dachu");
        assertEquals("Dachu", user.getUsername());
    }
    @Test
    void setAdmin(){
        user.setAdmin(true);
        assertEquals(true, user.getIsAdmin());
    }
  
    
}
