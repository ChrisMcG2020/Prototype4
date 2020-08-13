package com.example.android.prototype2.views;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.prototype2.dialogs.AlertDialogFragment;
import com.example.android.prototype2.helperClass.UserHelperClass;
import com.example.android.prototype2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//Class to update and display information in the recycler_view
public class PlayerListViewActivity extends AppCompatActivity {
    //Define the RecyclerView
    RecyclerView listViewPlayers;
    //Define a list to store the players
    ArrayList<UserHelperClass> playersList;
    //Define the reference to the database
    DatabaseReference reference;

    //Search view bar variable
    private SearchView searchView;


    //Initialise dialog to be shown to be true
    private boolean mIsDialogShown = true;


    private DatabaseReference databaseReference;

    //Define the PlayerList Adapter
    PlayerListAdapter adapter;


    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.users_list_recycler_view);

        //Show the medical warning dialog
        showDialog();

        //Get a reference to the users node of the database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

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
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        //Assign the playersList
        playersList = new ArrayList<>();

        //Assign the recycler view to the correct view
        listViewPlayers = findViewById(R.id.recycler_list_view);
        listViewPlayers.setHasFixedSize(true);
        //Set the adapter to an instance of the PlayerListAdapter

        listViewPlayers.setAdapter(new PlayerListAdapter(this, playersList));
        //Instruct the layout manager to set the layout to LinearLayout
        listViewPlayers.setLayoutManager(new LinearLayoutManager(this));


        //Get a reference to the path required in the database
        reference = FirebaseDatabase.getInstance().getReference("Users");

        //AddValueEventListener will update the players list if any new players added
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot playerSnapshot : snapshot.getChildren()) {
                    //Get the values defined in the UserHelperClass from the registered players in the database
                    UserHelperClass player = playerSnapshot.getValue(UserHelperClass.class);
                    //If a new player is created add them to the playersList
                    playersList.add(player);
                }
                //What does this do??
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

    //Implement the medical warning  alert to the user
    public void showDialog() {
        //Show the dialog
        mIsDialogShown = true;
        // Create an instance of the alert dialog fragment and show it
        AlertDialogFragment medicalAlert = new AlertDialogFragment();
        //User can only close dialog by choosing either option
        medicalAlert.setCancelable(false);
        //Use the Fragment manager to show the alert
        medicalAlert.show(getSupportFragmentManager(), "Fragment Medical Alert Dialog");

    }
        @Override
        public void onResume() {
            //Assign the search view variable
            super.onResume();
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
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });


        }
      @Override
      public void onRestart() {

          super.onRestart();

          //Assign the search view variable
          super.onResume();
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
                  adapter.getFilter().filter(newText);
                  return false;
              }
          });

      }
}
