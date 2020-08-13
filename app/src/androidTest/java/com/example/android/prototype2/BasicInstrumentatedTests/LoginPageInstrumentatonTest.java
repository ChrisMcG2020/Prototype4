package com.example.android.prototype2.BasicInstrumentatedTests;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import com.example.android.prototype2.PlayerLoginActivity;
import com.example.android.prototype2.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginPageInstrumentatonTest {

    @Rule
    public ActivityTestRule<PlayerLoginActivity> mActivityTestRule =
            new ActivityTestRule<>(PlayerLoginActivity.class);

    @Before
    public void setUp() throws Exception {


    }

   @Test
   public void loginAndPasswordFields(){
        Activity activity = mActivityTestRule.getActivity();
        EditText email=activity.findViewById(R.id.login_email2);
        Assert.assertNotNull(email);
        EditText pass=activity.findViewById(R.id.login_password2);
        Assert.assertNotNull(pass);

    }
  @Test
  public void loginAndRegButtons(){
        Activity activity = mActivityTestRule.getActivity();
        Button login=activity.findViewById(R.id.login);
        Assert.assertNotNull(login);
        Button register=activity.findViewById(R.id.player_reg_btn);
        Assert.assertNotNull(register);
        Button forgotP=activity.findViewById(R.id.forgot_pass_btn);
        Assert.assertNotNull(forgotP);
    }
    @After
    public void tearDown() throws Exception {

    }
}

