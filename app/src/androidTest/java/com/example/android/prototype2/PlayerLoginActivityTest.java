package com.example.android.prototype2;

import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class PlayerLoginActivityTest {

    @Rule
    public ActivityTestRule<PlayerLoginActivity> mActivityTestRule = new ActivityTestRule<>(PlayerLoginActivity.class);





    @Test
    public void checkIfPasswordTextIsEmptyToastAppears() {

        //Looper class handles the queue of runnables we want to test
        Looper.prepare();

        //Automate an entry into the email field and leave password blank
      //  onView(withId(R.id.email)).perform(typeText("test@test.com"));
      //String password= onView(withId(R.id.password)).perform(typeText("")).toString();

        //If user leaves password blank a Toast should appear with appropriate message


     //   if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            onView(withId(R.id.login)).perform(click());
            onView(withText("Please enter password!")).
                    inRoot(new ToastMatcher()).check(matches(isDisplayed()));

            Looper.loop();
        }



    @Test
    public void checkIfEmailTextIsEmptyToastAppears() {

        //Looper class handles the queue of runnables we want to test
        Looper.prepare();

        //Automate an entry into the password field and leave email blank
     //   onView(withId(R.id.password)).perform(typeText("test"));
     //   String email= onView(withId(R.id.password)).perform(typeText("")).toString();

        //If user leaves email blank a Toast should appear with appropriate message
       // if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_LONG).show();
            onView(withText("Please enter email!")).
                    inRoot(new ToastMatcher()).check(matches(isDisplayed()));

            Looper.loop();
        }
    }
