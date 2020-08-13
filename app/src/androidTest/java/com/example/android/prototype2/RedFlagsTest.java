package com.example.android.prototype2;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.prototype2.views.ObservableSignsActivity;
import com.example.android.prototype2.views.RedFlagActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RedFlagsTest {

    @Rule
    public IntentsTestRule<RedFlagActivity> mRF_MenuNavTestRule
            = new IntentsTestRule<>(RedFlagActivity.class);

    @Test
    public void rf_continue_button_opens_correct_activity() {
        //Find the view and perform action
        onView(withId(R.id.redFlag_continue)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(ObservableSignsActivity.class.getName()));
    }

    @Test
    public void rf_call_Ambulance_Button_launches_Dialog() {
        //Find the view and perform the action
        onView(withId(R.id.redFlag_call_ambulance)).perform(click());
        //Check if action returns desired outcome
        onView(withText("CALL AMBULANCE?"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));


    }
}
