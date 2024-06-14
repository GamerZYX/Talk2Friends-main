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
public class MeetingNameTest {

    @Rule
    public IntentsTestRule<ViewMeetingsActivity> intentsTestRule =
            new IntentsTestRule<>(ViewMeetingsActivity.class, true, false);

    @Test
    public void createMeetingButton_click_opensCreateMeetingActivity() {
        // Create an Intent to start ViewMeetingsActivity with necessary extra data
        Intent intent = new Intent();
        intent.putExtra("username", "zyx"); // Replace with a valid username

        // Launch Activity with the custom Intent
        intentsTestRule.launchActivity(intent);

        // Click the create meeting button
        onView(withId(R.id.createMeetingButton)).perform(click());

        // Check if CreateMeetingActivity is launched
        intended(hasComponent(CreateMeetingActivity.class.getName()));
    }
}

