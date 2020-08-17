package com.example.android.prototype2;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.android.prototype2.LoginScreenTest.EMAIL_ERROR;
import static com.example.android.prototype2.LoginScreenTest.PASSWORD_ERROR;
import static com.example.android.prototype2.PlayerRegistrationTest.HINT_EMAIL;
import static com.example.android.prototype2.PlayerRegistrationTest.HINT_NAME;
import static com.example.android.prototype2.PlayerRegistrationTest.HINT_PASSWORD;
import static com.example.android.prototype2.PlayerRegistrationTest.HINT_PHONENO;
import static com.example.android.prototype2.PlayerRegistrationTest.INVALID_EMAIL;
import static com.example.android.prototype2.PlayerRegistrationTest.NAME_ERROR;
import static com.example.android.prototype2.PlayerRegistrationTest.PHONENO_ERROR;
import static com.example.android.prototype2.PlayerRegistrationTest.TEST_EMAIL;
import static com.example.android.prototype2.PlayerRegistrationTest.TEST_NAME;
import static com.example.android.prototype2.PlayerRegistrationTest.TEST_PASSWORD;
import static com.example.android.prototype2.PlayerRegistrationTest.TEST_PHONENO;
import static com.example.android.prototype2.PlayerRegistrationTest.UNSECURE_PASS;
import static com.example.android.prototype2.PlayerRegistrationTest.hasTextInputLayoutErrorText;


@RunWith(AndroidJUnit4.class)
public class CoachRegistrationActivityTest {

    //Set the rule to apply to the test method and which class to use
    @Rule
    public IntentsTestRule<CoachRegistrationActivity> mCoachRegistrationActivityTestRule
            = new IntentsTestRule<>(CoachRegistrationActivity.class);

    //Variables used to test
    private static final String TEAM_HINT = "Team Coached *";
    private static final String TEAM_ERROR = "Team coached field cannot be empty";
    private static final String TEST_TEAM = "Liverpool";


    @Test
    public void testRegFieldsPresent() {
        //Get the activity
        Activity activity = mCoachRegistrationActivityTestRule.getActivity();
        //Find the views and assert they are present
        EditText name = activity.findViewById(R.id.coach_name_edit);
        Assert.assertNotNull(name);
        EditText email = activity.findViewById(R.id.coach_email_edit);
        Assert.assertNotNull(email);
        EditText phone = activity.findViewById(R.id.coach_phone_no_edit);
        Assert.assertNotNull(phone);
        EditText teamCoached = activity.findViewById(R.id.team_coached_edit);
        Assert.assertNotNull(teamCoached);


    }

    @Test
    public void regButtonsPresent() {
        //Get the activity
        Activity activity = mCoachRegistrationActivityTestRule.getActivity();
        //Find the views and perform action and assert they are present
        Button reg = activity.findViewById(R.id.coach_register);
        Assert.assertNotNull(reg);
        Button back = activity.findViewById(R.id.coach_return_to_login);
        Assert.assertNotNull(back);

    }

    @Test
    public void hint_text_appears() {
        //Find the views and and check the hint text is present
        onView(withId(R.id.coach_name_edit)).check(matches(withHint(HINT_NAME)));
        //Find the views and and check the hint text is present
        onView(withId(R.id.coach_email_edit)).check(matches(withHint(HINT_EMAIL)));
        //Find the views and and check the hint text is present
        onView(withId(R.id.coach_phone_no_edit)).check(matches(withHint(HINT_PHONENO)));
        //Find the views and and check the hint text is present
        onView(withId(R.id.team_coached_edit)).check(matches(withHint(TEAM_HINT)));
        //Find the views and and check the hint text is present
        onView(withId(R.id.coach_password_edit)).check(matches(withHint(HINT_PASSWORD)));

    }

    @Test
    public void error_text_appears_when_fields_empty_and_reg_clicked() {

        //Find the views and perform action
        onView(withId(R.id.coach_register)).perform(click());

        //Find the views and and check the error text is present
        onView(withId(R.id.coach_name)).check(matches(hasTextInputLayoutErrorText(NAME_ERROR)));
        //Find the views and and check the error text is present
        onView(withId(R.id.coach_email)).check(matches(hasTextInputLayoutErrorText((EMAIL_ERROR))));
        //Find the views and and check the error text is present
        onView(withId(R.id.coach_phone_no)).check(matches(hasTextInputLayoutErrorText(PHONENO_ERROR)));

        //Find the views and and check the error text is present
        onView(withId(R.id.team_coached)).check(matches(hasTextInputLayoutErrorText(TEAM_ERROR)));
        //Find the views and and check the error text is present
        onView(withId(R.id.coach_password)).check(matches(hasTextInputLayoutErrorText(PASSWORD_ERROR)));

    }

    @Test
    public void validation_errors_when_email_and_password_dont_follow_rules() {
        //Perform the actions to fill in the fields
        onView(withId(R.id.coach_name_edit)).perform(typeText(TEST_NAME), closeSoftKeyboard());
        onView(withId(R.id.coach_email_edit)).perform(typeText(TEST_EMAIL),
                closeSoftKeyboard());
        onView(withId(R.id.coach_phone_no_edit)).perform(typeText(TEST_PHONENO),
                closeSoftKeyboard());
        onView(withId(R.id.team_coached_edit)).perform(typeText(TEST_TEAM),
                closeSoftKeyboard());

        onView(withId(R.id.coach_password_edit)).perform(typeText(TEST_PASSWORD),
                closeSoftKeyboard());
        //Find the views and perform action
        onView(withId(R.id.coach_register)).perform(click());

        //Find the views and check if errors are shown
        onView(withId(R.id.coach_email)).check(matches(hasTextInputLayoutErrorText(INVALID_EMAIL)));
        onView(withId(R.id.coach_password)).check(matches(hasTextInputLayoutErrorText(UNSECURE_PASS)));


    }

    @Test
    public void return_to_login_screen_button() {
        //Find the views and perform action
        onView(withId(R.id.coach_return_to_login)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(PlayerLoginActivity.class.getName()));

    }

}
