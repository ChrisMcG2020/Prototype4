package com.example.android.prototype2;

import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.prototype2.FirebaseTests.DB_TEST_EMAIL;
import static com.example.android.prototype2.FirebaseTests.DB_TEST_PASSWORD;
import static com.example.android.prototype2.RegistrationActivityTest2.TEST_EC_PHONENO;
import static com.example.android.prototype2.RegistrationActivityTest2.TEST_EMERGENCYCONTACT;
import static com.example.android.prototype2.RegistrationActivityTest2.TEST_NAME;
import static com.example.android.prototype2.RegistrationActivityTest2.TEST_PHONENO;
import static java.util.regex.Pattern.matches;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class FirebaseActivitytest {

    @Rule
    public ActivityTestRule<RegistrationActivity> mActivityTestRule =
            new ActivityTestRule<>(RegistrationActivity.class);

    @Test
    public void registration_succeeds() {


        //Use the set Date method to pick the date, needs to happen before register is clicked
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1990, 6, 17));
        //Perform the actions to fill in the fields
        onView(withId(R.id.reg_name_edit)).perform(typeText(TEST_NAME), closeSoftKeyboard());
        onView(withId(R.id.reg_email_edit)).perform(typeText(DB_TEST_EMAIL),
                closeSoftKeyboard());
        onView(withId(R.id.reg_phone_no_edit)).perform(typeText(TEST_PHONENO),
                closeSoftKeyboard());
        onView(withId(R.id.reg_emergency_contact_edit)).perform(typeText(TEST_EMERGENCYCONTACT),
                closeSoftKeyboard());
        onView(withId(R.id.reg_emergency_contact_phone_edit)).perform(scrollTo()).perform(typeText(TEST_EC_PHONENO),
                closeSoftKeyboard());
        onView(withId(R.id.reg_password_edit)).perform(scrollTo()).perform(typeText(DB_TEST_PASSWORD),
                closeSoftKeyboard());
        //Find the view and perform action
        onView(withId(R.id.register_btn)).perform(scrollTo()).perform(click());

        //Implement an idyling resource method to make the test wait to see if activity is launched or not
       // String loginScreen = PlayerLoginActivity.class.getName();
        //Espresso.registerIdlingResources(new LoginScreenTest.WaitActivityIsResumedIdlingResource(loginScreen));

            onView(withText(R.string.registration_success)).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))));
       // onView(withText(R.string.registration_success)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        }

    }
