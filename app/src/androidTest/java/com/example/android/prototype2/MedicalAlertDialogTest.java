package com.example.android.prototype2;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.android.prototype2.views.PlayerListViewActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MedicalAlertDialogTest {


    //Set the rule to apply to the test method and which class to use
    @Rule
    public IntentsTestRule<PlayerListViewActivity> mDeleteProfileTestRule
            = new IntentsTestRule<>(PlayerListViewActivity.class);

    @Test
    public void dialog_appears_with_buttons() {
        //Find the view and perform the action

        //The medical alert dialog appears with two options
        onView(withText(R.string.medical_advice))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
        onView(withText("I understand"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }
    @Test
    public void button_clickable() {
        //Find the view and perform the action
        onView(withText("I understand")).perform(click());

    }
}
