package com.example.android.prototype2;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.rule.ActivityTestRule;

import com.example.android.prototype2.views.AllIncidentsListView;
import com.example.android.prototype2.views.IncidentListView;
import com.example.android.prototype2.views.PlayerListViewActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RecyclerViewTest {

    // Set the rule to apply to the test method and which class to use
    @Rule
    public ActivityTestRule<IncidentListView> mRecyclerTestRule =
            new ActivityTestRule<>(IncidentListView.class);

    @Test
    public void test_incident_recycler() {
        //Get the activity
        Activity activity = mRecyclerTestRule.getActivity();
        //Find the views and assert they are present
        RecyclerView recycler = activity.findViewById(R.id.recycler_list_view);
        Assert.assertNotNull(recycler);

    }


    // Set the rule to apply to the test method and which class to use
    @Rule
    public ActivityTestRule<AllIncidentsListView> mRecyclerTestRule2 =
            new ActivityTestRule<>(AllIncidentsListView.class);

    @Test
    public void test_all_incidents_recycler() {
        //Get the activity
        Activity activity = mRecyclerTestRule2.getActivity();
        //Find the views and assert they are present
        RecyclerView recycler2 = activity.findViewById(R.id.recycler_all_incident_list_view);
        Assert.assertNotNull(recycler2);

    }

    // Set the rule to apply to the test method and which class to use
    @Rule
    public ActivityTestRule<PlayerListViewActivity> mRecyclerTestRule3 =
            new ActivityTestRule<>(PlayerListViewActivity.class);

    @Test
    public void test_player_view_recycler() {
        //Get the activity
        Activity activity = mRecyclerTestRule3.getActivity();
        //Find the views and assert they are present
        RecyclerView recycler3 = activity.findViewById(R.id.recycler_list_view);
        Assert.assertNotNull(recycler3);
    }

}