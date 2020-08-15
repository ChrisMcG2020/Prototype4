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

public class IncidentListAdapterTest {

    @Rule
    public ActivityTestRule<IncidentListView> mRecyclerTestRule =
            new ActivityTestRule<>(IncidentListView.class);

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void test_incident_recycler() {


        Activity activity = mRecyclerTestRule.getActivity();
        RecyclerView recycler = activity.findViewById(R.id.recycler_list_view);
        Assert.assertNotNull(recycler);


    }


    @Rule
    public ActivityTestRule<AllIncidentsListView> mRecyclerTestRule2 =
            new ActivityTestRule<>(AllIncidentsListView.class);

    @Test
    public void test_all_incidents_recycler() {
        Activity activity = mRecyclerTestRule2.getActivity();
        RecyclerView recycler2 = activity.findViewById(R.id.recycler_all_incident_list_view);
        Assert.assertNotNull(recycler2);

    }

    @Rule
    public ActivityTestRule<PlayerListViewActivity> mRecyclerTestRule3 =
            new ActivityTestRule<>(PlayerListViewActivity.class);

    @Test
    public void test_player_view_recycler(){
        Activity activity = mRecyclerTestRule3.getActivity();
        RecyclerView recycler3 = activity.findViewById(R.id.recycler_list_view);
        Assert.assertNotNull(recycler3);
    }
    @After
    public void tearDown() throws Exception {

    }
}