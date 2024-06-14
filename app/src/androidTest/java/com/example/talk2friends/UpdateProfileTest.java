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
public class UpdateProfileTest {

    @Rule
    public IntentsTestRule<ProfileActivity> intentsTestRule =
            new IntentsTestRule<>(ProfileActivity.class, true, false);

    @Test
    public void updateButton_click_opensUpdateProfileActivity() {
        // Create an Intent to start ProfileActivity with necessary extra data
        Intent intent = new Intent();
        intent.putExtra("username", "zyx"); // Replace with a valid username
        // ... Add other necessary extra data

        // Launch Activity with the custom Intent
        intentsTestRule.launchActivity(intent);

        // Click the update profile button
        onView(withId(R.id.updateButton)).perform(click());

        // Check if UpdateProfileActivity is launched
        intended(hasComponent(UpdateProfileActivity.class.getName()));

        // More assertions can be added here, such as checking if certain UI elements are displayed in UpdateProfileActivity
    }
}


