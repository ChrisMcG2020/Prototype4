package com.example.android.prototype2.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.prototype2.helperClass.AllIncidentsModel;
import com.example.android.prototype2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllIncidentsListView extends AppCompatActivity {

    //Define the RecyclerView
    RecyclerView listViewAllIncidents;
    ArrayList<AllIncidentsModel> allIncidentsModel;

    //Define the reference to the database
    DatabaseReference reference;
    FirebaseAuth mauth;
    FirebaseUser mCurrent;

    //Define the PlayerList Adapter
    AllIncidentsAdapter adapter;


    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_incidents_recycler);

        //Assign the playersList
        allIncidentsModel = new ArrayList<>();

        //Assign the recycler view to the correct view
        listViewAllIncidents = findViewById(R.id.recycler_all_incident_list_view);
        listViewAllIncidents.setHasFixedSize(true);
        //Set the adapter to an instance of the PlayerListAdapter
        listViewAllIncidents.setAdapter(new AllIncidentsAdapter(this, allIncidentsModel));
        //Instruct the layout manager to set the layout to LinearLayout
        listViewAllIncidents.setLayoutManager(new LinearLayoutManager(this));


        mauth = FirebaseAuth.getInstance();
        mCurrent = mauth.getCurrentUser();
        //Get a reference to the path required in the database
        reference = FirebaseDatabase.getInstance().getReference("Incidents").child("Player_incidents");

        //AddValueEventListener will update the players list if any new players added
        reference.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot incidentSnapshot : snapshot.getChildren()) {
                    //Get the values defined in the UserHelperClass from the registered players in the database
                    AllIncidentsModel incident = incidentSnapshot.getValue(AllIncidentsModel.class);
                    //If a new player is created add them to the playersList
                    allIncidentsModel.add(incident);
                }

                adapter = new AllIncidentsAdapter(AllIncidentsListView.this, allIncidentsModel);
                listViewAllIncidents.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Show Error toast to user
                Toast.makeText(AllIncidentsListView.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });
    }
}
