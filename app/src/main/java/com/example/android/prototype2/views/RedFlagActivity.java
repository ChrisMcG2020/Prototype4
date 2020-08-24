package com.example.android.prototype2.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.R;
import com.example.android.prototype2.dialogs.AlertDialogFragment;
import com.example.android.prototype2.dialogs.AmbulanceFragment;

public class RedFlagActivity extends AppCompatActivity {


    //Variable for storing result
    private String redFlagDiagnosis;

    //Create a UID for the intent
    private String intentUid1, intentEmail1, intentName1;


    //Initialise dialog to be shown to be true
    private boolean mIsDialogShown = true;

    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide the status bar for this activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Set layout
        setContentView(R.layout.activity_red_flag);
        showDialog();

    }


    //Method to diagnose the red flag activity and set it in the database
    protected void redFlagDiagnosis() {
        intentUid1 = getIntent().getStringExtra("uid");
        intentName1 = getIntent().getStringExtra("name1");
        intentEmail1 = getIntent().getStringExtra("email1");
        redFlagDiagnosis = "Red Flags : Passed";

    }

    //Method to direct button clicks to correct action
    public void onButtonClicked(View view) {
        if (view.getId() == R.id.redFlag_call_ambulance) {
            //Create new instance of the call ambulance fragment
            AmbulanceFragment callAmbulance = new AmbulanceFragment();
            //setCancelable set to false to make sure user chooses one of the options
            callAmbulance.setCancelable(false);
            callAmbulance.show(getSupportFragmentManager(), "Fragment Alert Dialog");


            //If continue clicked run method and pass intents to next activity
        } else if (view.getId() == R.id.redFlag_continue) {
            redFlagDiagnosis();
            Intent redFlag_Intent = new Intent(getApplicationContext(), ObservableSignsActivity.class);
            redFlag_Intent.putExtra("uid1", intentUid1);
            redFlag_Intent.putExtra("name1", intentName1);
            redFlag_Intent.putExtra("email1", intentEmail1);
            redFlag_Intent.putExtra("redFlag1", redFlagDiagnosis);
            //Start the next activity
            startActivity(redFlag_Intent);
        }
    }
    //Implement the medical warning  alert to the user
    protected void showDialog() {
        //Show the dialog
        mIsDialogShown = true;
        // Create an instance of the alert dialog fragment and show it
        AlertDialogFragment medicalAlert = new AlertDialogFragment();
        //User can only close dialog by choosing option
        medicalAlert.setCancelable(false);
        //Use the Fragment manager to show the alert
        medicalAlert.show(getSupportFragmentManager(), "Fragment Medical Alert Dialog");

    }
}
