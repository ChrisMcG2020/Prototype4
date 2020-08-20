package com.example.android.prototype2;

import android.app.Activity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.android.prototype2.views.AllIncidentsListView;
import com.example.android.prototype2.views.PlayerListViewActivity;
import com.example.android.prototype2.views.SplashScreen;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.prototype2.PlayerRegistrationTest.INVALID_EMAIL;
import static com.example.android.prototype2.PlayerRegistrationTest.PHONE_FORMAT_ERROR;
import static com.example.android.prototype2.PlayerRegistrationTest.TEST_EMAIL;
import static com.example.android.prototype2.PlayerRegistrationTest.TEST_NAME;
import static com.example.android.prototype2.PlayerRegistrationTest.hasTextInputLayoutErrorText;
import static com.example.android.prototype2.UserProfileTest.TEST_UPDATE_EMAIL;
import static com.example.android.prototype2.UserProfileTest.TEST_UPDATE_NAME;
import static com.example.android.prototype2.UserProfileTest.TEST_UPDATE_PHONENO;

public class CoachProfileTest {
    //Set the rule to apply to the test method and which class to use
    @Rule
    public IntentsTestRule<CoachProfile> mCoachProfileTestRule
            = new IntentsTestRule<>(CoachProfile.class);


    @Test
    public void testRegFieldsPresent() {
        //Find the views and assert they are present
        Activity activity = mCoachProfileTestRule.getActivity();
        EditText name = activity.findViewById(R.id.coach_profile_name);
        Assert.assertNotNull(name);
        EditText email = activity.findViewById(R.id.coach_profile_email);
        Assert.assertNotNull(email);
        EditText phone = activity.findViewById(R.id.coach_profile_phone);
        Assert.assertNotNull(phone);
        EditText team = activity.findViewById(R.id.coach_profile_team);
        Assert.assertNotNull(team);


        //Find the views
        Button update = activity.findViewById(R.id.btn_updateCoachProfile);
        Button delete = activity.findViewById(R.id.btn_deleteCoachProfile);
        Button diagnose = activity.findViewById(R.id.diagnose_concussion_button);
        //Assert they are present
        Assert.assertNotNull(update);
        Assert.assertNotNull(delete);
        Assert.assertNotNull(diagnose);

        //Find the views
        ImageView playerSearch=activity.findViewById(R.id.player_search);
        ImageView history=activity.findViewById(R.id.history_image);
        ImageView signOut=activity.findViewById(R.id.coach_sign_out_image);
        //Assert they are present
        Assert.assertNotNull(playerSearch);
        Assert.assertNotNull(history);
        Assert.assertNotNull(signOut);


    }

    @Test
    public void click_diagnose_button() {
        //Find the views and perform action
        onView(withId(R.id.diagnose_concussion_button)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(PlayerListViewActivity.class.getName()));


    }

    @Test
    public void click_on_playerView() {
        //Find the views and perform action
        onView(withId(R.id.player_search)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(PlayerListViewActivity.class.getName()));

    }

    @Test
    public void on_calender_pic_opens_report() {
        //Find the views and perform action
        onView(withId(R.id.history_image)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(AllIncidentsListView.class.getName()));
    }

    @Test
    public void can_edit_name() {
        //Find the views and perform action
        onView(withId(R.id.coach_profile_name)).perform(typeText(TEST_UPDATE_NAME));
        closeSoftKeyboard();

    }

    @Test
    public void can_edit_phone() {
        //Find the views and perform action
        onView(withId(R.id.coach_profile_phone)).perform(typeText(TEST_UPDATE_PHONENO));
        closeSoftKeyboard();

    }

    @Test
    public void can_edit_email() {
        //Find the views and perform action
        onView(withId(R.id.coach_profile_email)).perform(typeText(TEST_UPDATE_EMAIL));
        closeSoftKeyboard();


    }

    @Test
    public void can_edit_team() {
        //Find the views and perform action
        onView(withId(R.id.coach_profile_team)).perform(scrollTo()).perform(typeText("Finn Harps")
                , closeSoftKeyboard());


    }


    @Test
    public void logout_profile() {
        //Find the view and perform the action
        onView(withId(R.id.coach_sign_out_image)).perform(scrollTo()).perform(click());
        //
        intended(hasComponent(SplashScreen.class.getName()));

    }

    @Test
    public void delete_profile() {
        //Find the view and perform the action
        onView(withId(R.id.btn_deleteCoachProfile)).perform(scrollTo()).perform(click());
        //The delete dialog appears
        onView(withText("Delete Profile"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        //Perform the action
        onView(withText("Delete")).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(SplashScreen.class.getName()));
    }

    @Test
    public void cancel_delete_profile() {
        //Find the view and perform the action
        onView(withId(R.id.btn_deleteCoachProfile)).perform(scrollTo()).perform(click());
        //The delete dialog appears, perform the action
        onView(withText("Cancel")).inRoot(isDialog()).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.btn_deleteCoachProfile)).check(matches(isDisplayed()));

    }
}
