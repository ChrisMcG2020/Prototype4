package com.example.android.prototype2.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.Dialogs.AmbulanceFragment;
import com.example.android.prototype2.R;

public class ObservableSignsActivity extends AppCompatActivity {

    //Checkbox variables
    CheckBox signs1, signs2, signs3, signs4, signs5;
    //Intent strings for passing intents
    String intent_RedFlag2, intent_uid2, intent_email2, intent_name2;

    //Variable for storing result
    private String obsDiagnosis;


    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide the status bar for this activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Set layout
        setContentView(R.layout.activity_observable_signs);


        //Initialise the variables to their corresponding views
        signs1 = findViewById(R.id.cb_ob_signs1);
        signs2 = findViewById(R.id.cb_ob_signs2);
        signs3 = findViewById(R.id.cb_ob_signs3);
        signs4 = findViewById(R.id.cb_ob_signs4);
        signs5 = findViewById(R.id.cb_ob_signs5);

    }

    public void obsDiagnose() {

        //Retrieve the previous results and assign them
        intent_uid2 = getIntent().getStringExtra("uid1");
        intent_name2 = getIntent().getStringExtra("name1");
        intent_email2 = getIntent().getStringExtra("email1");
        intent_RedFlag2 = getIntent().getStringExtra("redFlag1");


        //if any of the five signs are checked , results in a fail
        if (signs1.isChecked() || signs2.isChecked() || signs3.isChecked() || signs4.isChecked() || signs5.isChecked()) {
            obsDiagnosis = "Observable Signs: Failed";


        } else {
            //If pass recorded save result
            obsDiagnosis = "Observable Signs: Passed";


        }

    }

    //Method to direct button clicks to correct action
    public void onButtonClicked(View view) {
        //If call ambulance clicked then dialog fragment is launched
        if (view.getId() == R.id.os_call_ambulance) {
            AmbulanceFragment callAmbulance = new AmbulanceFragment();
            callAmbulance.setCancelable(false);
            callAmbulance.show(getSupportFragmentManager(), "Fragment Alert Dialog");
        }
        ///If continue clicked run method and pass intents to next activity
        else if (view.getId() == R.id.os_continue) {
            //Run the observable signs diagnosis method
            obsDiagnose();
            //Pass the intents to the next activity
            Intent obs_Intent = new Intent(getApplicationContext(), Symptoms.class);
            obs_Intent.putExtra("uid2", intent_uid2);
            obs_Intent.putExtra("name2", intent_name2);

            obs_Intent.putExtra("email2", intent_email2);
            obs_Intent.putExtra("redFlag2", intent_RedFlag2);
            obs_Intent.putExtra("obs1", obsDiagnosis);
            //Start the next activity
            startActivity(obs_Intent);
        }
    }
}
