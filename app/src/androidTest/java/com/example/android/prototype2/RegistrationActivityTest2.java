package com.example.android.prototype2;


import android.view.View;
import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
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
import static com.example.android.prototype2.LoginScreenTest.PASSWORD_ERROR;
import static com.example.android.prototype2.LoginScreenTest.EMAIL_ERROR;


@RunWith(AndroidJUnit4.class)
public class RegistrationActivityTest2 {




    @Rule
    public IntentsTestRule<RegistrationActivity> mRegistrationActivityTestRule
            = new IntentsTestRule<>(RegistrationActivity.class);

    //Public variables used to test so can use across all validation forms
    public static final String UNSECURE_PASS= "Password is not secure enough, try again";
    public static final String INVALID_EMAIL="Invalid email address";

    //Hint text variables
    public static final String HINT_NAME="Name";
    public static final String HINT_EMAIL="Email";
    public static final String HINT_PHONENO="Phone Number";
    public static final String HINT_DOB="Date of birth";
    public static final String HINT_EMERGENCYCONTACT="Emergency Contact";
    public static final String HINT_EC_PHONENO="Emergency Contact Phone Number";
    public static final String HINT_PASSWORD="Password";


    //Test variables
    public static final String TEST_NAME="John Smith";
    public static final String TEST_EMAIL="john";
    public static final String TEST_PHONENO = "081111";
    public static final String TEST_EMERGENCYCONTACT="Mary Smith";
    public static final String TEST_EC_PHONENO="082222";
    public static final String TEST_PASSWORD="password";

    //Test error message variables
    public static final String NAME_ERROR="Name field cannot be empty";
    public static final String PHONENO_ERROR="Phone number field cannot be empty";
    public static final String AGE_ERROR="You must be over 18 to use this app";





    @Test
    public void hint_text_appears() {

        //Find the view and and check the hint text is present
        onView(withId(R.id.reg_name_edit)).check(matches(withHint(HINT_NAME)));
        //Find the view and and check the hint text is present
        onView(withId(R.id.reg_email_edit)).check(matches(withHint(HINT_EMAIL)));
        //Find the view and and check the hint text is present
        onView(withId(R.id.reg_phone_no_edit)).check(matches(withHint(HINT_PHONENO)));
        //Find the view and and check the hint text is present
        onView(withId(R.id.reg_dob_edit)).check(matches(withHint(HINT_DOB)));
        //Find the view and and check the hint text is present
        onView(withId(R.id.reg_emergency_contact_edit)).check(matches(withHint(HINT_EMERGENCYCONTACT)));
        //Find the view and and check the hint text is present
        onView(withId(R.id.reg_emergency_contact_phone_edit)).check(matches(withHint(HINT_EC_PHONENO)));
        //Find the view and and check the hint text is present
        onView(withId(R.id.reg_password_edit)).check(matches(withHint(HINT_PASSWORD)));

    }

    @Test
    public void error_text_appears_when_fields_empty_and_reg_clicked() {

        //Use the set Date method to pick the date, needs to happen before register is clicked
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 6, 17));

        //Find the view and perform action
        onView(withId(R.id.register_btn)).perform(scrollTo()).perform(click());

        //Find the view and and check the error text is present
        onView(withId(R.id.reg_name)).check(matches(hasTextInputLayoutErrorText(NAME_ERROR)));
        //Find the view and and check the error text is present
        onView(withId(R.id.reg_email)).check(matches(hasTextInputLayoutErrorText((EMAIL_ERROR))));
        //Find the view and and check the error text is present
        onView(withId(R.id.reg_phone_no)).check(matches(hasTextInputLayoutErrorText(PHONENO_ERROR)));
        //Find the view and and check the error text is present
        onView(withId(R.id.reg_dob)).check(matches(hasTextInputLayoutErrorText(AGE_ERROR)));
        //Find the view and and check the error text is present
        onView(withId(R.id.reg_emergency_contact)).check(matches(hasTextInputLayoutErrorText(NAME_ERROR)));
        //Find the view and and check the error text is present
        onView(withId(R.id.reg_emergency_contact_phone)).check(matches(hasTextInputLayoutErrorText(PHONENO_ERROR)));
        //Find the view and and check the error text is present
        onView(withId(R.id.reg_password)).check(matches(hasTextInputLayoutErrorText(PASSWORD_ERROR)));

    }

    @Test
    public void validation_errors_when_email_and_password_dont_follow_rules() {

        //Use the set Date method to pick the date, needs to happen before register is clicked
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1990, 6, 17));
        //Perform the actions to fill in the fields
        onView(withId(R.id.reg_name_edit)).perform(typeText(TEST_NAME), closeSoftKeyboard());
        onView(withId(R.id.reg_email_edit)).perform(typeText(TEST_EMAIL),
                closeSoftKeyboard());
        onView(withId(R.id.reg_phone_no_edit)).perform(typeText(TEST_PHONENO),
                closeSoftKeyboard());
        onView(withId(R.id.reg_emergency_contact_edit)).perform(typeText(TEST_EMERGENCYCONTACT),
                closeSoftKeyboard());
        onView(withId(R.id.reg_emergency_contact_phone_edit)).perform(scrollTo()).perform(typeText(TEST_EC_PHONENO),
                closeSoftKeyboard());
        onView(withId(R.id.reg_password_edit)).perform(scrollTo()).perform(typeText(TEST_PASSWORD),
                closeSoftKeyboard());
        //Find the view and perform action
        onView(withId(R.id.register_btn)).perform(scrollTo()).perform(click());

        //Find the views and check if errors are shown
        onView(withId(R.id.reg_email)).check(matches(hasTextInputLayoutErrorText(INVALID_EMAIL)));
        onView(withId(R.id.reg_password)).check(matches(hasTextInputLayoutErrorText(UNSECURE_PASS)));


    }

    @Test
    public void return_to_login_screen_button() {
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



