package com.example.android.prototype2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RecoveryActivity extends AppCompatActivity {

    //Button variable
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Allows for a smoother transition
        // overridePendingTransition(0, 0);
        //Set layout
        setContentView(R.layout.recovery_advice);


        //Assign the back button
        back = findViewById(R.id.back_to_profile_btn);
        //set onClick to back button to take user back to User profile screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                startActivity(intent);
            }
        });
    }

}

