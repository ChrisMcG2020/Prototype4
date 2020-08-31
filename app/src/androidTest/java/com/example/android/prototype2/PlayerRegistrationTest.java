package com.example.android.prototype2;


import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.prototype2.views.PlayerLoginActivity;
import com.example.android.prototype2.views.PlayerRegistrationActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.android.prototype2.LoginScreenTest.EMAIL_ERROR;
import static com.example.android.prototype2.LoginScreenTest.PASSWORD_ERROR;


@RunWith(AndroidJUnit4.class)
public class PlayerRegistrationTest {

    // Set the rule to apply to the test method and which class to use
    @Rule
    public final IntentsTestRule<PlayerRegistrationActivity> mRegistrationActivityTestRule
            = new IntentsTestRule<>(PlayerRegistrationActivity.class);

    //Public variables used to test so can use across all validation forms
    public static final String UNSECURE_PASS = "Password must have at least 1 upper case,1 special character";
    public static final String INVALID_EMAIL = "Invalid email address";

    //Hint text variables
    public static final String HINT_NAME = "Full Name *";
    public static final String HINT_EMAIL = "Email *";
    public static final String HINT_PHONENO = "Phone Number *";
    public static final String HINT_DOB = "Date of Birth *";
    public static final String HINT_EMERGENCYCONTACT = "Emergency Contact *";
    public static final String HINT_EC_PHONENO = "Emergency Contact Phone Number *";
    public static final String HINT_PASSWORD = "Password *";


    //Test variables
    public static final String TEST_NAME = "John Smith";
    public static final String TEST_EMAIL = "john";
    public static final String TEST_PHONENO = "081111";
    public static final String TEST_EMERGENCYCONTACT = "Mary Smith";
    public static final String TEST_EC_PHONENO = "082222";
    public static final String TEST_PASSWORD = "password";
    public static final String TEST_PHONE_FAIL = "12";

    //Test error message variables
    public static final String NAME_ERROR = "Name field cannot be empty";
    public static final String PHONENO_ERROR = "Phone number field cannot be empty";
    public static final String AGE_ERROR = "You must be over 18 to use this app";
    public static final String PHONE_FORMAT_ERROR = "Is number correctly formatted?";


    //UI Tests
    @Test
    public void test_reg_fields_present() {
        //Get the activity
        Activity activity = mRegistrationActivityTestRule.getActivity();
        //Find the views and assert they are present
        EditText name = activity.findViewById(R.id.reg_name_edit);
        Assert.assertNotNull(name);
        EditText email = activity.findViewById(R.id.reg_email_edit);
        Assert.assertNotNull(email);
        EditText phone = activity.findViewById(R.id.reg_phone_no_edit);
        Assert.assertNotNull(phone);
        DatePicker date = activity.findViewById(R.id.date_picker);
        Assert.assertNotNull(date);
        EditText emergencyC = activity.findViewById(R.id.reg_emergency_contact_edit);
        Assert.assertNotNull(emergencyC);
        EditText eC_phone = activity.findViewById(R.id.reg_emergency_contact_phone_edit);
        Assert.assertNotNull(eC_phone);
        EditText pass = activity.findViewById(R.id.reg_password_edit);
        Assert.assertNotNull(pass);

    }

    @Test
    public void test_reg_buttons_present() {
        //Find the view and perform action
        onView(withId(R.id.register_btn)).perform(scrollTo());
        //Get the activity
        Activity activity = mRegistrationActivityTestRule.getActivity();
        //Find the views and assert they are present
        Button reg = activity.findViewById(R.id.register_btn);
        Assert.assertNotNull(reg);
        Button back = activity.findViewById(R.id.back_to_login);
        Assert.assertNotNull(back);

    }


    @Test
    public void test_hint_text_appears() {

        //Find the views and and check the hint text is present
        onView(withId(R.id.reg_name_edit)).check(matches(withHint(HINT_NAME)));
        //Find the views and and check the hint text is present
        onView(withId(R.id.reg_email_edit)).check(matches(withHint(HINT_EMAIL)));
        //Find the views and and check the hint text is present
        onView(withId(R.id.reg_phone_no_edit)).check(matches(withHint(HINT_PHONENO)));
        //Find the views and and check the hint text is present
        onView(withId(R.id.reg_dob_edit)).check(matches(withHint(HINT_DOB)));
        //Find the views and and check the hint text is present
        onView(withId(R.id.reg_emergency_contact_edit)).check(matches(withHint(HINT_EMERGENCYCONTACT)));
        //Find the views and and check the hint text is present
        onView(withId(R.id.reg_emergency_contact_phone_edit)).check(matches(withHint(HINT_EC_PHONENO)));
        //Find the views and and check the hint text is present
        onView(withId(R.id.reg_password_edit)).check(matches(withHint(HINT_PASSWORD)));

    }

    //UI & Instrumentation Tests
    @Test
    public void test_error_text_appears_when_fields_empty_and_reg_clicked() {
        //Use the set Date method to pick the date, needs to happen before register is clicked
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 6, 17));

        //Find the views and perform action
        onView(withId(R.id.register_btn)).perform(scrollTo()).perform(click());

        //Find the views and and check the error text is present
        onView(withId(R.id.reg_name)).check(matches(hasTextInputLayoutErrorText(NAME_ERROR)));
        //Find the views and and check the error text is present
        onView(withId(R.id.reg_email)).check(matches(hasTextInputLayoutErrorText((EMAIL_ERROR))));
        //Find the views and and check the error text is present
        onView(withId(R.id.reg_phone_no)).check(matches(hasTextInputLayoutErrorText(PHONENO_ERROR)));
        //Find the views and and check the error text is present
        onView(withId(R.id.reg_dob)).check(matches(hasTextInputLayoutErrorText(AGE_ERROR)));
        //Find the views and and check the error text is present
        onView(withId(R.id.reg_emergency_contact)).check(matches(hasTextInputLayoutErrorText(NAME_ERROR)));
        //Find the views and and check the error text is present
        onView(withId(R.id.reg_emergency_contact_phone)).check(matches(hasTextInputLayoutErrorText(PHONENO_ERROR)));
        //Find the views and and check the error text is present
        onView(withId(R.id.reg_password)).check(matches(hasTextInputLayoutErrorText(PASSWORD_ERROR)));

    }

    @Test
    public void test_validation_errors_when_player_email_password_phone_dont_follow_rules() throws InterruptedException {

        //Use the set Date method to pick the date, needs to happen before register is clicked
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1990, 6, 17));
        //Perform the actions to fill in the fields
        onView(withId(R.id.reg_name_edit)).perform(typeText(TEST_NAME),
                closeSoftKeyboard());
        onView(withId(R.id.reg_email_edit)).perform(typeText(TEST_EMAIL),
                closeSoftKeyboard());
        onView(withId(R.id.reg_phone_no_edit)).perform(typeText(TEST_PHONE_FAIL));
        closeSoftKeyboard();
        onView(withId(R.id.reg_emergency_contact_edit)).perform(scrollTo()).perform(typeText(TEST_EMERGENCYCONTACT));
        closeSoftKeyboard();
        onView(withId(R.id.reg_emergency_contact_phone_edit)).perform(scrollTo()).perform(typeText(TEST_EC_PHONENO));
        closeSoftKeyboard();
        onView(withId(R.id.reg_password_edit)).perform(scrollTo()).perform(scrollTo()).perform(typeText(TEST_PASSWORD),
                closeSoftKeyboard());
        //Find the view and perform action
        onView(withId(R.id.register_btn)).perform(scrollTo()).perform(click());

        //Pause the test in time for errors to show.
        Thread.sleep(1500);


        //Find the views and check if errors are shown
        onView(withId(R.id.reg_email)).check(matches(hasTextInputLayoutErrorText(INVALID_EMAIL)));
        onView(withId(R.id.reg_password)).check(matches(hasTextInputLayoutErrorText(UNSECURE_PASS)));
        onView(withId(R.id.reg_phone_no)).check(matches(hasTextInputLayoutErrorText(PHONE_FORMAT_ERROR)));


    }

    @Test
    public void test_return_to_login_screen_button() {
        //Find the view and perform action
        onView(withId(R.id.back_to_login)).perform(scrollTo()).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(PlayerLoginActivity.class.getName()));

    }


    //Custom matcher to match the errors in the TextInputLayouts
    public static Matcher<View> hasTextInputLayoutErrorText(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextInputLayout)) {
                    return false;
                }

                CharSequence error = ((TextInputLayout) view).getError();

                if (error == null) {
                    return false;
                }

                String hint = error.toString();

                return expectedErrorText.equals(hint);
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }

    //Custom method to set the date in the date picker
    public static void setDate(int year, int monthOfYear, int dayOfMonth) {
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withId(R.id.register_btn)).perform(click());

    }

}




