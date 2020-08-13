package com.example.android.prototype2;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.prototype2.Views.QuizActivity;
import com.example.android.prototype2.Views.Symptoms;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.PendingIntent.getActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SymptomsTest {
    //Set up the rule
    @Rule
    public IntentsTestRule<Symptoms> mSymptomsTestRule
            = new IntentsTestRule<>(Symptoms.class);

    @Test
    public void is_view_scollable() {
        //Find view and perform action
        onView(withId(R.id.symptom_continue)).perform(scrollTo());
    }

    @Test
    public void are_checkboxes_checkable() {
        //Find view and perform action
        onView(withId(R.id.symptom1)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom2)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom3)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom4)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom5)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom6)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom7)).perform(click());
        //Find view and perform action
        //ScrollDown
        onView(withId(R.id.symptom16)).perform(scrollTo());
        onView(withId(R.id.symptom8)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom9)).perform(click());
        //ScrollDown
        onView(withId(R.id.symptom10)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom11)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom12)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom13)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom14)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom15)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom16)).perform(click());
        //ScrollDown
        onView(withId(R.id.symptom_continue)).perform(scrollTo());
        //Find view and perform action
        onView(withId(R.id.symptom17)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom18)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom19)).perform(click());
        //Find view and perform action
        onView(withId(R.id.symptom20)).perform(click());

    }
    @Test
    public void symptom_continue_button_opens_correct_activity() {
        //ScrollDown
        onView(withId(R.id.symptom_continue)).perform(scrollTo());
        //Find the view and perform action
        onView(withId(R.id.symptom_continue)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(QuizActivity.class.getName()));
    }

    @Test
    public void symptom_call_Ambulance_Button_launches_Dialog() {
        //ScrollDown
        onView(withId(R.id.symptom_call_ambulance)).perform(scrollTo());
        //Find the view and perform the action
        onView(withId(R.id.symptom_call_ambulance)).perform(click());
        //Check if action returns desired outcome
        onView(withText("CALL AMBULANCE")).check(matches(isDisplayed()));

    }
    }



