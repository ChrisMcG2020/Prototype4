package com.example.android.prototype2;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CoachLoginActivity extends AppCompatActivity {


    //Declare the variables
    private TextView regLink;
    private TextInputLayout loginEmail, loginPass;
    private Button forgotPass, register;

    //Firebase variables
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    ProgressBar progressBar;


    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set layout
        setContentView(R.layout.activity_login);

        //Initialise the variables to their corresponding views
        loginEmail = findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_password);
        forgotPass = findViewById(R.id.forgot_pass_btn);
        register = findViewById(R.id.player_reg_btn);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        //regLink textView when click allows user to register
        regLink = findViewById(R.id.reglink_text_view);


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
            isUser();
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
            loginPass.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            loginPass.setErrorEnabled(false);
            return true;
        }
    }

    //Method to check the details against the firebase database and retrieve the information if present
    public void isUser() {
        //Retrieve the user entered details
        final String userEnteredEmail = loginEmail.getEditText().getText().toString().trim();
        final String userEnteredPassword = loginPass.getEditText().getText().toString().trim();

        rootNode = FirebaseDatabase.getInstance();
        //Define the reference in the database that should be called
        databaseReference = rootNode.getReference("Coaches");
        //Tag for printing log details
        Log.d(TAG, "Firebase contacted");

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String user = currentFirebaseUser.getUid();
        firebaseAuth = FirebaseAuth.getInstance();


        final Query[] checkUser = {databaseReference.orderByChild("coachEmail").equalTo(userEnteredEmail)};

        firebaseAuth.signInWithEmailAndPassword(userEnteredEmail, userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), CoachProfile.class));
                } else {
                    loginEmail.setError("Email/Password Incorrect");
                    loginPass.setError("Email/Password Incorrect");

                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    protected void forgotPassword() {

        final String entry = loginEmail.getEditText().getText().toString();
        //Characters accepted for email address
        final String emailCharacters = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                + "+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|";

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        //If entry left empty error shown
        if (entry.isEmpty()) {
            loginEmail.setError("Email field cannot be empty");

            //If entry doesn't match email regex then error shown
        } else if (!entry.matches(emailCharacters)) {
            loginEmail.setError("Invalid email address");
        } else {

            progressBar.setVisibility(View.VISIBLE);

            mAuth.sendPasswordResetEmail(entry)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Reset password email sent!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error! Reset email not sent", Toast.LENGTH_SHORT).show();
                            }

                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }
    }


    //Method to direct user to appropriate page
    public void onButtonClicked(View view) {
        if (view.getId() == R.id.player_reg_btn) {
            Intent coachLogin = new Intent(getApplicationContext(), CoachRegistrationActivity.class);
            startActivity(coachLogin);

        }
    }
}
