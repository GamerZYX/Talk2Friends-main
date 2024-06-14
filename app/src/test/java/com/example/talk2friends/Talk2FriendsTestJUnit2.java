package com.example.talk2friends;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class Talk2FriendsTestJUnit2 {
    private Meeting meeting;

    @Before
    public void setup() {
        meeting = new Meeting("test", "test", "test", "test", "test");
    }

    @Test
    public void testMeetingGetName_Correct() {
        assertEquals("test", meeting.getName());
    }

    @Test
    public void testMeetingGetUsername_Incorrect() {
        assertNotEquals("name", meeting.getName());
    }

    @Test
    public void testMeetingGetTopic_Correct() {
        assertEquals("test", meeting.getTopic());
    }

    @Test
    public void testMeetingGetTopic_Incorrect() {
        assertNotEquals("topic", meeting.getTopic());
    }

    @Test
    public void testMeetingGetTime_Correct() {
        assertEquals("test", meeting.getTime());
    }

    @Test
    public void testMeetingGetTime_Incorrect() {
        assertNotEquals("time", meeting.getTime());
    }

    @Test
    public void testMeetingGetRegion_Correct() {
        assertEquals("test", meeting.getRegion());
    }

    @Test
    public void testMeetingGetRegion_Incorrect() {
        assertNotEquals("region", meeting.getRegion());
    }

    @Test
    public void testMeetingGetLocation_Correct() {
        assertEquals("test", meeting.getLocation());
    }

    @Test
    public void testMeetingGetLocation_Incorrect() {
        assertNotEquals("location", meeting.getLocation());
    }
}
