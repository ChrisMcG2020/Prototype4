package com.example.android.prototype2;


import android.app.Activity;
import android.support.test.filters.LargeTest;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginScreenTest {

    @Rule
    public IntentsTestRule<PlayerLoginActivity> mLoginActivityTestRule
            = new IntentsTestRule<>(PlayerLoginActivity.class);


    //Public variables used to test so can use across all validation forms
    public static final String TESTEMAIL = "test@test.com";
    public static final String TESTPASSWORD = "Password@";
    public static final String EMAIL_ERROR = "Email field cannot be empty";
    public static final String PASSWORD_ERROR = "Password field cannot be empty";
    public static final String VALID_EMAIL = "chrismcglynn2002@hotmail.com";
    public static final String INVALID_USER="Email/Password Incorrect";

    @Test
    public void hint_text_appears() {

        //Find the view and and check the hint text is present
        onView(withId(R.id.login_email2)).check(matches(withHint("Email")));

        //Find the view and and check the hint text is present
        onView(withId(R.id.login_password2)).check(matches(withHint("Password")));
    }


    @Test
    public void click_login_with_empty_fields_gives_errors() {

        //Find the view and perform action
        onView(withId(R.id.login)).perform(click());
        //Check if view does what it should
        onView(withId(R.id.login_email)).check(matches(hasTextInputLayoutErrorText(EMAIL_ERROR)));
        onView(withId(R.id.login_password)).check(matches(hasTextInputLayoutErrorText(PASSWORD_ERROR)));
    }

    @Test
    public void click_login_with_empty_password_gives_errors() {
        //Find the view and perform action
        onView(withId(R.id.login_email2)).perform(typeText(TESTEMAIL),
                closeSoftKeyboard());

        //Check if view does what it should
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.login_password)).check(matches(hasTextInputLayoutErrorText(PASSWORD_ERROR)));

    }


    @Test
    public void click_login_with_empty_email_gives_errors() {
        //Find the view and perform action
        onView(withId(R.id.login_password2)).perform(typeText(TESTPASSWORD),
                closeSoftKeyboard());

        //Check if view does what it should
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.login_email)).check(matches(hasTextInputLayoutErrorText(EMAIL_ERROR)));
    }

    @Test
    public void testLogin_WithEmail_Password_launches_profile() {


        onView(withId(R.id.login_email2))
                .perform(typeText(VALID_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.login_password2))
                .perform(replaceText(TESTPASSWORD), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());


        //Implement an idling resource method to make the test wait to see if activity is launched or not
        String userProfile = UserProfile.class.getName();
        Espresso.registerIdlingResources(new WaitActivityIsResumedIdlingResource(userProfile));
        intended(hasComponent(hasClassName(userProfile)));

        intended(hasComponent(UserProfile.class.getName()));


    }

  @Test //FIREBASE CONNECTION???

  public void testLogin_with_Incorrect_Details(){
        onView(withId(R.id.login_email2))
                .perform(typeText(TESTEMAIL), closeSoftKeyboard());
        onView(withId(R.id.login_password2))
                .perform(replaceText(TESTPASSWORD), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());

        onView(withId(R.id.login_email)).check(matches(hasTextInputLayoutErrorText(INVALID_USER)));

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
    //Idling resource method to ensure test waits to check if activity is launched or not
    static class WaitActivityIsResumedIdlingResource implements IdlingResource {
        private final ActivityLifecycleMonitor instance;
        private final String activityToWaitClassName;
        private volatile ResourceCallback resourceCallback;
        boolean resumed = false;

        public WaitActivityIsResumedIdlingResource(String activityToWaitClassName) {
            instance = ActivityLifecycleMonitorRegistry.getInstance();
            this.activityToWaitClassName = activityToWaitClassName;
        }

        @Override
        public String getName() {
            return this.getClass().getName();
        }

        @Override
        public boolean isIdleNow() {
            resumed = isActivityLaunched();
            if(resumed && resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }

            return resumed;
        }

        private boolean isActivityLaunched() {
            Collection<Activity> activitiesInStage = instance.getActivitiesInStage(Stage.RESUMED);
            for (Activity activity : activitiesInStage) {
                if(activity.getClass().getName().equals(activityToWaitClassName)){
                    return true;
                }
            }
            return false;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            this.resourceCallback = resourceCallback;
        }
    }
}
