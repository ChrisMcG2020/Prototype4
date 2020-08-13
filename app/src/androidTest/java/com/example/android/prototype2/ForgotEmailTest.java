package com.example.android.prototype2;


import android.content.ComponentName;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matcher;
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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.android.prototype2.LoginScreenTest.EMAIL_ERROR;
import static com.example.android.prototype2.LoginScreenTest.TESTEMAIL;
import static com.example.android.prototype2.RegistrationActivityTest2.INVALID_EMAIL;
import static com.example.android.prototype2.RegistrationActivityTest2.TEST_EMAIL;
import static com.example.android.prototype2.RegistrationActivityTest2.hasTextInputLayoutErrorText;

@RunWith(AndroidJUnit4.class)
public class ForgotEmailTest {

    @Rule
    public IntentsTestRule<PlayerLoginActivity> mLoginActivityTestRule
            = new IntentsTestRule<>(PlayerLoginActivity.class);

   @Test
   public void clickForgot_Password_Without_Email_Entered_Prompts_Error(){

        //Find the view and perform action
        onView(withId(R.id.forgot_pass_btn)).perform(click());

        //If no email entered then correct Error should appear
       onView(withId(R.id.login_email)).check(matches(hasTextInputLayoutErrorText(EMAIL_ERROR)));



    }

   @Test
   public void clickForgot_Password_With_an_Invalid_Email_Prompts_Error(){

        //Find the view and perform action
        onView(withId(R.id.login_email2)).perform(typeText(TEST_EMAIL),
        closeSoftKeyboard());
        onView(withId(R.id.forgot_pass_btn)).perform(click());

        //If an invalid email type entered then correct Error should appear
        onView(withId(R.id.login_email)).check(matches(hasTextInputLayoutErrorText(INVALID_EMAIL)));




    }
    @Test
    public void clickForgot_password_launches_password_email(){

        //Find the view and perform action
        onView(withId(R.id.login_email2)).perform(typeText(TESTEMAIL),
                closeSoftKeyboard());
        onView(withId(R.id.forgot_pass_btn)).perform(click());
        intended(hasComponent((Matcher<ComponentName>) FirebaseAuth.getInstance()));



    }
}
