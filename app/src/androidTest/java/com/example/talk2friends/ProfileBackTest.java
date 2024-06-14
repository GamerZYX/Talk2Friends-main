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
public class ProfileBackTest {

    @Rule
    public IntentsTestRule<ProfileActivity> intentsTestRule =
            new IntentsTestRule<>(ProfileActivity.class, true, false);

    @Test
    public void backButton_click_returnsToMainPageActivity() {
        // Create an Intent to start ProfileActivity with necessary extra data
        Intent intent = new Intent();
        intent.putExtra("username", "zyx"); // Replace with a valid username

        intentsTestRule.launchActivity(intent);

        // Click the back button
        onView(withId(R.id.button)).perform(click());

        // Check if MainPageActivity is launched
        intended(hasComponent(MainPageActivity.class.getName()));

    }
}

