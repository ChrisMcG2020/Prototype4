package com.example.android.prototype2.views;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CoachLoginActivity extends AppCompatActivity {


    //Declare the variables
    private TextInputLayout loginEmail, loginPass;
    private Button forgotPass;
    private EditText loginEmail2,loginPass2;
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
        progressBar = findViewById(R.id.progressBar);
        //Initialise the progress bar to be not visible for now
        progressBar.setVisibility(View.GONE);

        //Assign EditText views
        loginEmail2=findViewById(R.id.login_email2);
        loginPass2=findViewById(R.id.login_password2);

        //To disable fullscreen keyboard in landscape mode
        loginEmail2.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        loginPass2.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

        //Initialise the FirebaseAuth variable
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
            //Method to retrieve users data from Firebase
            validateCoach();
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

    //Method to check the details with Firebase Auth and proceed if present
    protected void validateCoach() {
        //Retrieve the user entered details
        final String userEnteredEmail = loginEmail.getEditText().getText().toString().trim();
        final String userEnteredPassword = loginPass.getEditText().getText().toString().trim();

        //Define the path in the database that should be called
        databaseReference = FirebaseDatabase.getInstance().getReference("Coaches");

        //Use the FirebaseAuth method to sign in
        firebaseAuth.signInWithEmailAndPassword(userEnteredEmail, userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    //Show successful Toast
                    Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    //Log message for testing
                    Log.d(TAG, "TEST_Login : Logged In Successfully");
                    //Launch profile page
                    startActivity(new Intent(getApplicationContext(), CoachProfile.class));
                } else {
                    //Show validation errors
                    loginEmail.setError("Email/Password Incorrect");
                    loginPass.setError("Email/Password Incorrect");

                    //Hide progress bar
                    progressBar.setVisibility(View.GONE);


                }
            }
        });
    }

    //Method to get the users email and send them a reset password email
    private void forgotPassword() {
        //Get the users entered email
        final String entry = loginEmail.getEditText().getText().toString().trim();
        //Characters accepted for email address
        final String emailCharacters = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                + "+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|";

        //If entry left empty error shown
        if (entry.isEmpty()) {
            loginEmail.setError("Email field cannot be empty");

            //If entry doesn't match email regex then error shown
        } else if (!entry.matches(emailCharacters)) {
            loginEmail.setError("Invalid email address");
            //No Error
        } else {

            progressBar.setVisibility(View.VISIBLE);

            //Use Firebase Auth to send the password reset mail
            firebaseAuth.sendPasswordResetEmail(entry)
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
        //If register button clicked launch coach register page
        if (view.getId() == R.id.player_reg_btn) {
            Intent coachLogin = new Intent(getApplicationContext(), CoachRegistrationActivity.class);
            startActivity(coachLogin);
        }
    }
}
