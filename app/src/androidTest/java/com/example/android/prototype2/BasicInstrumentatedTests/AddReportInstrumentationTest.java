package com.example.android.prototype2.BasicInstrumentatedTests;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.android.prototype2.AddReportActivity;
import com.example.android.prototype2.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddReportInstrumentationTest {

    @Rule
    public ActivityTestRule<AddReportActivity> mActivityTestRule =
            new ActivityTestRule<>(AddReportActivity.class);


    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void testIsEditTextPresent() {
        Activity activity = mActivityTestRule.getActivity();
        EditText edit = activity.findViewById(R.id.report_edit_text);
        Assert.assertNotNull(edit);
    }

    @Test
    public void testIsContinueButtonPresent(){
        Activity activity = mActivityTestRule.getActivity();
        Button button= activity.findViewById(R.id.report_continue);
        Assert.assertNotNull(button);
    }
    @After
    public void tearDown() throws Exception {

    }
}

