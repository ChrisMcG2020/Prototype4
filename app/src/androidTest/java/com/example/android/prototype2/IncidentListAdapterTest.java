package com.example.android.prototype2;

import androidx.test.rule.ActivityTestRule;

import com.example.android.prototype2.views.IncidentListView;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class IncidentListAdapterTest {

    @Rule
    public ActivityTestRule<IncidentListView> mActivityTestRule =
            new ActivityTestRule<>(IncidentListView.class);

    @Test
    public void testRecyclerScrollable() {

    }

    @Test
    public void onBindViewHolder() {
    }
}