package com.example.android.prototype2;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class RecoveryAdviceTest {

    // Set the rule to apply to the test method and which class to use
    @Rule
    public IntentsTestRule<RecoveryActivity> mActivityTestRule =
            new IntentsTestRule<>(RecoveryActivity.class);


    @Test
    public void test_views_buttons() {
        //Get the activity
        Activity activity = mActivityTestRule.getActivity();
        //Find the views and assert they are present
        TextView recovery_signs = activity.findViewById(R.id.recovery_signs);
        Assert.assertNotNull(recovery_signs);
        TextView recovery_cautions = activity.findViewById(R.id.recovery_caution);
        Assert.assertNotNull(recovery_cautions);
        Button returnButton = activity.findViewById(R.id.recovery_back_btn);
        Assert.assertNotNull(returnButton);
        Button sportStrategy = activity.findViewById(R.id.return_to_sport_btn);
        Assert.assertNotNull(sportStrategy);


    }

    @Test
    public void test_rts_Buttons_clickable() {
        //Find the views and perform action
        onView(withId(R.id.return_to_sport_btn)).perform(scrollTo()).perform(click());

    }

    @Test
    public void test_back_button_clickable() {
        onView(withId(R.id.recovery_back_btn)).perform(scrollTo()).perform(click());
    }

    @Test
    public void test_rts_button_launches_activity() {
        //Find the views and perform action
        onView(withId(R.id.return_to_sport_btn)).perform(scrollTo()).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(ReturnToSport.class.getName()));

    }

    @Test
    public void test_back_button_launches_activity() {
        //Find the views and perform action
        onView(withId(R.id.recovery_back_btn)).perform(scrollTo()).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(PlayerProfile.class.getName()));

    }
}