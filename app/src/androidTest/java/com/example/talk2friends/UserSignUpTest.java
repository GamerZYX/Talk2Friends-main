package com.example.talk2friends;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserSignUpTest {

    @Rule
    public IntentsTestRule<SignUpActivity> intentsTestRule =
            new IntentsTestRule<>(SignUpActivity.class);

    @Test
    public void signUp_success_opensMainPageActivity() throws InterruptedException {
        // Input user details
        onView(withId(R.id.usernameSignUpField)).perform(typeText("Tommy"), closeSoftKeyboard());
        onView(withId(R.id.nameSignUpField)).perform(typeText("Tommy"), closeSoftKeyboard());
        onView(withId(R.id.emailSignUpField)).perform(typeText("Tommy@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.passwordSignUpField)).perform(typeText("Tommy"), closeSoftKeyboard());
        onView(withId(R.id.interestSignUpField)).perform(typeText("football"), closeSoftKeyboard());
        onView(withId(R.id.regionSignUpField)).perform(typeText("native"), closeSoftKeyboard());

        // Click the sign-up button
        onView(withId(R.id.signUpButton)).perform(click());
    }
}


