package com.example.android.prototype2;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.android.prototype2.views.IncidentListView;
import com.example.android.prototype2.views.SplashScreen;

import org.hamcrest.Matcher;
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
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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
    public void testFields_buttons_images_Present() {
        //Find the views
        Activity activity = mUserProfileTestRule.getActivity();
        EditText name = activity.findViewById(R.id.edit_text_profile_full_name);
        //Assert they are present
        Assert.assertNotNull(name);
        EditText email = activity.findViewById(R.id.edit_text_profile_email);
        Assert.assertNotNull(email);
        EditText phone = activity.findViewById(R.id.edit_text_profile_phone);
        Assert.assertNotNull(phone);
        EditText emergencyC = activity.findViewById(R.id.edit_text_emergency_contact);
        Assert.assertNotNull(emergencyC);
        EditText emergencyC_phone = activity.findViewById(R.id.edit_text_profile_emergency_phone);
        Assert.assertNotNull(emergencyC_phone);

        //Find the views
        Button update = activity.findViewById(R.id.btn_updatePlayerProfile);
        Button logout = activity.findViewById(R.id.btn_logoutProfile);
        Button delete = activity.findViewById(R.id.btn_deleteProfile);
        //Assert they are present
        Assert.assertNotNull(update);
        Assert.assertNotNull(logout);
        Assert.assertNotNull(delete);

        //Find the views
        ImageButton recovery = activity.findViewById(R.id.recovery_image);
        ImageButton incidents = activity.findViewById(R.id.incident_image);
        //Assert they are present
        Assert.assertNotNull(recovery);
        Assert.assertNotNull(incidents);


    }

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
    public void can_edit_user_name() {
        //Find the view and perform action
        onView(withId(R.id.edit_text_profile_full_name)).perform(typeText(TEST_UPDATE_NAME));
        closeSoftKeyboard();

    }

    @Test
    public void can_edit_phone() {
        onView(withId(R.id.edit_text_profile_phone)).perform(typeText(TEST_UPDATE_PHONENO));
        closeSoftKeyboard();

    }

    @Test
    public void can_edit_email() {
        onView(withId(R.id.edit_text_profile_email)).perform(typeText(TEST_UPDATE_EMAIL));
        closeSoftKeyboard();


    }

    @Test
    public void can_edit_emergency_C() {
        onView(withId(R.id.edit_text_emergency_contact)).perform(typeText(TEST_UPDATE_EMERGENCYCONTACT)
                , closeSoftKeyboard());


    }

    @Test
    public void can_edit_contact_phone() {
        onView(withId(R.id.edit_text_profile_emergency_phone)).perform(typeText(TEST_UPDATE_PHONENO));
        closeSoftKeyboard();

        onView(withId(R.id.edit_text_profile_emergency_phone)).perform(forceClick()).perform(typeText(TEST_UPDATE_EC_PHONENO));


    }

    @Test
    public void logout_profile() {
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

    @Test
    public void cancel_delete_profile() {
        //Find the view and perform the action

        onView(withId(R.id.btn_deleteProfile)).perform(scrollTo()).perform(click());
        //The delete dialog appears, perform the action
        onView(withText("Cancel")).inRoot(isDialog()).perform(click());
        //The delete dialog appears (UserProfile screen with delete button present is returned
        onView(withId(R.id.btn_deleteProfile)).check(matches(isDisplayed()));

    }


    //Custom method to force a click when given a co-ordiantion error
    public static ViewAction forceClick() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isClickable(), isEnabled(), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "force click";
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.performClick(); // perform click without checking view coordinates.
                uiController.loopMainThreadUntilIdle();
            }
        };
    }
}
