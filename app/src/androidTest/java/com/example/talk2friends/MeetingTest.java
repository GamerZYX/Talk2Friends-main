package com.example.talk2friends;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MeetingTest {

    @Rule
    public IntentsTestRule<MainPageActivity> intentsTestRule =
            new IntentsTestRule<>(MainPageActivity.class, true, false);

    @Test
    public void meetingButton_click_opensViewMeetingsActivity() {
        // Create an Intent to launch MainPageActivity with necessary extra data
        Intent intent = new Intent();
        intent.putExtra("user", "zyx"); // Example user data, adjust according to actual scenarios
        intent.putExtra("username", "zyx");
        // ... Add any other necessary extra data

        // Launch the Activity using the custom Intent
        intentsTestRule.launchActivity(intent);

        // Click on the meeting button
        onView(withId(R.id.meetingButton)).perform(click());

        // Check if ViewMeetingsActivity has been launched
        intended(hasComponent(ViewMeetingsActivity.class.getName()));

    }
}
