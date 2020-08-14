package com.example.android.prototype2;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.android.prototype2.views.RedFlagActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;


public class AmbulanceFragmentTest {
    @Rule
    public IntentsTestRule<RedFlagActivity> mAmbulanceTestRule
            = new IntentsTestRule<>(RedFlagActivity.class);


    int titleId = mAmbulanceTestRule.getActivity().getResources()
            .getIdentifier("alertTitle", "id", "android");


    @Test
    public void check_dialog_appears() {

        onView(withId(R.id.redFlag_call_ambulance)).perform(click());

        onView(withId(titleId))
                .inRoot(isDialog())
                .check(matches(withText("CALL AMBULANCE?")))
                .check(matches(isDisplayed()));
    }
        @Test
        public void dialogFragmentIsShownToTheUser() {

        }


    }
