package com.example.android.prototype2;


import android.app.Activity;
import android.os.IBinder;
import android.support.test.filters.LargeTest;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.Root;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.prototype2.RegistrationActivityTest2.INVALID_EMAIL;
import static com.example.android.prototype2.RegistrationActivityTest2.TEST_EMAIL;
import static com.example.android.prototype2.RegistrationActivityTest2.hasTextInputLayoutErrorText;


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
    public static final String VALID_EMAIL = "berniegault@gmail.com";
    public static final String INVALID_USER="Email/Password Incorrect";

    @Test
    public void loginAndPasswordFields(){
        Activity activity = mLoginActivityTestRule.getActivity();
        EditText email=activity.findViewById(R.id.login_email2);
        Assert.assertNotNull(email);
        EditText pass=activity.findViewById(R.id.login_password2);
        Assert.assertNotNull(pass);

    }
    @Test
    public void loginAndRegButtons(){
        Activity activity = mLoginActivityTestRule.getActivity();
        Button login=activity.findViewById(R.id.login);
        Assert.assertNotNull(login);
        Button register=activity.findViewById(R.id.player_reg_btn);
        Assert.assertNotNull(register);
        Button forgotP=activity.findViewById(R.id.forgot_pass_btn);
        Assert.assertNotNull(forgotP);
    }
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

  @Test

  public void testLogin_with_Incorrect_Details(){
        onView(withId(R.id.login_email2))
                .perform(typeText(TESTEMAIL), closeSoftKeyboard());
        onView(withId(R.id.login_password2))
                .perform(replaceText(TESTPASSWORD), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());

        onView(withId(R.id.login_email)).check(matches(hasTextInputLayoutErrorText(INVALID_USER)));

    }

    @Test
    public void clickForgot_Password_Without_Email_Entered_Prompts_Error() {

        //Find the view and perform action
        onView(withId(R.id.forgot_pass_btn)).perform(click());

        //If no email entered then correct Error should appear
        onView(withId(R.id.login_email)).check(matches(hasTextInputLayoutErrorText(EMAIL_ERROR)));


    }

    @Test
    public void clickForgot_Password_With_an_Invalid_Email_Prompts_Error() {

        //Find the view and perform action
        onView(withId(R.id.login_email2)).perform(typeText(TEST_EMAIL),
                closeSoftKeyboard());
        onView(withId(R.id.forgot_pass_btn)).perform(click());

        //If an invalid email type entered then correct Error should appear
        onView(withId(R.id.login_email)).check(matches(hasTextInputLayoutErrorText(INVALID_EMAIL)));


    }
    @Test
    public void clickForgot_password_registered_user_sends_email() {

        //Find the view and perform action
        onView(withId(R.id.login_email2)).perform(typeText(VALID_EMAIL),
                closeSoftKeyboard());
        onView(withId(R.id.forgot_pass_btn)).perform(click());

        //Implement an idling resource method to make the test wait to see if activity is launched or not
        String login = PlayerLoginActivity.class.getName();
        Espresso.registerIdlingResources(new com.example.android.prototype2.LoginScreenTest.WaitActivityIsResumedIdlingResource(login));

        //Check if action returns expected outcome
        onView(withText("Reset password email sent!"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickForgot_password_unregistered_user_gives_error() {

        //Find the view and perform action
        onView(withId(R.id.login_email2)).perform(typeText(TESTEMAIL),
                closeSoftKeyboard());
        onView(withId(R.id.forgot_pass_btn)).perform(click());

        //Implement an idling resource method to make the test wait to see if activity is launched or not
        String login = PlayerLoginActivity.class.getName();
        Espresso.registerIdlingResources(new com.example.android.prototype2.LoginScreenTest.WaitActivityIsResumedIdlingResource(login));

        //Check if action returns expected outcome
        onView(withText("Error! Reset email not sent"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
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
    //Custom ToastMatcher to test if a toast is displayed
    public static class ToastMatcher extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    return true;
                }
            }
            return false;
        }
    }


}
