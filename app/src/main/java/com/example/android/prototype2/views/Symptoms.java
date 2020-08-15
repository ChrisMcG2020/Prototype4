package com.example.android.prototype2.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.dialogs.AmbulanceFragment;
import com.example.android.prototype2.R;

public class Symptoms extends AppCompatActivity {

    //Checkbox variables
    CheckBox symptom1, symptom2, symptom3, symptom4, symptom5, symptom6, symptom7, symptom8, symptom9, symptom10,
            symptom11, symptom12, symptom13, symptom14, symptom15, symptom16, symptom17, symptom18, symptom19, symptom20;

    //Intent strings for passing intents
    String intent_uid3, intent_RedFlag3, intent_Observable3, intent_email3, intent_name3;

    ////Variable for storing result
    private String symptomResult;

    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide the status bar for this activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Set layout
        setContentView(R.layout.symptoms);

        //Initialise the variables to their corresponding views
        symptom1 = findViewById(R.id.symptom1);
        symptom2 = findViewById(R.id.symptom2);
        symptom3 = findViewById(R.id.symptom3);
        symptom4 = findViewById(R.id.symptom4);
        symptom5 = findViewById(R.id.symptom5);
        symptom6 = findViewById(R.id.symptom6);
        symptom7 = findViewById(R.id.symptom7);
        symptom8 = findViewById(R.id.symptom8);
        symptom9 = findViewById(R.id.symptom9);
        symptom10 = findViewById(R.id.symptom10);
        symptom11 = findViewById(R.id.symptom11);
        symptom12 = findViewById(R.id.symptom12);
        symptom13 = findViewById(R.id.symptom13);
        symptom14 = findViewById(R.id.symptom14);
        symptom15 = findViewById(R.id.symptom15);
        symptom16 = findViewById(R.id.symptom16);
        symptom17 = findViewById(R.id.symptom17);
        symptom18 = findViewById(R.id.symptom18);
        symptom19 = findViewById(R.id.symptom19);
        symptom20 = findViewById(R.id.symptom20);


    }

    public void symptomsResult() {
        //Retrieve the previous results and assign them
        intent_uid3 = getIntent().getStringExtra("uid2");
        intent_name3=getIntent().getStringExtra("name2");
        Log.d(TAG,"NameSymptoms"+intent_name3);
        intent_email3 = getIntent().getStringExtra("email2");
        intent_RedFlag3 = getIntent().getStringExtra("redFlag2");
        intent_Observable3 = getIntent().getStringExtra("obs1");

        //If any of the checkboxes result is "One or more symptoms present"
        if (symptom1.isChecked() || symptom2.isChecked() || symptom3.isChecked() || symptom4.isChecked() || symptom5.isChecked() || symptom6.isChecked() || symptom7.isChecked() || symptom8.isChecked() ||
                symptom9.isChecked() || symptom10.isChecked() || symptom11.isChecked() || symptom12.isChecked() || symptom13.isChecked() || symptom14.isChecked() || symptom15.isChecked() || symptom16.isChecked() || symptom17.isChecked() ||
                symptom18.isChecked() || symptom19.isChecked() || symptom20.isChecked()) {
            symptomResult = "One or more symptoms present";

        } else {
            //Otherwise
            symptomResult = "No symptoms";
        }

    }

    //Method to direct button clicks to correct action
    public void onButtonClicked(View view) {
        //If call ambulance clicked then dialog fragment is launched
        if (view.getId() == R.id.symptom_call_ambulance) {
            //Create new instance of the call ambulance fragment
            AmbulanceFragment callAmbulance = new AmbulanceFragment();
            //setCancelable set to false to make sure user chooses one of the options
            callAmbulance.setCancelable(false);
            callAmbulance.show(getSupportFragmentManager(), "Fragment Alert Dialog");

        }
        //If continue is clicked
        if (view.getId() == R.id.symptom_continue) {
            //Run the symptoms method
            symptomsResult();
            //Pass the intents to the next activity
            Intent symptom_Intent = new Intent(getApplicationContext(), MemoryActivity.class);
            symptom_Intent.putExtra("uid3", intent_uid3);
            symptom_Intent.putExtra("name3", intent_name3);
            symptom_Intent.putExtra("email3", intent_email3);
            symptom_Intent.putExtra("redFlag3", intent_RedFlag3);
            symptom_Intent.putExtra("obs3", intent_Observable3);
            symptom_Intent.putExtra("symptom3", symptomResult);
            //Start the next activity
            startActivity(symptom_Intent);
        }
    }
}
