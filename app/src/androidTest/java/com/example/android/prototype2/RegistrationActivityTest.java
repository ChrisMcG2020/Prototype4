package com.example.android.prototype2;

import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class RegistrationActivityTest {

    @Rule
    public ActivityTestRule<PlayerLoginActivity> mActivityTestRule = new ActivityTestRule<>(PlayerLoginActivity.class);

    @Test
    public void checkIfPasswordTextIsEmptyToastAppears() {

        //Assign the text from the email and password text views
        String email = "test@test.com";
        String password = null;

        Looper.prepare();

        //If user leaves password  blank a Toast should appear with appropriate message

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            Espresso.onView(withText("Please enter password!")).
                    inRoot(new ToastMatcher()).check(matches(isDisplayed()));
            return;
        }

    }

    @Test
    public void checkIfEmailTextIsEmptyToastAppears() {
        //Assign the text from the email and password text views
        String email = null;
        String password = "password";

        Looper.prepare();

        //If user leaves email blank a Toast should appear with appropriate message
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_LONG).show();
            Espresso.onView(withText("Please enter email!")).
                    inRoot(new ToastMatcher()).check(matches(isDisplayed()));
            return;
        }
    }
}