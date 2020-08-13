package com.example.android.prototype2;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.prototype2.views.QuizActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {
    @Rule
    public IntentsTestRule<QuizActivity> mQuizTestRule
            = new IntentsTestRule<>(QuizActivity.class);


    @Test
    public void is_view_scrollable() {
        //Find view and perform action
        onView(withId(R.id.memory_call_ambulance)).perform(scrollTo());
    }

    @Test
    public void are_yes_checkboxes_checkable() {
        //Find view and perform action
        onView(withId(R.id.memory_q1_yes)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q1_yes)).check(matches(isChecked()));
        //Find view and perform action
        onView(withId(R.id.memory_q2_yes)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q2_yes)).check(matches(isChecked()));
        //Find view and perform action
        onView(withId(R.id.memory_q3_yes)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q3_yes)).check(matches(isChecked()));
        //Find view and perform action
        onView(withId(R.id.memory_q4_yes)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q4_yes)).check(matches(isChecked()));
        //Find view and perform action
        onView(withId(R.id.memory_q5_yes)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q5_yes)).check(matches(isChecked()));
    }

    @Test
    public void are_no_checkboxes_checkable() {
        //Find view and perform action
        onView(withId(R.id.memory_q1_no)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q1_no)).check(matches(isChecked()));
        //Find view and perform action
        onView(withId(R.id.memory_q2_no)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q2_no)).check(matches(isChecked()));
        //Find view and perform action
        onView(withId(R.id.memory_q3_no)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q3_no)).check(matches(isChecked()));
        //Find view and perform action
        onView(withId(R.id.memory_q4_no)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q4_no)).check(matches(isChecked()));
        //Find view and perform action
        onView(withId(R.id.memory_q5_no)).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.memory_q5_no)).check(matches(isChecked()));

    }

}