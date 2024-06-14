package com.example.talk2friends;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    // Using IntentsTestRule to initialize Espresso Intents and prepare to record Intent sending
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void signInButton_click_opensSignInActivity() {
        // Click the login button
        onView(withId(R.id.buttonLogIn)).perform(click());

        // Verify that SignInActivity is started
        intended(hasComponent(SignInActivity.class.getName()));
    }
}

