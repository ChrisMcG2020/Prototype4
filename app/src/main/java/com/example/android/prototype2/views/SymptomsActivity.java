package com.example.android.prototype2.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.R;
import com.example.android.prototype2.dialogs.AmbulanceFragment;

public class SymptomsActivity extends AppCompatActivity {

    //Checkbox variables
    private CheckBox symptom1, symptom2, symptom3, symptom4, symptom5, symptom6, symptom7, symptom8, symptom9, symptom10,
            symptom11, symptom12, symptom13, symptom14, symptom15, symptom16, symptom17, symptom18, symptom19, symptom20;

    //Intent strings for passing intents
    private String intentUid3, intentRedFlag3, intentObservable3, intentEmail3, intentName3;

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

    private void symptomsResult() {
        //Retrieve the previous results and assign them
        intentUid3 = getIntent().getStringExtra("uid2");
        intentName3 = getIntent().getStringExtra("name2");
        Log.d(TAG, "NameSymptoms" + intentName3);
        intentEmail3 = getIntent().getStringExtra("email2");
        intentRedFlag3 = getIntent().getStringExtra("redFlag2");
        intentObservable3 = getIntent().getStringExtra("obs1");

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
            Intent symptomIntent = new Intent(getApplicationContext(), MemoryActivity.class);
            symptomIntent.putExtra("uid3", intentUid3);
            symptomIntent.putExtra("name3", intentName3);
            symptomIntent.putExtra("email3", intentEmail3);
            symptomIntent.putExtra("redFlag3", intentRedFlag3);
            symptomIntent.putExtra("obs3", intentObservable3);
            symptomIntent.putExtra("symptom3", symptomResult);
            //Start the next activity
            startActivity(symptomIntent);
        }
    }
}
