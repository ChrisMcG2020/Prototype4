package com.example.android.prototype2.views;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.prototype2.adapters.AllIncidentsAdapter;
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

//Class to update and display information in the recycler_view
public class AllIncidentsListView extends AppCompatActivity {

    //Define the RecyclerView
    private RecyclerView listViewAllIncidents;
    private ArrayList<AllIncidentsModel> allIncidentsModel;

    //Define the reference to the database
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

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


        //Assign the incidents List
        allIncidentsModel = new ArrayList<>();

        //Assign the recycler view to the correct view
        listViewAllIncidents = findViewById(R.id.recycler_all_incident_list_view);
        //Avoid invalidating the whole layout when  adapter contents change
        listViewAllIncidents.setHasFixedSize(true);
        //Set the adapter to an instance of the AllIncidentsAdapter
        listViewAllIncidents.setAdapter(new AllIncidentsAdapter(this, allIncidentsModel));
        //Instruct the layout manager to set the layout to LinearLayout
        listViewAllIncidents.setLayoutManager(new LinearLayoutManager(this));


        //Assign the firebase Auth to get the current user
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        //Get a reference to the path required in the database
        reference = FirebaseDatabase.getInstance().getReference("Incidents");

        //AddValueEventListener will return the all incidents when called. Displayed in order of date.
        reference.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot incidentSnapshot : snapshot.getChildren()) {
                    //Get the values defined in the AllIncidentsModel from the incidents in the database
                    AllIncidentsModel incident = incidentSnapshot.getValue(AllIncidentsModel.class);
                    //Add the incidents to the list including any newly created
                    allIncidentsModel.add(incident);
                }
                //Add the incidents to the adapter to be displayed
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
