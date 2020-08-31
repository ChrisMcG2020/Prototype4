package com.example.android.prototype2.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.R;
import com.example.android.prototype2.dialogs.AmbulanceFragment;


public class CoachAdviceActivity extends AppCompatActivity {
    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set layout
        setContentView(R.layout.activity_coach_advice);
    }

    //Method to direct button clicks to correct action
    public void onButtonClicked(View view) {
        //Call ambulance clicked launches the CallAmbulance alert dialog
        if (view.getId() == R.id.coach_advice_Call_ambulance_btn) {
            //Create new instance of the fragment
            AmbulanceFragment callAmbulance = new AmbulanceFragment();
            callAmbulance.setCancelable(false);
            callAmbulance.show(getSupportFragmentManager(), "Fragment Alert Dialog");
        }

        //Continue button clicked returns to Profile
        if (view.getId() == R.id.coach_advice_continue_btn) {
            Intent advice_Intent = new Intent(getApplicationContext(), CoachProfile.class);
            startActivity(advice_Intent);
        }

    }
}


