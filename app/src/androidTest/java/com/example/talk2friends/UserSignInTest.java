package com.example.talk2friends;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserSignInTest {

    private FirebaseIdlingResource firebaseIdlingResource;

    @Rule
    public IntentsTestRule<SignInActivity> intentsTestRule =
            new IntentsTestRule<>(SignInActivity.class);

    @Before
    public void setUp() {
        firebaseIdlingResource = new FirebaseIdlingResource();
        // The IdlingRegistry takes care of registering and unregistering resources
        IdlingRegistry.getInstance().register(firebaseIdlingResource);
    }

    @Test
    public void signIn_success_opensMainPageActivity() {
        // Input username and password
        onView(withId(R.id.usernameSignInField)).perform(typeText("zyx"));
        onView(withId(R.id.passwordSignInField)).perform(typeText("123"));

        // Click the sign-in button
        onView(withId(R.id.signInButton)).perform(click());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(firebaseIdlingResource);
    }
}


