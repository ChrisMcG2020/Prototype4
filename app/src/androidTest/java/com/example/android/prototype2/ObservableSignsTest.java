package com.example.android.prototype2;

import android.app.Activity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.prototype2.dialogs.AmbulanceFragment;
import com.example.android.prototype2.views.ObservableSignsActivity;
import com.example.android.prototype2.views.Symptoms;

import org.junit.Assert;
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
    public IntentsTestRule<ObservableSignsActivity> mObs_Test_Rule
            = new IntentsTestRule<>(ObservableSignsActivity.class);

    @Test
    public void editText_and_buttons_present(){
        //Find the views
        Activity activity=mObs_Test_Rule.getActivity();
        CheckBox checkBox=activity.findViewById(R.id.cb_ob_signs1);
        CheckBox checkBox2=activity.findViewById(R.id.cb_ob_signs2);
        CheckBox checkBox3=activity.findViewById(R.id.cb_ob_signs3);
        CheckBox checkBox4=activity.findViewById(R.id.cb_ob_signs4);
        CheckBox checkBox5=activity.findViewById(R.id.cb_ob_signs5);
        //Assert they are present
        Assert.assertNotNull(checkBox);
        Assert.assertNotNull(checkBox2);
        Assert.assertNotNull(checkBox3);
        Assert.assertNotNull(checkBox4);
        Assert.assertNotNull(checkBox5);
        //Find the views
        Button ambulance=activity.findViewById(R.id.os_call_ambulance);
        Button continueB=activity.findViewById(R.id.os_continue);
        //Assert they are present
        Assert.assertNotNull(ambulance);
        Assert.assertNotNull(continueB);


    }

    @Test
    public void check_boxes_checkable() {
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