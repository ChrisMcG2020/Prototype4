package com.example.android.prototype2;

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

import com.example.android.prototype2.helperClass.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables for the registration form
    private TextInputLayout regName,
            regEmail, regPhoneNo, regPassword, regContact, regContactPhone, regDobValidationsError;
    //Variables for getting DOB
    DatePicker regDOB;

    private Button regBtn, regToLoginBtn;

    //Progress bar variable
    private ProgressBar progressBar;

    //Firebase variables
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser mCurrent;

    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set layout
        setContentView(R.layout.activity_registration);

        //Initialise the variables to their corresponding views
        regName = findViewById(R.id.reg_name);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phone_no);
        regPassword = findViewById(R.id.reg_password);
        regDOB = findViewById(R.id.date_picker);
        regDobValidationsError = findViewById(R.id.reg_dob);
        regContact = findViewById(R.id.reg_emergency_contact);
        regContactPhone = findViewById(R.id.reg_emergency_contact_phone);
        regBtn = findViewById(R.id.register_btn);
        regToLoginBtn = findViewById(R.id.back_to_login);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //Initialise Firebase components
        mAuth = FirebaseAuth.getInstance();
        mCurrent = mAuth.getCurrentUser();
        //Button to exit registration screen and return to login screen
        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayerLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }


    //Validation for name
    private Boolean validateName() {
        //Get the entry from the regName field
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

            String entry = regEmail.getEditText().getText().toString();

            //Characters accepted for email address
            String emailCharacters = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";

            //If empty display error
            if (entry.isEmpty()) {
                regEmail.setError("Email field cannot be empty");
                return false;
                //If email address does not follow constraints
           } else if (!entry.matches(emailCharacters)) {
              regEmail.setError("Invalid email address");
                return false;
            } else {
                regEmail.setError(null);
                //setErrorEnabled(false) ensures layout will not change size when an error is displayed
                regEmail.setErrorEnabled(false);
                return true;
            }
        }


    //Validation for phone entry
    private Boolean validatePhoneNo() {
        String entry = regPhoneNo.getEditText().getText().toString();

        //If empty display error
        if (entry.isEmpty()) {
            regPhoneNo.setError("Phone number field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }


    //Validation for password
    private Boolean validatePassword() {
        String entry = regPassword.getEditText().getText().toString();
        //Password must contain certain characters to be secure
        String passwordEntry = "^" +

                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=!*])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
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
            regPassword.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            regPassword.setErrorEnabled(false);
            return true;
        }

    }

    //Validation for Emergency contact name
    private Boolean validateContactName() {
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
        String entry = regContactPhone.getEditText().getText().toString();

        //If empty display error
        if (entry.isEmpty()) {
            regContactPhone.setError("Phone number field cannot be empty");
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

        //Get the users Date of birth
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
    public void registerUser() {


        //Get all the values from the text fields
        final String name = regName.getEditText().getText().toString();
        final String email = regEmail.getEditText().getText().toString();
        final String phoneNo = regPhoneNo.getEditText().getText().toString();
        final String userUID = mCurrent.getUid();

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

        DatabaseReference player = FirebaseDatabase.getInstance().getReference("Users");
        final String playerID = player.push().getKey();

        //If any of the fields do not pass validation then return appropriate errors
        if (!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateDOB() | !validateContactName() | !validateContactPhoneNo()) {
            return;
        }

        //Progress bar now visible
        progressBar.setVisibility(View.VISIBLE);
        //Access the firebase reference and create a user with their email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String UID=mCurrent.getUid();
                        if (task.isSuccessful()) {
                            //Get the details from our UserHelperClass
                            //UserHelperClass helperClass = new UserHelperClass(playerID, name, email, phoneNo, dob, emergencyContact, contactNumber, password,uid);
                            UserHelperClass helperClass = new UserHelperClass();
                            helperClass.setName(name);
                            helperClass.setContactNumber(contactNumber);
                            helperClass.setEmail(email);
                            helperClass.setEmergencyContact(emergencyContact);
                            helperClass.setPhoneNo(phoneNo);
                            helperClass.setDob(dob);
                           helperClass.setPlayerID(playerID);
                            helperClass.setUid(UID);

                            Log.d(TAG, "TEST_RegName: "+name);
                            Log.d(TAG, "TEST_RegPhone: "+phoneNo);
                            Log.d(TAG,"TEST_RegEmail: "+email);
                            Log.d(TAG, "TEST_EC: "+emergencyContact);
                            Log.d(TAG, "TEST_EC_phone: "+contactNumber);
                            Log.d (TAG, "TEST_Dob: "+dob);
                            Log.d(TAG,"TEST_UID: "+UID);

                            //Get an instance of the firebase database and add the details from helper class to the current user
                            FirebaseDatabase.getInstance().getReference("Users").child(UID)
                                    //  .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    //of successful show Toast
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), getString(R.string.registration_success), Toast.LENGTH_SHORT).show();

                                        Log.d(TAG,"TEST_User_Reg_Success");

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
        Intent intent = new Intent(getApplicationContext(), PlayerLoginActivity.class);
        startActivity(intent);
    }


    //On clicking register button run the method
    @Override
    public void onClick(View v) {
        registerUser();
    }
}




