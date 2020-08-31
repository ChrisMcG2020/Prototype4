package com.example.android.prototype2;


import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.prototype2.views.AddReportActivity;
import com.example.android.prototype2.views.CoachAdviceActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AddReportActivityTest {
    //Set the rule to apply to the test method and which class to use
    @Rule
    public final IntentsTestRule<AddReportActivity> mAddReportTestRule
            = new IntentsTestRule<>(AddReportActivity.class);

    //Unit Tests
    @Test
    public void test_editText_and_buttons_present() {
        //Get the activity
        Activity activity = mAddReportTestRule.getActivity();
        //Find the views and assert they are present
        EditText report = activity.findViewById(R.id.report_edit_text);
        Assert.assertNotNull(report);
        Button ambulance = activity.findViewById(R.id.report_call_ambulance);
        Assert.assertNotNull(ambulance);
        Button continueB = activity.findViewById(R.id.report_continue);
        Assert.assertNotNull(continueB);

    }

    //UI & Instrumentation Tests
    @Test
    public void test_write_in_edit_text_test() {
        //Find the views and perform action
        onView(withId(R.id.report_edit_text)).perform(typeText("Test"));
    }

    @Test
    public void test_report_continue_button_opens_correct_activity() {
        //Find the views and perform action
        onView(withId(R.id.report_continue)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(CoachAdviceActivity.class.getName()));

    }

    @Test
    public void test_report_call_Ambulance_Button_launches_Dialog() {
        //Find the views and perform the action
        onView(withId(R.id.report_call_ambulance)).perform(click());
        //Check if action returns desired outcome
        onView(withText("CALL AMBULANCE?")).check(matches(isDisplayed()));

    }
}

