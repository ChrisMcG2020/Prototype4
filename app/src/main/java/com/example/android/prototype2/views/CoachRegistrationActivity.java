package com.example.android.prototype2.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.R;
import com.example.android.prototype2.helperClass.CoachModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class CoachRegistrationActivity extends AppCompatActivity implements View.OnClickListener {



    //Variables for the registration form
    private TextInputLayout regCoachName,
            regCoachEmail, regCoachPhoneNo, regCoachPassword, regTeamCoached;

    private Button coachReturnToLoginBtn;

    //Progress bar variable
    private ProgressBar progressBar;

    //Declare an instance of Firebase Authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set layout
        setContentView(R.layout.activity_coach_registration);

        //Initialise the variables to their corresponding views
        regCoachName = findViewById(R.id.coach_name);
        regCoachEmail = findViewById(R.id.coach_email);
        regCoachPhoneNo = findViewById(R.id.coach_phone_no);
        regCoachPassword = findViewById(R.id.coach_password);
        regTeamCoached = findViewById(R.id.team_coached);
        coachReturnToLoginBtn = findViewById(R.id.coach_return_to_login);

        //Assign progress bar and set it to not appear
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //Assign instance of Firebase variables, Firebase Auth to gain access to its features
        firebaseAuth= FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        //return to login button takes user back to login screen
        coachReturnToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent returnIntent = new Intent(getApplicationContext(), PlayerLoginActivity.class);
                startActivity(returnIntent);
            }
        });

    }

    //Validation for name

    //Validation for name
    private Boolean validateName() {
        //Retrieve the user's entry from the text field
        String entry = regCoachName.getEditText().getText().toString();
        //If empty display error
        if (entry.isEmpty()) {
            regCoachName.setError("Name field cannot be empty");
            return false;
        } else {
            //no Error
            regCoachName.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regCoachName.setErrorEnabled(false);
            return true;
        }

    }

    //Validation for email
    private Boolean validateEmail() {
        //Retrieve the user's entry from the text field
        String entry = regCoachEmail.getEditText().getText().toString();
        //Characters accepted for email address
        String emailCharacters = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                + "+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|";

        //If entry left empty error shown
        if (entry.isEmpty()) {
            regCoachEmail.setError("Email field cannot be empty");
            return false;
            //If entry doesn't match email regex then error shown
        } else if (!entry.matches(emailCharacters)) {
            regCoachEmail.setError("Invalid email address");
            return false;
        } else {
            //No error
            regCoachEmail.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regCoachEmail.setErrorEnabled(false);
            return true;
        }
    }

    //Validation for phone entry
    private Boolean validatePhoneNo() {
        //Retrieve the user's entry from the text field
        String entry = regCoachPhoneNo.getEditText().getText().toString();
        //If empty display error
        if (entry.isEmpty()) {
            regCoachPhoneNo.setError("Phone number field cannot be empty");
            return false;
            //If less than 6 numbers entered, invalid number
        } else if (entry.length() < 6) {
            regCoachPhoneNo.setError("Is number correctly formatted?");
            return false;

        } else {
            //No error
            regCoachPhoneNo.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regCoachPhoneNo.setErrorEnabled(false);
            return true;
        }
    }


    //Validation for password
    private Boolean validatePassword() {
        //Retrieve the user's entry from the text field
        String entry = regCoachPassword.getEditText().getText().toString();
        //Password must contain certain characters to be secure
        String passwordEntry = "^" +

                "(?=.*[A-Z])" +         //1 upper case
                "(?=.*[a-zA-Z])" +      //Any letter
                "(?=.*[@#$%^&+=!*])" +    //1 Special character
                "(?=\\S+$)" +           //No gaps
                ".{4,}" +               //At least 4 characters
                "$";
        //If empty display error
        if (entry.isEmpty()) {
            regCoachPassword.setError("Password field cannot be empty");
            return false;
            //If password does not follow constraints
        } else if (!entry.matches(passwordEntry)) {
            regCoachPassword.setError("Password must have at least 1 upper case,1 special character");
            return false;
        } else {
            //No error
            regCoachPassword.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regCoachPassword.setErrorEnabled(false);
            return true;
        }

    }


    //Validation for Team coached
    private Boolean validateTeamCoached() {
        //Retrieve the user's entry from the text field
        String entry = regTeamCoached.getEditText().getText().toString();
        //If empty display error
        if (entry.isEmpty()) {
            regTeamCoached.setError("Team coached field cannot be empty");
            return false;
        } else
            //No error
            regTeamCoached.setError(null);
        //setErrorEnabled(false) ensures layout will not change size when an error is displayed
        regTeamCoached.setErrorEnabled(false);
        return true;
    }

    //This function will execute when user click on Register Button
    private void registerCoachUser() {
        //Get all the values from the text fields
        final String name = regCoachName.getEditText().getText().toString();
        final String email = regCoachEmail.getEditText().getText().toString();
        final String teamCoached = regTeamCoached.getEditText().getText().toString();
        final String phoneNo = regCoachPhoneNo.getEditText().getText().toString();
        final String password = regCoachPassword.getEditText().getText().toString();

        //if any of the validations do not pass return will be called and errors will be shown
        if (!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateTeamCoached()) {
            return;
        }

        //Progress bar now visible while loading
        progressBar.setVisibility(View.VISIBLE);
        //Access the firebase reference and create a user with their email and password
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Get the UID
                        String coachUID = currentUser.getUid();

                        if (task.isSuccessful()) {
                            //Set the details matching the fields in CoachModel
                            CoachModel coachModel = new CoachModel();
                            coachModel.setCoachName(name);
                            coachModel.setCoachPhoneNumber(phoneNo);
                            coachModel.setCoachEmail(email);
                            coachModel.setTeamCoached(teamCoached);
                            coachModel.setUid(coachUID);

                            //Log tags used for testing
                            Log.d(TAG, "TEST_coach_RegName: " + name);
                            Log.d(TAG, "TEST_coach_RegPhone: " + phoneNo);
                            Log.d(TAG, "TEST_coach_RegEmail: " + email);
                            Log.d(TAG, "TEST_coach_TeamCoachedEC: " + teamCoached);
                            Log.d(TAG, "TEST_coachUID: " + coachUID);

                            //Get an instance of the firebase database and add the details from helper class to a new UID node
                            FirebaseDatabase.getInstance().getReference("Coaches").child(coachUID)
                                    //Insert the newly created coach at their UID path
                                    .setValue(coachModel).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    //If successful show Toast
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
                                        //Log tag for testing
                                        Log.d(TAG, "TEST_Coach_Reg_Success");


                                    } else {
                                        //Display a failure message
                                        Toast.makeText(getApplicationContext(), "Not Registered", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            //Show exception message
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        //After registration completed return to login screen to login
        Intent returnToLogin = new Intent(getApplicationContext(), CoachLoginActivity.class);
        startActivity(returnToLogin);
    }


    //On clicking register button run the method
    @Override
    public void onClick(View v) {
        registerCoachUser();
    }
}
