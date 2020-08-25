package com.example.android.prototype2;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlayerLoginActivity extends AppCompatActivity {


    //Declare the variables
    private TextInputLayout loginEmail, loginPass;
    private Button forgotPass;

    //Firebase variables
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    //Progress bar variable
    private ProgressBar progressBar;

    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set layout
        setContentView(R.layout.activity_player_login);

        //Initialise the variables to their corresponding views
        loginEmail = findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_password);
        forgotPass = findViewById(R.id.forgot_pass_btn);


        //Initialise the progress bar to be not visible for now
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //Initalise the FirebaseAuth variable
        firebaseAuth = FirebaseAuth.getInstance();

        //Assign forgot password method to button
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
    }

    public void loginUser(View view) {
        //Validate Login Info
        //If email or password doesn't pass validation then errors are returned
        if (!validateLoginEmail() | !validateLoginPassword()) {
            return;
        } else {
            //method to retrieve users data from Firebase
            validatePlayer();
        }

    }


    //Validation for email
    private Boolean validateLoginEmail() {
        String entry = loginEmail.getEditText().getText().toString();
        //If left blank error shown
        if (entry.isEmpty()) {
            loginEmail.setError("Email field cannot be empty");
            return false;
        } else {
            //No Error
            loginEmail.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            loginEmail.setErrorEnabled(false);
            return true;
        }

    }

    //Validation for password
    private Boolean validateLoginPassword() {
        String entry = loginPass.getEditText().getText().toString();

        //If left blank error shown
        if (entry.isEmpty()) {
            loginPass.setError("Password field cannot be empty");
            return false;
        } else {
            //No Error
            loginPass.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            loginPass.setErrorEnabled(false);
            return true;
        }
    }

    //Method to check the details against the firebase database and retrieve the information if present
    public void validatePlayer() {
        //Retrieve the user entered details
        final String userEnteredEmail = loginEmail.getEditText().getText().toString().trim();
        final String userEnteredPassword = loginPass.getEditText().getText().toString().trim();

        //Define the reference in the database that should be called
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        firebaseAuth.signInWithEmailAndPassword(userEnteredEmail, userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "TEST_Login : Logged In Successfully");
                    startActivity(new Intent(getApplicationContext(), PlayerProfile.class));
                } else {
                    loginEmail.setError("Email/Password Incorrect");
                    loginPass.setError("Email/Password Incorrect");

                    //Hide progress bar
                    progressBar.setVisibility(View.GONE);
                }
            }

        });
    }


    protected void forgotPassword() {
        //Get the users entered email
        final String entry = loginEmail.getEditText().getText().toString().trim();
        //Characters accepted for email address
        final String emailCharacters =
                "[a-z0-9!#$%&'*+/=?^_`{|}~-]" +
                        "+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|";


        //If entry left empty error shown
        if (entry.isEmpty()) {
            loginEmail.setError("Email field cannot be empty");
            //If entry doesn't match email regex then error shown
        } else if (!entry.matches(emailCharacters)) {
            loginEmail.setError("Invalid email address");
        } else {

            //Progress bar to show loading
            progressBar.setVisibility(View.VISIBLE);

            //Get an instance of the Firebase Auth
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();

            //Use Firebase Auth to send the password reset mail
            mAuth.sendPasswordResetEmail(entry)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Show successful Toast
                                Toast.makeText(getApplicationContext(), "Reset password email sent!", Toast.LENGTH_SHORT).show();
                            } else {
                                //Show error Toast
                                Toast.makeText(getApplicationContext(), "Error! Reset email not sent", Toast.LENGTH_SHORT).show();
                            }
                            //Hide progress bar
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }
    }


    //Method to direct user to appropriate page
    public void onButtonClicked(View view) {
        //If register button clicked launch player register page
        if (view.getId() == R.id.player_reg_btn) {
            Intent regActivity = new Intent(getApplicationContext(), PlayerRegistrationActivity.class);
            startActivity(regActivity);

        }
    }


}
