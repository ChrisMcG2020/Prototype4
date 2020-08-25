package com.example.android.prototype2;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.android.prototype2.views.AllIncidentsListView;
import com.example.android.prototype2.views.AppInformationPage;
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
import static com.example.android.prototype2.PlayerProfileTest.TEST_UPDATE_EMAIL;
import static com.example.android.prototype2.PlayerProfileTest.TEST_UPDATE_NAME;
import static com.example.android.prototype2.PlayerProfileTest.TEST_UPDATE_PHONENO;

public class CoachProfileTest {
    //Set the rule to apply to the test method and which class to use
    @Rule
    public final IntentsTestRule<CoachProfile> mCoachProfileTestRule
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
        Button update = activity.findViewById(R.id.updateCoachProfile_btn);
        Button delete = activity.findViewById(R.id.deleteCoachProfile_btn);
        Button diagnose = activity.findViewById(R.id.diagnose_concussion_button);
        ImageView info = activity.findViewById(R.id.coach_info_btn);
        //Assert they are present
        Assert.assertNotNull(update);
        Assert.assertNotNull(delete);
        Assert.assertNotNull(diagnose);
        Assert.assertNotNull(info);

        //Find the views
        ImageView playerSearch = activity.findViewById(R.id.player_search);
        ImageView history = activity.findViewById(R.id.history_image);
        ImageView signOut = activity.findViewById(R.id.coach_sign_out_image);
        //Assert they are present
        Assert.assertNotNull(playerSearch);
        Assert.assertNotNull(history);
        Assert.assertNotNull(signOut);


    }

    @Test
    public void test_click_diagnose_button() {
        //Find the views and perform action
        onView(withId(R.id.diagnose_concussion_button)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(PlayerListViewActivity.class.getName()));


    }

    @Test
    public void test_click_on_playerView() {
        //Find the views and perform action
        onView(withId(R.id.player_search)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(PlayerListViewActivity.class.getName()));

    }

    @Test
    public void test_on_calender_pic_opens_report() {
        //Find the views and perform action
        onView(withId(R.id.history_image)).perform(click());
        //Check if action returns desired outcome
        intended(hasComponent(AllIncidentsListView.class.getName()));
    }

    @Test
    public void test_can_edit_name() {
        //Find the views and perform action
        onView(withId(R.id.coach_profile_name)).perform(typeText(TEST_UPDATE_NAME));
        closeSoftKeyboard();

    }

    @Test
    public void test_can_edit_phone() {
        //Find the views and perform action
        onView(withId(R.id.coach_profile_phone)).perform(typeText(TEST_UPDATE_PHONENO));
        closeSoftKeyboard();

    }

    @Test
    public void test_can_edit_email() {
        //Find the views and perform action
        onView(withId(R.id.coach_profile_email)).perform(typeText(TEST_UPDATE_EMAIL));
        closeSoftKeyboard();


    }

    @Test
    public void test_can_edit_team() {
        //Find the views and perform action
        onView(withId(R.id.coach_profile_team)).perform(scrollTo()).perform(typeText("Finn Harps")
                , closeSoftKeyboard());


    }


    @Test
    public void test_logout_profile() {
        //Find the view and perform the action
        onView(withId(R.id.coach_sign_out_image)).perform(scrollTo()).perform(click());
        //Expected result
        intended(hasComponent(SplashScreen.class.getName()));

    }

    @Test
    public void test_delete_profile() {
        //Find the view and perform the action
        onView(withId(R.id.deleteCoachProfile_btn)).perform(scrollTo()).perform(click());
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
    public void test_cancel_delete_profile() {
        //Find the view and perform the action
        onView(withId(R.id.deleteCoachProfile_btn)).perform(scrollTo()).perform(click());
        //The delete dialog appears, perform the action
        onView(withText("Cancel")).inRoot(isDialog()).perform(click());
        //Check if action returns desired outcome
        onView(withId(R.id.deleteCoachProfile_btn)).check(matches(isDisplayed()));

    }

    @Test
    public void test_info_button_opens_page() {
        //Find the view and perform the action
        onView(withId(R.id.coach_info_btn)).perform(click());
        //Expected result
        intended(hasComponent(AppInformationPage.class.getName()));

    }
}
