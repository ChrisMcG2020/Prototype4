package com.example.android.prototype2;

import android.app.Activity;
import android.widget.Button;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.prototype2.views.ObservableSignsActivity;
import com.example.android.prototype2.views.RedFlagActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RedFlagsTest {
    // Set the rule to apply to the test method and which class to use
    @Rule
    public IntentsTestRule<RedFlagActivity> mRed_Flags_Test_Rule
            = new IntentsTestRule<>(RedFlagActivity.class);

    @Test
    public void test_buttons_present() {
        //Get the activity
        Activity activity = mRed_Flags_Test_Rule.getActivity();
        //Find the views
        Button ambulance = activity.findViewById(R.id.redFlag_call_ambulance);
        Button continueB = activity.findViewById(R.id.redFlag_continue);
        //Assert they are present
        Assert.assertNotNull(ambulance);
        Assert.assertNotNull(continueB);


    }

    @Test
    public void test_rf_continue_button_opens_correct_activity() {
        //Find the views and perform action
        onView(withId(R.id.redFlag_continue)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(ObservableSignsActivity.class.getName()));
    }


    @Test
    public void test_call_Ambulance_Button_launches_Dialog() {
        //Find the views and perform the action
        onView(withId(R.id.redFlag_call_ambulance)).perform(click());
        //Check if action returns desired outcome
        onView(withText("CALL AMBULANCE?")).check(matches(isDisplayed()));

    }

}
