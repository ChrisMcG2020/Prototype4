package com.example.android.prototype2;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.android.prototype2.views.AllIncidentsListView;
import com.example.android.prototype2.views.IncidentListView;
import com.example.android.prototype2.views.PlayerListViewActivity;
import com.example.android.prototype2.views.SplashScreen;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.prototype2.UserProfileTest.TEST_UPDATE_EMAIL;
import static com.example.android.prototype2.UserProfileTest.TEST_UPDATE_NAME;
import static com.example.android.prototype2.UserProfileTest.TEST_UPDATE_PHONENO;

public class CoachProfileTest {


    @Rule
    public IntentsTestRule<CoachProfile> mCoachProfileTestRule
            = new IntentsTestRule<>(CoachProfile.class);




    @Test
    public void testRegFieldsPresent() {
        //Find the views
        Activity activity = mCoachProfileTestRule.getActivity();
        EditText name = activity.findViewById(R.id.edit_text_profile_coach_name);
        //Assert they are present
        Assert.assertNotNull(name);
        EditText email = activity.findViewById(R.id.edit_text_coach_profile_email);
        Assert.assertNotNull(email);
        EditText phone = activity.findViewById(R.id.edit_text_coach_profile_phone);
        Assert.assertNotNull(phone);
        EditText team = activity.findViewById(R.id.edit_text_coach_team_coached);
        Assert.assertNotNull(team);


        //Find the views
        Button update=activity.findViewById(R.id.btn_updateCoachProfile);
        Button logout=activity.findViewById(R.id.btn_logoutCoachProfile);
        Button delete=activity.findViewById(R.id.btn_deleteCoachProfile);
        Button diagnose=activity.findViewById(R.id.diagnose_concussion_button);
        //Assert they are present
        Assert.assertNotNull(update);
        Assert.assertNotNull(logout);
        Assert.assertNotNull(delete);
        Assert.assertNotNull(diagnose);

    }
    @Test
    public void click_diagnose_button(){
        //Find the view and perform action
        onView(withId(R.id.diagnose_concussion_button)).perform(click());
        //Check if view does what it should
        intended(hasComponent(PlayerListViewActivity.class.getName()));


    }
    @Test
    public void click_on_playerView() {
        //Find the view and perform action
        onView(withId(R.id.player_info_pic)).perform(click());
        //Check if view does what it should
        intended(hasComponent(PlayerListViewActivity.class.getName()));

    }

    @Test
    public void on_Incidents_pic_opens_report() {
        //Find the view and perform action
        onView(withId(R.id.history_image)).perform(click());
        //Check if view does what it should
        intended(hasComponent(AllIncidentsListView.class.getName()));
    }

    @Test
    public void can_edit_user_name() {
        //Find the view and perform action
        onView(withId(R.id.edit_text_profile_coach_name)).perform(typeText(TEST_UPDATE_NAME));
        closeSoftKeyboard();

    }

    @Test
    public void can_edit_phone() {
        onView(withId(R.id.edit_text_coach_profile_phone)).perform(typeText(TEST_UPDATE_PHONENO));
        closeSoftKeyboard();

    }

    @Test
    public void can_edit_email() {
        onView(withId(R.id.edit_text_coach_profile_email)).perform(typeText(TEST_UPDATE_EMAIL));
        closeSoftKeyboard();


    }

    @Test
    public void can_edit_emergency_C() {
        onView(withId(R.id.edit_text_coach_team_coached)).perform(typeText("Finn Harps")
                ,closeSoftKeyboard());


    }


    @Test
    public void logout_profile(){
        //Find the view and perform the action
        onView(withId(R.id.btn_logoutCoachProfile)).perform(scrollTo()).perform(click());
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
        //Has expected result
        intended(hasComponent(SplashScreen.class.getName()));
    }
    @Test public void cancel_delete_profile(){
        //Find the view and perform the action

        onView(withId(R.id.btn_deleteCoachProfile)).perform(scrollTo()).perform(click());
        //The delete dialog appears, perform the action
        onView(withText("Cancel")).inRoot(isDialog()).perform(click());
        //The delete dialog appears (UserProfile screen with delete button present is returned
        onView(withId(R.id.btn_deleteCoachProfile)).check(matches(isDisplayed()));

    }
}
