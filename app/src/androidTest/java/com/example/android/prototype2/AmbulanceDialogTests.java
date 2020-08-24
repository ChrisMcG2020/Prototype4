package com.example.android.prototype2;

import android.content.Intent;
import android.net.Uri;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.android.prototype2.views.RedFlagActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class AmbulanceDialogTests {
    //Set the rule to apply to the test method and which class to use
    @Rule
    public IntentsTestRule<RedFlagActivity> mAmbulanceTestRule
            = new IntentsTestRule<>(RedFlagActivity.class);

    @Test
    public void test_dialog_appears_with_buttons() {
        //Find the views and perform the action
        onView(withId(R.id.redFlag_call_ambulance)).perform(click());
        //The delete dialog appears with two options
        onView(withText("CALL AMBULANCE?"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
        onView(withText("Cancel"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

    }

    @Test
    public void test_call_Ambulance_Button_launches_Dialog() {
        //Find the view and perform the action
        onView(withId(R.id.redFlag_call_ambulance)).perform(click());
        //Check if action returns desired outcome
        onView(withText("CALL AMBULANCE?")).check(matches(isDisplayed()));

    }

    @Test
    public void test_clickCallButton_opensDialler_forAmbulance() {
        //Find the views and perform the action
        onView(withId(R.id.redFlag_call_ambulance)).perform(click());
        onView(withText("CALL")).perform(click());
        //Check if action returns desired outcome
        intended(allOf(
                hasAction(Intent.ACTION_DIAL)));
        //Intent has the data ("999")
        hasData(Uri.parse("tel:+999"));
    }


    @Test
    public void test_onCancel() {
        //Find the views and perform the action
        onView(withId(R.id.redFlag_call_ambulance)).perform(click());
        onView(withText("CANCEL")).perform(click());
        //Is the button viewable again
        Assert.assertNotNull(RedFlagActivity.class);

    }
}