package com.example.android.prototype2.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.R;
import com.example.android.prototype2.helperClass.PlayerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlayerRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables for the registration form
    private TextInputLayout regName,
            regEmail, regPhoneNo, regPassword, regContact, regContactPhone, regDobValidationsError;
    //Variables for getting DOB
    private DatePicker regDOB;

    private Button regToLoginBtn;

    //Progress bar variable
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set layout
        setContentView(R.layout.activity_player_registration);

        //Initialise the variables to their corresponding views
        regName = findViewById(R.id.reg_name);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phone_no);
        regPassword = findViewById(R.id.reg_password);
        regDOB = findViewById(R.id.date_picker);
        regDobValidationsError = findViewById(R.id.reg_dob);
        regContact = findViewById(R.id.reg_emergency_contact);
        regContactPhone = findViewById(R.id.reg_emergency_contact_phone);
        regToLoginBtn = findViewById(R.id.back_to_login);

        //Assign progress bar and set it to not appear
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        //Assign instance of Firebase variables, Firebase Auth to gain access to its API features
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        //return to login button takes user back to login screen
        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent(getApplicationContext(), PlayerLoginActivity.class);
                startActivity(returnIntent);
            }
        });
    }


    //Validation for name
    private Boolean validateName() {
        //Retrieve the user's entry from the text field
        String entry = regName.getEditText().getText().toString();
        //If empty display error
        if (entry.isEmpty()) {
            regName.setError("Name field cannot be empty");
            return false;
        } else {
            //no Error
            regName.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regName.setErrorEnabled(false);
            return true;
        }

    }

    //Validation for email
    private Boolean validateEmail() {
        //Retrieve the user's entry from the text field
        String entry = regEmail.getEditText().getText().toString();

        //Characters accepted for email address
        String emailCharacters = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+" +
                "+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|";

        //If entry left empty error shown
        if (entry.isEmpty()) {
            regEmail.setError("Email field cannot be empty");
            return false;
            //If entry doesn't match email regex then error shown
        } else if (!entry.matches(emailCharacters)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            //No error
            regEmail.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regEmail.setErrorEnabled(false);
            return true;
        }
    }


    //Validation for phone entry
    private Boolean validatePhoneNo() {
        //Retrieve the user's entry from the text field
        String entry = regPhoneNo.getEditText().getText().toString();

        //If empty display error
        if (entry.isEmpty()) {
            regPhoneNo.setError("Phone number field cannot be empty");
            return false;
            //If less than 6 numbers entered invalid number
        } else if (entry.length() < 6) {
            regPhoneNo.setError("Is number correctly formatted?");
            return false;

        } else {
            //No error
            regPhoneNo.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }


    //Validation for password
    private Boolean validatePassword() {
        //Retrieve the user's entry from the text field
        String entry = regPassword.getEditText().getText().toString();
        //Password must contain certain characters to be secure
        String passwordEntry = "^" +

                "(?=.*[A-Z])" +         //1 upper case
                "(?=.*[a-zA-Z])" +      //Any letter
                "(?=.*[@#$%^&+=!*])" +   //1 Special character
                "(?=\\S+$)" +           //No gaps
                ".{4,}" +               //At least 4 characters
                "$";

        //If empty display error
        if (entry.isEmpty()) {
            regPassword.setError("Password field cannot be empty");
            return false;
            //If password does not follow constraints
        } else if (!entry.matches(passwordEntry)) {
            regPassword.setError("Password must have at least 1 upper case,1 special character");
            return false;
        } else {
            //No error
            regPassword.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regPassword.setErrorEnabled(false);
            return true;
        }

    }

    //Validation for Emergency contact name
    private Boolean validateContactName() {
        //Retrieve the user's entry from the text field
        String entry = regContact.getEditText().getText().toString();

        //If empty display error
        if (entry.isEmpty()) {
            regContact.setError("Name field cannot be empty");
            return false;
        } else {
            //No Error
            regContact.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regContact.setErrorEnabled(false);
            return true;
        }

    }

    //Validation for phone entry
    private Boolean validateContactPhoneNo() {
        //Retrieve the user's entry from the text field
        String entry = regContactPhone.getEditText().getText().toString();

        //If empty display error
        if (entry.isEmpty()) {
            regContactPhone.setError("Phone number field cannot be empty");
            return false;
        } else if (entry.length() < 6) {
            regContactPhone.setError("Is number correctly formatted?");
            return false;
        } else {
            regContactPhone.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regContactPhone.setErrorEnabled(false);
            return true;
        }
    }

    //Validation for date of birth, age must be over 18
    private Boolean validateDOB() {

        //Get the players Date of birth
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = regDOB.getYear();
        int ageRequired = currentYear - userAge;

        //If under 18 display error
        if (ageRequired < 18) {
            regDobValidationsError.setError("You must be over 18 to use this app");
            return false;
        } else
            return true;

    }

    //Method to register user
    private void registerUser() {


        //Get all the values from the text fields
        final String name = regName.getEditText().getText().toString();
        final String email = regEmail.getEditText().getText().toString();
        final String phoneNo = regPhoneNo.getEditText().getText().toString();
        final String userUID = currentUser.getUid();

        //Method to get a readable day/month/year from the Datepicker
        int day = regDOB.getDayOfMonth();
        int month = regDOB.getMonth() + 1;
        int year = regDOB.getYear();


        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy");
        Date date = new Date(year, month, day);
        final String dob = dateFormatter.format(date);
        //final String dob = stringDate;

        final String emergencyContact = regContact.getEditText().getText().toString();
        final String contactNumber = regContactPhone.getEditText().getText().toString();
        final String password = regPassword.getEditText().getText().toString();

        //If any of the fields do not pass validation then return appropriate errors
        if (!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateDOB() | !validateContactName() | !validateContactPhoneNo()) {
            return;
        }

        //Progress bar visible while loading
        progressBar.setVisibility(View.VISIBLE);
        //Access the firebase reference and create a user with their email and password
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Get the UID
                        String UID = currentUser.getUid();
                        if (task.isSuccessful()) {
                            //Set the details matching the fields in PlayerModel
                            PlayerModel helperClass = new PlayerModel();
                            helperClass.setName(name);
                            helperClass.setContactNumber(contactNumber);
                            helperClass.setEmail(email);
                            helperClass.setEmergencyContact(emergencyContact);
                            helperClass.setPhoneNo(phoneNo);
                            helperClass.setDob(dob);
                            helperClass.setUid(UID);

                            //Log tags used for testing
                            Log.d(TAG, "TEST_RegName: " + name);
                            Log.d(TAG, "TEST_RegPhone: " + phoneNo);
                            Log.d(TAG, "TEST_RegEmail: " + email);
                            Log.d(TAG, "TEST_EC: " + emergencyContact);
                            Log.d(TAG, "TEST_EC_phone: " + contactNumber);
                            Log.d(TAG, "TEST_Dob: " + dob);
                            Log.d(TAG, "TEST_UID: " + UID);

                            //Get an instance of the firebase database and add the details from helper class to a new UID node
                            FirebaseDatabase.getInstance().getReference("Players").child(UID)
                                    //Insert the newly created player at their UID path
                                    .setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    //If successful show Toast
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), getString(R.string.registration_success), Toast.LENGTH_SHORT).show();

                                        //Log tag for testing
                                        Log.d(TAG, "TEST_User_Reg_Success");

                                    } else {
                                        //Display a failure message
                                        Toast.makeText(getApplicationContext(), "Not Registered", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                });

        //After registration completed return to login screen to login
        Intent returnToLogin = new Intent(getApplicationContext(), PlayerLoginActivity.class);
        startActivity(returnToLogin);
    }


    //On clicking register button run the method
    @Override
    public void onClick(View v) {
        registerUser();
    }
}




