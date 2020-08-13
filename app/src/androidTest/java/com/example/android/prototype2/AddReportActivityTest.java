package com.example.android.prototype2;


import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AddReportActivityTest {
    @Rule
    public IntentsTestRule<AddReportActivity> mAddReportTestRule
            = new IntentsTestRule<>(AddReportActivity.class);


    @Test
    public void write_in_edit_text_test() {
        onView(withId(R.id.report_edit_text)).perform(typeText("Test"));
    }
    @Test
    public void report_continue_button_opens_correct_activity() {

        //Find the view and perform action
        onView(withId(R.id.report_continue)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(CoachAdviceActivity.class.getName()));

    }

    @Test
    public void report_call_Ambulance_Button_launches_Dialog() {

        //Find the view and perform the action
        onView(withId(R.id.report_call_ambulance)).perform(click());
        //Check if action returns desired outcome
        onView(withText("CALL AMBULANCE")).check(matches(isDisplayed()));

    }

}

