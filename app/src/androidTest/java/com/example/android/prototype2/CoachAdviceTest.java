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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class CoachAdviceTest {
    //Set the rule to apply to the test method and which class to use
    @Rule
    public IntentsTestRule<CoachAdviceActivity> mCoachAdviceTestRule
            = new IntentsTestRule<>(CoachAdviceActivity.class);

    @Test
    public void test_advice_screen() {
        //Get the activity
        Activity activity = mCoachAdviceTestRule.getActivity();
        //Find the views and assert they are present
        TextView advice = activity.findViewById(R.id.advice_text_view);
        Assert.assertNotNull(advice);
        TextView warning = activity.findViewById(R.id.text_view_warning);
        Assert.assertNotNull(warning);

        //Find the views
        Button ambulance = activity.findViewById(R.id.coach_advice_Call_ambulance_btn);
        Button continueB = activity.findViewById(R.id.coach_advice_continue_btn);
        //Assert they are present
        Assert.assertNotNull(ambulance);
        Assert.assertNotNull(continueB);
    }

    @Test
    public void continue_button_opens_correct_activity() {
        //Find the views and perform action
        onView(withId(R.id.coach_advice_continue_btn)).perform(scrollTo()).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(CoachProfile.class.getName()));
    }

    @Test
    public void call_Ambulance_Button_launches_Activity() {
        //Find the views and perform the action
        onView(withId(R.id.coach_advice_Call_ambulance_btn)).perform(scrollTo()).perform(click());
        //Check if action returns desired outcome
        onView(withText("CALL AMBULANCE?")).check(matches(isDisplayed()));

    }
}
