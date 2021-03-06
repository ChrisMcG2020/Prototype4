package com.example.android.prototype2.views;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.prototype2.adapters.PlayerListAdapter;
import com.example.android.prototype2.R;
import com.example.android.prototype2.helperClass.PlayerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//Class to update and display information in the recycler_view
public class PlayerListViewActivity extends AppCompatActivity {
    //Define the RecyclerView
   private  RecyclerView listViewPlayers;
    //Define a list to store the players
    private ArrayList<PlayerModel> playersList;
    //Define the reference to the database
    private DatabaseReference reference;

    //Search view bar variable
    private SearchView searchView;

    private DatabaseReference databaseReference;

    //Define the PlayerList Adapter
    private PlayerListAdapter adapter;

    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.users_list_recycler_view);
        
        //Get a reference to the users node of the database
        databaseReference = FirebaseDatabase.getInstance().getReference("Players");


        //Assign the search view variable
        searchView = findViewById(R.id.search_users);
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
        playersList = new ArrayList<>();

        //Assign the recycler view to the correct view
        listViewPlayers = findViewById(R.id.recycler_list_view);
        //Avoid invalidating the whole layout when  adapter contents change
        listViewPlayers.setHasFixedSize(true);
        //Set the adapter to an instance of the PlayerListAdapter

        listViewPlayers.setAdapter(new PlayerListAdapter(this, playersList));
        //Instruct the layout manager to set the layout to LinearLayout
        listViewPlayers.setLayoutManager(new LinearLayoutManager(this));


        //Get a reference to the path required in the database
        reference = FirebaseDatabase.getInstance().getReference("Players");

        //AddValueEventListener will return all the players when called. Order by name
        reference.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot playerSnapshot : snapshot.getChildren()) {
                    //Get the values defined in the PlayerHelperClass from the registered players in the database
                    PlayerModel player = playerSnapshot.getValue(PlayerModel.class);
                    //Add all players to the player list including any newly created
                    playersList.add(player);
                }
                //Add the players to the adapter to be displayed
                adapter = new PlayerListAdapter(PlayerListViewActivity.this, playersList);
                listViewPlayers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Show Error toast to user
                Toast.makeText(PlayerListViewActivity.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

}

