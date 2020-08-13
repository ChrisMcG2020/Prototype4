package com.example.android.prototype2;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.prototype2.Views.IncidentListView;
import com.example.android.prototype2.Views.SplashScreen;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.openLinkWithText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class UserProfileTest {

    //Test variables
    public static final String TEST_UPDATE_NAME = "John Smith";
    public static final String TEST_UPDATE_EMAIL = "john@gmail.com";
    public static final String TEST_UPDATE_PHONENO = "081111";
    public static final String TEST_UPDATE_EMERGENCYCONTACT = "Mary Smith";
    public static final String TEST_UPDATE_EC_PHONENO = "082222";
    @Rule
    public IntentsTestRule<UserProfile> mUserProfileTestRule
            = new IntentsTestRule<>(UserProfile.class);

    @Test
    public void click_onRecovery_pic_opens_Recovery_advice() {
        //Find the view and perform action
        onView(withId(R.id.recovery_image)).perform(click());
        //Check if view does what it should
        intended(hasComponent(RecoveryActivity.class.getName()));

    }

    @Test
    public void on_Incidents_pic_opens_report() {
        //Find the view and perform action
        onView(withId(R.id.incident_text)).perform(click());
        //Check if view does what it should
        intended(hasComponent(IncidentListView.class.getName()));
    }

    @Test
    public void edit_user_details() {
        //Find the view and perform action
        onView(withId(R.id.edit_text_profile_full_name)).perform(typeText(TEST_UPDATE_NAME));
        closeSoftKeyboard();

        onView(withId(R.id.btn_updatePlayerProfile)).perform(click());
        onView(withText(TEST_UPDATE_NAME)).check(matches(isDisplayed()));
    }

    @Test
    public void edit_phone() {
        onView(withId(R.id.edit_text_profile_phone)).perform(typeText(TEST_UPDATE_PHONENO));
        closeSoftKeyboard();
    }

    @Test
    public void edit_email() {
        onView(withId(R.id.edit_text_profile_email)).perform(typeText(TEST_UPDATE_EMAIL));
        closeSoftKeyboard();

        onView(withId(R.id.btn_updatePlayerProfile)).perform(scrollTo()).perform(forceClick());

        onView(withText(TEST_UPDATE_EMAIL)).check(matches(isDisplayed()));






    }

    @Test
    public void edit_emergency_C() {
        onView(withId(R.id.edit_text_emergency_contact)).perform(typeText(TEST_UPDATE_EMERGENCYCONTACT)
                ,closeSoftKeyboard());

        onView(withId(R.id.btn_updatePlayerProfile)).perform(scrollTo()).perform(forceClick());
        onView(withText(TEST_UPDATE_EMERGENCYCONTACT)).check(matches(isDisplayed()));
    }

    @Test
    public void edit_contact_phone() {
        onView(withId(R.id.edit_text_profile_email)).perform(typeText(TEST_UPDATE_EMAIL));
        closeSoftKeyboard();

        onView(withId(R.id.edit_text_profile_emergency_phone)).perform(forceClick()).perform(typeText(TEST_UPDATE_EC_PHONENO));

        onView(withId(R.id.btn_updatePlayerProfile)).perform(scrollTo()).perform(forceClick());
        closeSoftKeyboard();
    }

   @Test
   public void logout_profile(){
        //Find the view and perform the action
        onView(withId(R.id.btn_logoutProfile)).perform(scrollTo()).perform(click());
        //
        intended(hasComponent(SplashScreen.class.getName()));

    }

    @Test
    public void delete_profile() {
        //Find the view and perform the action
        onView(withId(R.id.btn_deleteProfile)).perform(scrollTo()).perform(click());
        //The delete dialog appears
        onView(withText("Delete Profile"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        //Perform the action
        onView(withText("Delete")).perform(click());
        //Has expected result
        intended(hasComponent(SplashScreen.class.getName()));
    }
       @Test public void cancel_delete_profile(){
           //Find the view and perform the action

        onView(withId(R.id.btn_deleteProfile)).perform(scrollTo()).perform(click());
           //The delete dialog appears, perform the action
        onView(withText("Cancel")).inRoot(isDialog()).perform(click());
           //The delete dialog appears (UserProfile screen with delete button present is returned
           onView(withId(R.id.btn_deleteProfile)).check(matches(isDisplayed()));

        }



    public static ViewAction forceClick() {
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return allOf(isClickable(), isEnabled(), isDisplayed());
            }

            @Override public String getDescription() {
                return "force click";
            }

            @Override public void perform(UiController uiController, View view) {
                view.performClick(); // perform click without checking view coordinates.
                uiController.loopMainThreadUntilIdle();
            }
        };
    }
}
