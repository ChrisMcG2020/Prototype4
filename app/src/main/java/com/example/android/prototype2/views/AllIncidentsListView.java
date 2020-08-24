package com.example.android.prototype2.views;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.prototype2.R;
import com.example.android.prototype2.helperClass.AllIncidentsModel;
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
    private RecyclerView listViewAllIncidents;
    private ArrayList<AllIncidentsModel> allIncidentsModel;

    //Define the reference to the database
    private DatabaseReference reference;
    private FirebaseAuth mauth;
    private FirebaseUser mCurrent;

    //Define the PlayerList Adapter
    private AllIncidentsAdapter adapter;

    //Search view bar variable
    private SearchView searchView;


    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_incidents_recycler);


        //Assign the search view variable
        searchView = findViewById(R.id.search_incidents);
        //Set onQueryListener for when text is entered into the search bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            //When text is changed filter the results from the playerlist adapter
            public boolean onQueryTextChange(String newText) {
                //If statement prevents crash due to null pointer exception when device rotated
                if (adapter != null)
                    adapter.getFilter().filter(newText);
                return false;
            }

        });


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
        reference = FirebaseDatabase.getInstance().getReference("Incidents");

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
