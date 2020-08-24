package com.example.android.prototype2.views;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.R;
import com.example.android.prototype2.dialogs.AlertLoginFragment;

public class SplashScreen extends AppCompatActivity {

    private boolean mIsDialogShown = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide the status bar for this activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Set layout
        setContentView(R.layout.splash_screen);
    }


    //Method for showing the login dialog
    protected void showDialog() {
        mIsDialogShown = true;
        //Create a new instance of the fragment
        AlertLoginFragment alertLogin = new AlertLoginFragment();
        //User must pick one of the choices cannot cancel by clicking outside of dialog
        alertLogin.setCancelable(false);

        alertLogin.show(getSupportFragmentManager(), "Fragment Alert Dialog");
    }

    //Will show the dialog on start and any time the main screen is navigated back to
    @Override
    protected void onResume() {
        super.onResume();
        // show dialog here
        showDialog();
    }
}
