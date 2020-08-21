package com.example.android.prototype2;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.android.prototype2.views.MemoryActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class IntentsTest {
    // Set the rule to apply to the test method and which class to use
    @Rule
    public IntentsTestRule<MemoryActivity> mIntentsTestRule
            = new IntentsTestRule<>(MemoryActivity.class);

    @Test
    public void testEmailIntentsPresent() {
        //ScrollDown
        onView(withId(R.id.memory_continue_btn)).perform(scrollTo());
        //Find the view and perform action
        onView(withId(R.id.memory_continue_btn)).perform(click());
        //Intended activity has the intent
        intended(allOf(
                toPackage("com.example.android.prototype2"),
                hasExtra("email4", null)));
    }


    @Test
    public void testNameIntentsPresent() {
        //ScrollDown
        onView(withId(R.id.memory_continue_btn)).perform(scrollTo());
        //Find the view and perform action
        onView(withId(R.id.memory_continue_btn)).perform(click());
        //Intended activity has the intent
        intended(allOf(
                toPackage("com.example.android.prototype2"),
                hasExtra("name4", null)));
    }
    @Test
    public void testRedFlagIntentsPresent() {
        //ScrollDown
        onView(withId(R.id.memory_continue_btn)).perform(scrollTo());
        //Find the view and perform action
        onView(withId(R.id.memory_continue_btn)).perform(click());
        //Intended activity has the intent
        intended(allOf(
                toPackage("com.example.android.prototype2"),
                hasExtra("redFlag4", null)));
    }
    @Test
    public void testobsIntentsPresent() {
        //ScrollDown
        onView(withId(R.id.memory_continue_btn)).perform(scrollTo());
        //Find the view and perform action
        onView(withId(R.id.memory_continue_btn)).perform(click());
        //Intended activity has the intent
        intended(allOf(
                toPackage("com.example.android.prototype2"),
                hasExtra("obs4", null)));
    }
    @Test
    public void testsymptomsIntentsPresent() {
        //ScrollDown
        onView(withId(R.id.memory_continue_btn)).perform(scrollTo());
        //Find the view and perform action
        onView(withId(R.id.memory_continue_btn)).perform(click());
        //Intended activity has the intent
        intended(allOf(
                toPackage("com.example.android.prototype2"),
                hasExtra("symptom4", null)));
    }



}