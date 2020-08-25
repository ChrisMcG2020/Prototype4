package com.example.android.prototype2;

import android.app.Activity;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.prototype2.views.MemoryActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MemoryActivityTest {
    // Set the rule to apply to the test method and which class to use
    @Rule
    public final IntentsTestRule<MemoryActivity> mMemoryTestRule
            = new IntentsTestRule<>(MemoryActivity.class);

    @Test
    public void test_editText_and_buttons_present() {
        //Find the views
        Activity activity = mMemoryTestRule.getActivity();
        CheckBox checkBox = activity.findViewById(R.id.memory_q1_yes);
        CheckBox checkBox2 = activity.findViewById(R.id.memory_q1_no);
        CheckBox checkBox3 = activity.findViewById(R.id.memory_q2_yes);
        CheckBox checkBox4 = activity.findViewById(R.id.memory_q2_no);
        CheckBox checkBox5 = activity.findViewById(R.id.memory_q3_yes);
        CheckBox checkBox6 = activity.findViewById(R.id.memory_q3_no);
        CheckBox checkBox7 = activity.findViewById(R.id.memory_q4_yes);
        CheckBox checkBox8 = activity.findViewById(R.id.memory_q4_no);
        CheckBox checkBox9 = activity.findViewById(R.id.memory_q5_yes);
        CheckBox checkBox10 = activity.findViewById(R.id.memory_q5_no);

        //Assert they are present
        Assert.assertNotNull(checkBox);
        Assert.assertNotNull(checkBox2);
        Assert.assertNotNull(checkBox3);
        Assert.assertNotNull(checkBox4);
        Assert.assertNotNull(checkBox5);
        Assert.assertNotNull(checkBox6);
        Assert.assertNotNull(checkBox7);
        Assert.assertNotNull(checkBox8);
        Assert.assertNotNull(checkBox9);
        Assert.assertNotNull(checkBox10);
        //Find the views
        Button ambulance = activity.findViewById(R.id.memory_call_ambulance);
        Button continueB = activity.findViewById(R.id.memory_continue_btn);
        //Assert they are present
        Assert.assertNotNull(ambulance);
        Assert.assertNotNull(continueB);

    }


    @Test
    public void test_is_view_scrollable() {
        //Find view and perform action
        onView(withId(R.id.memory_call_ambulance)).perform(scrollTo());
    }

    @Test
    public void test_are_yes_checkboxes_checkable() {
        //Find views and perform action
        onView(withId(R.id.memory_q1_yes)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q1_yes)).check(matches(isChecked()));
        //Find views and perform action
        onView(withId(R.id.memory_q2_yes)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q2_yes)).check(matches(isChecked()));
        //Find views and perform action
        onView(withId(R.id.memory_q3_yes)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q3_yes)).check(matches(isChecked()));
        //Find views and perform action
        onView(withId(R.id.memory_q4_yes)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q4_yes)).check(matches(isChecked()));
        //Find views and perform action
        onView(withId(R.id.memory_q5_yes)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q5_yes)).check(matches(isChecked()));
    }

    @Test
    public void test_are_no_checkboxes_checkable() {
        //Find views and perform action
        onView(withId(R.id.memory_q1_no)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q1_no)).check(matches(isChecked()));
        //Find views and perform action
        onView(withId(R.id.memory_q2_no)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q2_no)).check(matches(isChecked()));
        //Find views and perform action
        onView(withId(R.id.memory_q3_no)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q3_no)).check(matches(isChecked()));
        //Find views and perform action
        onView(withId(R.id.memory_q4_no)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q4_no)).check(matches(isChecked()));
        //Find views and perform action
        onView(withId(R.id.memory_q5_no)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q5_no)).check(matches(isChecked()));

    }

    @Test
    public void test_less_then_5_checkboxes_ticked_shows_error_toast() {
        //Find views and perform action
        onView(withId(R.id.memory_q1_no)).perform(click());
        //Find views and perform action
        onView(withId(R.id.memory_q2_no)).perform(click());
        //Find views and perform action
        onView(withId(R.id.memory_q3_no)).perform(click());
        //Find views and perform action
        onView(withId(R.id.memory_q4_no)).perform(click());


        onView(withId(R.id.memory_continue_btn)).perform(scrollTo()).perform(click());
        //Check if action returns expected outcome
        onView(withText("Too few boxes ticked, try again"))
                .inRoot(new LoginScreenTest.ToastMatcher())
                .check(matches(isDisplayed()));


    }

    @Test
    public void test_more_then_5_checkboxes_ticked_shows_error_toast() {
        //Find views and perform action
        onView(withId(R.id.memory_q1_no)).perform(click());
        //Find views and perform action
        onView(withId(R.id.memory_q2_no)).perform(click());
        //Find views and perform action
        onView(withId(R.id.memory_q3_no)).perform(click());
        //Find views and perform action
        onView(withId(R.id.memory_q4_no)).perform(click());
        //Find views and perform action
        onView(withId(R.id.memory_q3_yes)).perform(click());
        //Find views and perform action
        onView(withId(R.id.memory_q4_yes)).perform(click());


        onView(withId(R.id.memory_continue_btn)).perform(scrollTo()).perform(click());
        //Check if action returns expected outcome
        onView(withText("Too many boxes ticked, try again"))
                .inRoot(new LoginScreenTest.ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_continue_button_opens_correct_activity() {
        //ScrollDown
        onView(withId(R.id.memory_continue_btn)).perform(scrollTo());
        //Find the views and perform action
        onView(withId(R.id.memory_continue_btn)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(AddReportActivity.class.getName()));
    }

    @Test
    public void test_call_Ambulance_Button_launches_Dialog() {
        //ScrollDown
        onView(withId(R.id.memory_call_ambulance)).perform(scrollTo());
        //Find the views and perform the action
        onView(withId(R.id.memory_call_ambulance)).perform(click());
        //Check if action returns desired outcome
        onView(withText("CALL AMBULANCE?")).check(matches(isDisplayed()));

    }

}
