package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.context.TestPropertySource;

public class UserFileDAOTest {
    UserFileDAO userFileDAO;
    User[] testUsers;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupUserFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[3];
        
        testUsers[0] = new User(1, "zac", false);
        testUsers[1] = new User(2, "vin", false);
        testUsers[2] = new User(3, "merg", false);

        when(mockObjectMapper
                .readValue(new File("users.json"), User[].class))
                .thenReturn(testUsers);
        userFileDAO = new UserFileDAO("users.json", mockObjectMapper);
    }

    @Test
    void getUsers() {
        User[] users = userFileDAO.getUsers();
        for (int i = 0; i < testUsers.length; i++) {
            assertEquals(testUsers[i], users[i]);
        }
    }

    @Test
    public void findUsers() {
        User[] users = userFileDAO.findUsers("zac");
        assertEquals(1, users.length);
    }

    @Test
    public void findUserWithoutText() {
        User[] users = userFileDAO.findUsers(null);
        assertEquals(testUsers.length, users.length);
    }

    @Test
    void updateUser() {
        User user = new User(1, "zac", false);
        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(user), "Unexpected exception thrown");
        assertNotNull(result);
        User realUser = userFileDAO.getUser(user.getId());
        assertEquals(realUser, user);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class), any(User[].class));
        User user = new User(1, "zac", false);
        assertThrows(IOException.class, () -> userFileDAO.createUser(user), "IOException not thrown");
    }

    @Test
    void getUser() {
        User user = userFileDAO.getUser(1);
        assertEquals(user, testUsers[0]);
    }

    @Test
    public void testCreateUser() {
        // Setup
        User user = new User(1, "zac", false);

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.createUser(user),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        User actual = userFileDAO.getUser(user.getId());
        assertEquals(actual.getId(), user.getId());

    }

    @Test
    public void testDeleteUser() {
        // new user
        User user = new User(1, "zac", false);

        // user currently in system with same id
        User realUser = userFileDAO.getUser(user.getId());

        // delete user
        Boolean result = assertDoesNotThrow(() -> userFileDAO.deleteUser(realUser.getId()),
                "Unexpected exception thrown");

        // check if its there
        assertNotNull(result);

        // delete user again
        Boolean result2 = assertDoesNotThrow(() -> userFileDAO.deleteUser(realUser.getId()),
                "Unexpected exception thrown");

        assertEquals(result, true);
        assertEquals(result2, false);

    }

}
