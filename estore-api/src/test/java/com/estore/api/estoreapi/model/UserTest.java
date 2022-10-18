package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class UserTest {

    @Test
    void createUser(){
        User user = new User(1, "Regina", true);
        assertEquals(1, user.getId());
        assertEquals("Regina", user.getUsername());
    }
   
    
}
