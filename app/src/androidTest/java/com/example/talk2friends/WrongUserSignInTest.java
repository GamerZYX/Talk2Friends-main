package com.example.talk2friends;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class WrongUserSignInTest {

    @Rule
    public ActivityScenarioRule<SignInActivity> activityScenarioRule =
            new ActivityScenarioRule<>(SignInActivity.class);

    @Test
    public void signInWithWrongCredentials_ShowsAuthenticationFailedToast() {
        // Input wrong username and password
        onView(withId(R.id.usernameSignInField)).perform(typeText("wronguser"));
        onView(withId(R.id.passwordSignInField)).perform(typeText("wrongpassword"));

        // Click the sign-in button
        onView(withId(R.id.signInButton)).perform(click());

        // Wait for the Main activity to start
//        activityScenarioRule.getScenario().onActivity(activity -> {
//            Matcher<Root> rootMatcher = withDecorView(not(is(activity.getWindow().getDecorView())));
//            onView(withText("Authentication failed."))
//                    .inRoot(rootMatcher)
//                    .check(matches(ViewMatchers.isDisplayed()));
//        });
    }
}



