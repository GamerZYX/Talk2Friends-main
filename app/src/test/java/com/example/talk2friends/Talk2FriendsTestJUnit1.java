package com.example.talk2friends;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class Talk2FriendsTestJUnit1 {
    private User user;

    @Before
    public void setup() {
        ArrayList<String> test = new ArrayList<>();
        test.add("test");
        user = new User("test", "test", "test@usc.edu", "test", "test", test);
    }

    @Test
    public void testGetUsername_Correct() {
        assertEquals("test", user.getUsername());
    }

    @Test
    public void testGetUsername_Incorrect() {
        assertNotEquals("username", user.getUsername());
    }

    @Test
    public void testGetName_Correct() {
        assertEquals("test", user.getName());
    }

    @Test
    public void testGetName_Incorrect() {
        assertNotEquals("name", user.getName());
    }

    @Test
    public void testGetEmail_Correct() {
        assertEquals("test@usc.edu", user.getEmail());
    }

    @Test
    public void testGetEmail_Incorrect() {
        assertNotEquals("email", user.getEmail());
    }

    @Test
    public void testGetRegion_Correct() {
        assertEquals("test", user.getRegion());
    }

    @Test
    public void testGetRegion_Incorrect() {
        assertNotEquals("region", user.getRegion());
    }

    @Test
    public void testGetPassword_Correct() {
        assertEquals("test", user.getPassword());
    }

    @Test
    public void testGetPassword_Incorrect() {
        assertNotEquals("password", user.getPassword());
    }
}
