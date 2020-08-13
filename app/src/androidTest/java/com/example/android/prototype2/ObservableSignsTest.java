package com.example.android.prototype2;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.prototype2.Dialogs.AmbulanceFragment;
import com.example.android.prototype2.Views.ObservableSignsActivity;
import com.example.android.prototype2.Views.Symptoms;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ObservableSignsTest {
    @Rule
    public IntentsTestRule<ObservableSignsActivity> mVS_MenuNavTestRule
            = new IntentsTestRule<>(ObservableSignsActivity.class);

@Test

    public void check_boxes_checkable(){
    //Find the view and perform action
    onView(withId(R.id.cb_ob_signs1)).perform(click());
    //Check if action returns desired outcome
    onView(withId(R.id.cb_ob_signs1)).check(matches(isChecked()));
    //Find the view and perform action
    onView(withId(R.id.cb_ob_signs2)).perform(click());
    //Check if action returns desired outcome
    onView(withId(R.id.cb_ob_signs2)).check(matches(isChecked()));
    //Find the view and perform action
    onView(withId(R.id.cb_ob_signs3)).perform(click());
    //Check if action returns desired outcome
    onView(withId(R.id.cb_ob_signs3)).check(matches(isChecked()));
    //Find the view and perform action
    onView(withId(R.id.cb_ob_signs4)).perform(click());
    //Check if action returns desired outcome
    onView(withId(R.id.cb_ob_signs4)).check(matches(isChecked()));
    //Find the view and perform action
    onView(withId(R.id.cb_ob_signs5)).perform(click());
    //Check if action returns desired outcome
    onView(withId(R.id.cb_ob_signs5)).check(matches(isChecked()));






}
    @Test
    public void continue_button_opens_correct_activity() {
        //Find the view and perform action
        onView(withId(R.id.os_continue)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(Symptoms.class.getName()));
    }

    @Test
    public void call_Ambulance_Button_launches_Activity() {
        //Find the view and perform the action
        onView(withId(R.id.os_call_ambulance)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(AmbulanceFragment.class.getName()));
    }
}