package com.example.android.prototype2;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.android.prototype2.views.SplashScreen;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class DeleteDialogTest {
    //Set the rule to apply to the test method and which class to use
    @Rule
    public IntentsTestRule<PlayerProfile> mDeleteProfileTestRule
            = new IntentsTestRule<>(PlayerProfile.class);


    @Test
    public void test_dialog_appears_with_buttons() {
        //Find the view and perform the action
        onView(withId(R.id.btn_deleteProfile)).perform(scrollTo()).perform(click());
        //The delete dialog appears with two options
        onView(withText("Delete Profile"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
        onView(withText("Cancel"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }


    @Test
    public void test_delete_profile() {
        //Find the view and perform the action
        onView(withId(R.id.btn_deleteProfile)).perform(scrollTo()).perform(click());
        //Perform the action
        onView(withText("Delete")).perform(click());
        //Has expected result
        intended(hasComponent(SplashScreen.class.getName()));
    }

    @Test
    public void test_cancel_delete_profile() {
        //Find the view and perform the action
        onView(withId(R.id.btn_deleteProfile)).perform(scrollTo()).perform(click());
        //The delete dialog appears, perform the action
        onView(withText("Cancel")).inRoot(isDialog()).perform(click());
        //The delete dialog appears (UserProfile screen with delete button present is returned
        onView(withId(R.id.btn_deleteProfile)).check(matches(isDisplayed()));

    }
}
