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

    //UI & Instrumentation Tests
    @Test
    public void testEmailIntentsPresent() {
        //Click enough buttons to move forward
        onView(withId(R.id.memory_q1_no)).perform(click());
        onView(withId(R.id.memory_q2_no)).perform(click());
        onView(withId(R.id.memory_q3_no)).perform(click());
        onView(withId(R.id.memory_q4_no)).perform(click());
        onView(withId(R.id.memory_q5_no)).perform(click());
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
    public void testNam_intents_present() {
        //Click enough buttons to move forward
        onView(withId(R.id.memory_q1_no)).perform(click());
        onView(withId(R.id.memory_q2_no)).perform(click());
        onView(withId(R.id.memory_q3_no)).perform(click());
        onView(withId(R.id.memory_q4_no)).perform(click());
        onView(withId(R.id.memory_q5_no)).perform(click());
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
    public void testRedFlag_intents_present() {
        //Click enough buttons to move forward
        onView(withId(R.id.memory_q1_no)).perform(click());
        onView(withId(R.id.memory_q2_no)).perform(click());
        onView(withId(R.id.memory_q3_no)).perform(click());
        onView(withId(R.id.memory_q4_no)).perform(click());
        onView(withId(R.id.memory_q5_no)).perform(click());
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
    public void testobs_intents_present() {
        //Click enough buttons to move forward
        onView(withId(R.id.memory_q1_no)).perform(click());
        onView(withId(R.id.memory_q2_no)).perform(click());
        onView(withId(R.id.memory_q3_no)).perform(click());
        onView(withId(R.id.memory_q4_no)).perform(click());
        onView(withId(R.id.memory_q5_no)).perform(click());
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
    public void testsymptoms_intents_present() {
        //Click enough buttons to move forward
        onView(withId(R.id.memory_q1_no)).perform(click());
        onView(withId(R.id.memory_q2_no)).perform(click());
        onView(withId(R.id.memory_q3_no)).perform(click());
        onView(withId(R.id.memory_q4_no)).perform(click());
        onView(withId(R.id.memory_q5_no)).perform(click());
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
