package com.example.android.prototype2;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.android.prototype2.views.SplashScreen;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class LoginDialogTest {
    // Set the rule to apply to the test method and which class to use
    @Rule
    public IntentsTestRule<SplashScreen> mLoginDialogTest
            = new IntentsTestRule<>(SplashScreen.class);


    @Test
    public void test_dialog_appears_with_buttons() {
        //The choice dialog appears
        onView(withText("Coach"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
        onView(withText("Player"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_player_option_launches_player_login() {
        //Find the view and perform the action
        onView(withText("Player")).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(PlayerLoginActivity.class.getName()));
    }

    @Test
    public void test_coach_option_launches_coach_login() {
        //Find the view and perform the action
        onView(withText("Coach")).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(CoachLoginActivity.class.getName()));
    }
}
