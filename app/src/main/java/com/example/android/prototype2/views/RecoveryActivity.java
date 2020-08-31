package com.example.android.prototype2.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.R;

public class RecoveryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set layout
        setContentView(R.layout.recovery_advice);

    }

    //Method to direct button clicks to correct action
    public void onButtonClicked(View view) {
        //Back button clicked launches the player profile alert dialog
        if (view.getId() == R.id.recovery_back_btn) {
            Intent back = new Intent(getApplicationContext(), PlayerProfile.class);
            startActivity(back);
        }
        //Return to sport button clicked launches Return To Sport page
        if (view.getId() == R.id.return_to_sport_btn) {
            Intent returnToSport = new Intent(getApplicationContext(), ReturnToSportStrategy.class);
            startActivity(returnToSport);
        }
    }
}
