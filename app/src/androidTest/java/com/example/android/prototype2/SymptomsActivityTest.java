package com.example.android.prototype2;

import android.app.Activity;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.prototype2.views.MemoryActivity;
import com.example.android.prototype2.views.SymptomsActivity;

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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SymptomsActivityTest {
    // Set the rule to apply to the test method and which class to use
    @Rule
    public final IntentsTestRule<SymptomsActivity> mSymptomsTestRule
            = new IntentsTestRule<>(SymptomsActivity.class);

    @Test
    public void test_editText_and_buttons_present() {
        //Get the activity
        Activity activity = mSymptomsTestRule.getActivity();
        //Find the views
        CheckBox checkBox = activity.findViewById(R.id.symptom1);
        CheckBox checkBox2 = activity.findViewById(R.id.symptom2);
        CheckBox checkBox3 = activity.findViewById(R.id.symptom3);
        CheckBox checkBox4 = activity.findViewById(R.id.symptom4);
        CheckBox checkBox5 = activity.findViewById(R.id.symptom5);
        CheckBox checkBox6 = activity.findViewById(R.id.symptom6);
        CheckBox checkBox7 = activity.findViewById(R.id.symptom7);
        CheckBox checkBox8 = activity.findViewById(R.id.symptom8);
        CheckBox checkBox9 = activity.findViewById(R.id.symptom9);
        CheckBox checkBox10 = activity.findViewById(R.id.symptom10);
        CheckBox checkBox11 = activity.findViewById(R.id.symptom11);
        CheckBox checkBox12 = activity.findViewById(R.id.symptom12);
        CheckBox checkBox13 = activity.findViewById(R.id.symptom13);
        CheckBox checkBox14 = activity.findViewById(R.id.symptom14);
        CheckBox checkBox15 = activity.findViewById(R.id.symptom15);
        CheckBox checkBox16 = activity.findViewById(R.id.symptom16);
        CheckBox checkBox17 = activity.findViewById(R.id.symptom17);
        CheckBox checkBox18 = activity.findViewById(R.id.symptom18);
        CheckBox checkBox19 = activity.findViewById(R.id.symptom19);
        CheckBox checkBox20 = activity.findViewById(R.id.symptom20);


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
        Assert.assertNotNull(checkBox11);
        Assert.assertNotNull(checkBox12);
        Assert.assertNotNull(checkBox13);
        Assert.assertNotNull(checkBox14);
        Assert.assertNotNull(checkBox15);
        Assert.assertNotNull(checkBox16);
        Assert.assertNotNull(checkBox17);
        Assert.assertNotNull(checkBox18);
        Assert.assertNotNull(checkBox19);
        Assert.assertNotNull(checkBox20);
        //Find the views
        Button ambulance = activity.findViewById(R.id.symptom_call_ambulance);
        Button continueB = activity.findViewById(R.id.symptom_continue);
        //Assert they are present
        Assert.assertNotNull(ambulance);
        Assert.assertNotNull(continueB);


    }

    @Test
    public void test_is_view_scrollable() {
        //Find view and perform action
        onView(withId(R.id.symptom_continue)).perform(scrollTo());
    }

    @Test
    public void test_are_checkboxes_checkable() {
        //Find views and perform action
        onView(withId(R.id.symptom1)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom2)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom3)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom4)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom5)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom6)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom7)).perform(click());
        //Find views and perform action

        //ScrollDown
        onView(withId(R.id.symptom16)).perform(scrollTo());

        onView(withId(R.id.symptom8)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom9)).perform(click());
        //ScrollDown
        onView(withId(R.id.symptom10)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom11)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom12)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom13)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom14)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom15)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom16)).perform(click());
        //ScrollDown
        onView(withId(R.id.symptom_continue)).perform(scrollTo());
        //Find views and perform action
        onView(withId(R.id.symptom17)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom18)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom19)).perform(click());
        //Find views and perform action
        onView(withId(R.id.symptom20)).perform(click());

    }

    @Test
    public void test_symptom_continue_button_opens_correct_activity() {
        //ScrollDown
        onView(withId(R.id.symptom_continue)).perform(scrollTo());
        //Find the view and perform action
        onView(withId(R.id.symptom_continue)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(MemoryActivity.class.getName()));
    }

    @Test
    public void test_symptom_call_Ambulance_Button_launches_Dialog() {
        //ScrollDown
        onView(withId(R.id.symptom_call_ambulance)).perform(scrollTo());
        //Find the view and perform the action
        onView(withId(R.id.symptom_call_ambulance)).perform(click());
        //Check if action returns desired outcome
        onView(withText("CALL AMBULANCE?")).check(matches(isDisplayed()));

    }
}



