package com.example.android.prototype2;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.example.android.prototype2.R;
import com.example.android.prototype2.RecoveryActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class RecoveryAdvice {

    // Set the rule to apply to the test method and which class to use
    @Rule
    public ActivityTestRule<RecoveryActivity> mActivityTestRule =
            new ActivityTestRule<>(RecoveryActivity.class);


    @Test
    public void testTextViews(){
        //Get the activity
        Activity activity = mActivityTestRule.getActivity();
        //Find the views and assert they are present
        TextView warning=activity.findViewById(R.id.recovery_caution);
        Assert.assertNotNull(warning);
        TextView tv1=activity.findViewById(R.id.return_to_sport1);
        Assert.assertNotNull(tv1);
        TextView tv2=activity.findViewById(R.id.return_to_sport2);
        Assert.assertNotNull(tv2);
        TextView tv3=activity.findViewById(R.id.return_to_sport3);
        Assert.assertNotNull(tv3);
        TextView tv4=activity.findViewById(R.id.return_to_sport4);
        Assert.assertNotNull(tv4);
        TextView tv5=activity.findViewById(R.id.return_to_sport5);
        Assert.assertNotNull(tv5);
        TextView tv6=activity.findViewById(R.id.return_to_sport6);
        Assert.assertNotNull(tv6);

    }
    @Test
    public void testButton(){
        //Get the activity
        Activity activity = mActivityTestRule.getActivity();
        //Find the views and assert they are present
        Button profile=activity.findViewById(R.id.back_to_profile_btn);
        Assert.assertNotNull(profile);

    }

}