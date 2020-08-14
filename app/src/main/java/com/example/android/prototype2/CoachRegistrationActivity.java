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

import com.example.android.prototype2.helperClass.CoachHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CoachRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    //Tag for printing log details
    private static final String TAG = "CoachRegActivity";

    //Declare variables
    private TextInputLayout regCoachName,
            regCoachEmail, regCoachPhoneNo, regCoachPassword, regTeamCoached;
    private Button regCoachBtn, coachReturnToLoginBtn;
    private ProgressBar progressBar;

    //Declare an instance of Firebase Authentication
    private FirebaseAuth mAuth;


    FirebaseUser currentUser;


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
        regCoachBtn = findViewById(R.id.coach_register);
        coachReturnToLoginBtn = findViewById(R.id.coach_return_to_login);

        //Assign progress bar and set it to not appear
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //Assign instance of Firebase variables
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //return to login button takes user back to login screen
        coachReturnToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PlayerLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    //Validation for name

    //Validation for name
    private Boolean validateName() {
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
        String entry = regCoachEmail.getEditText().getText().toString();
        //Characters accepted for email address
        String emailCharacters = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (entry.isEmpty()) {
            regCoachEmail.setError("Email field cannot be empty");
            return false;
        } else if (!entry.matches(emailCharacters)) {
            regCoachEmail.setError("Invalid email address");
            return false;
        } else {
            regCoachEmail.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regCoachEmail.setErrorEnabled(false);
            return true;
        }
    }

    //Validation for phone entry
    private Boolean validatePhoneNo() {
        String entry = regCoachPhoneNo.getEditText().getText().toString();
        //If empty display error
        if (entry.isEmpty()) {
            regCoachPhoneNo.setError("Phone number field cannot be empty");
            return false;
        } else {
            regCoachPhoneNo.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regCoachPhoneNo.setErrorEnabled(false);
            return true;
        }
    }


    //Validation for password
    private Boolean validatePassword() {
        String entry = regCoachPassword.getEditText().getText().toString();
        String passwordEntry = "^" +

                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=!*])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        //If empty display error
        if (entry.isEmpty()) {
            regCoachPassword.setError("Password field cannot be empty");
            return false;
        } else if (!entry.matches(passwordEntry)) {
            regCoachPassword.setError("Password is not secure enough, try again");
            return false;
        } else {
            regCoachPassword.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regCoachPassword.setErrorEnabled(false);
            return true;
        }

    }


    //Validation for Team coached
    private Boolean validateTeamCoached() {
        String entry = regTeamCoached.getEditText().getText().toString();
        //If empty display error
        if (entry.isEmpty()) {
            regTeamCoached.setError("Team coached field cannot be empty");
            return false;
        } else
            regTeamCoached.setError(null);
        //setErrorEnabled(false) ensures layout will not change size when an error is displayed
        regTeamCoached.setErrorEnabled(false);
        return true;
    }

    //This function will execute when user click on Register Button
    public void registerCoachUser() {
        //Get all the values from the text fields
        final String name = regCoachName.getEditText().getText().toString();
        final String email = regCoachEmail.getEditText().getText().toString();
        final String teamCoached = regTeamCoached.getEditText().getText().toString();
        final String phoneNo = regCoachPhoneNo.getEditText().getText().toString();
        final String password = regCoachPassword.getEditText().getText().toString();
        //Get a reference to the required section of the database

        DatabaseReference coach = FirebaseDatabase.getInstance().getReference("Coaches");
        //Add a unique Id to the coach
        final String id = coach.push().getKey();
        // final String clubId= coach.push().getKey();
        //if any of the validations do not pass return will be called and errors will be shown
        if (!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateTeamCoached()) {
            return;
        }

        //Progress bar now visible
        progressBar.setVisibility(View.VISIBLE);
        //Access the firebase reference and create a user with their email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String coachUID = currentUser.getUid();

                        if (task.isSuccessful()) {
                            //Get the details from our UserHelperClass
                            CoachHelperClass coachHelperClass = new CoachHelperClass();
                            coachHelperClass.setCoachName(name);
                            coachHelperClass.setCoachPhoneNumber(phoneNo);
                            coachHelperClass.setCoachEmail(email);
                            coachHelperClass.setTeamCoached(teamCoached);
                            coachHelperClass.setCoachID(id);
                            coachHelperClass.setUid(coachUID);

                            Log.d(TAG, "TEST_coach_RegName: "+name);
                            Log.d(TAG, "TEST_coach_RegPhone: "+phoneNo);
                            Log.d(TAG,"TEST_coach_RegEmail: "+email);
                            Log.d(TAG, "TEST_coach_TeamCoachedEC: "+teamCoached);
                            Log.d(TAG,"TEST_coachUID: "+coachUID);

                            //Get an instance of the firebase database and add the details from helper class to the current user
                            FirebaseDatabase.getInstance().getReference("Coaches").child(coachUID)
                                    //.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(coachHelperClass).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    //of successful show Toast
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
                                        Log.d(TAG,"TEST_Coach_Reg_Success");


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
        Intent intent = new Intent(getApplicationContext(), CoachLoginActivity.class);
        startActivity(intent);
    }


    //On clicking register button run the method
    @Override
    public void onClick(View v) {
        registerCoachUser();
    }
}
