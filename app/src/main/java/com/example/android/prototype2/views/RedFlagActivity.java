package com.example.android.prototype2.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.dialogs.AmbulanceFragment;
import com.example.android.prototype2.helperClass.PlayerIncidentsModel;
import com.example.android.prototype2.R;

public class RedFlagActivity extends AppCompatActivity {


    //Variable for storing result
    private String redFlagDiagnosis;

    //Create a UID for the intent
    String intent_uid1, intent_Email1, intent_Name1;

    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide the status bar for this activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Set layout
        setContentView(R.layout.activity_red_flag);

    }


    //Method to diagnose the red flag activity and set it in the database
    protected void redFlagDiagnosis() {
        intent_uid1 = getIntent().getStringExtra("uid");
        intent_Name1=getIntent().getStringExtra("name1");
        intent_Email1 = getIntent().getStringExtra("email1");
        redFlagDiagnosis = "Red Flags : Passed";
        PlayerIncidentsModel mPlayerIncidentsModel = new PlayerIncidentsModel();
        mPlayerIncidentsModel.setRed_FLag_Test(redFlagDiagnosis);



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
            redFlag_Intent.putExtra("uid1", intent_uid1);
            redFlag_Intent.putExtra("name1",intent_Name1);
            Log.d(TAG,"NAME+_+_+_"+intent_Name1);
            redFlag_Intent.putExtra("email1", intent_Email1);
            redFlag_Intent.putExtra("redFlag1", redFlagDiagnosis);
            //Start the next activity
            startActivity(redFlag_Intent);
        }
    }
}
