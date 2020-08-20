package com.example.android.prototype2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.android.prototype2.dialogs.DeleteProfileDialog;
import com.example.android.prototype2.views.IncidentListView;
import com.example.android.prototype2.views.SplashScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class UserProfile extends AppCompatActivity {

    //Variables
    private Button updateProfile, deleteUser;
    private TextInputEditText emailTextView, phoneNoTextView, nameTextView, emergencyContactTextView, emergencyContactPhoneNoTextView;
    private TextInputLayout nameView, emailView, phoneView, emergencyContactView, emergencyContactPhoneView;
    private TextView displayName, displayPhone, incidents;
    String playerEmail;

    //Firebase variables
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set layout
        setContentView(R.layout.player_profile);

        //Firebase reference for the Users
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        final String uid = user.getUid();
        Intent intent = getIntent();
        playerEmail = intent.getStringExtra("email");


        //Initialise the variables to their corresponding views
        phoneView = findViewById(R.id.edit_text_profile_phone);
        emailView = findViewById(R.id.edit_text_profile_email);
        nameView = findViewById(R.id.edit_text_profile_full_name);
        emergencyContactView = findViewById(R.id.edit_text_emergency_contact);
        emergencyContactPhoneView = findViewById(R.id.edit_text_profile_emergency_phone);
        displayName = findViewById(R.id.user_name);
        displayPhone = findViewById(R.id.user_phone_small);

        emergencyContactPhoneView = findViewById(R.id.edit_text_profile_emergency_phone);
        incidents = findViewById(R.id.incident_text);
        updateProfile = findViewById(R.id.btn_updatePlayerProfile);
        nameTextView = findViewById(R.id.player_profile_name);
        emailTextView = findViewById(R.id.player_profile_email);
        phoneNoTextView = findViewById(R.id.player_profile_phone);
        emergencyContactTextView = findViewById(R.id.player_profile_emergency_contact);
        emergencyContactPhoneNoTextView = findViewById(R.id.player_prof_emergency_contact_phone);

        deleteUser = findViewById(R.id.btn_deleteProfile);

        //Query the database to get the current user
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Loop until required user found
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    //Retrieve required information
                    String name = (String) userSnapshot.child("name").getValue();
                    String email = (String) userSnapshot.child("email").getValue();
                    String phone = (String) userSnapshot.child("phoneNo").getValue();
                    String emergencyContact = (String) userSnapshot.child("emergencyContact").getValue();
                    String contactNumber = (String) userSnapshot.child("contactNumber").getValue();
                    String playerID = (String) userSnapshot.child("playerID").getValue();

                    //Assign the details to the text views
                    displayName.setText(name);
                    displayPhone.setText(phone);
                    nameTextView.setText(name);
                    phoneNoTextView.setText(phone);
                    emailTextView.setText(email);
                    emergencyContactTextView.setText(emergencyContact);
                    emergencyContactPhoneNoTextView.setText(contactNumber);


                    // Intent intent = new Intent(getIntent());
                    // intent.putExtra("PlayerID", playerID);

                    //Logging statements to test update feature of Realtime DB
                    Log.d(TAG, "TEST__name: " + name);
                    Log.d(TAG, "TEST_phone: " + phone);
                    Log.d(TAG, "TEST_email: " + email);
                    Log.d(TAG, "TEST_EC: " + emergencyContact);
                    Log.d(TAG, "TEST_EC_phone: " + contactNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Method to update the players profile
    private void updatePlayerProfile() {

        //Assign variable to their views
        final String updateName = nameTextView.getText().toString();
        final String updateEmail = emailTextView.getText().toString();
        final String updatePhone = phoneNoTextView.getText().toString();
        final String updateEmergencyContact = emergencyContactTextView.getText().toString();
        final String updateEC_Phone = emergencyContactPhoneNoTextView.getText().toString();

        //If the new profile data doesnt pass the validations then return
        if (!validateName() | !validatePhone() | !validateEmail() | !validateContactName() | !validateECPhone()) {
            return;
        }
        //Firebase updateEmail method used to update email and other profile information
        user.updateEmail(updateEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        //Find the path in the database for the required information and update it
                        firebaseDatabase.getReference("Users").child(user.getUid()).child("name").setValue(updateName);
                        displayName.setText(updateName);
                        firebaseDatabase.getReference("Users").child(user.getUid()).child("phoneNo").setValue(updatePhone);
                        firebaseDatabase.getReference("Users").child(user.getUid()).child("email").setValue(updateEmail);
                        displayPhone.setText(phoneNoTextView.getText().toString());
                        firebaseDatabase.getReference("Users").child(user.getUid()).child("emergencyContact").setValue(updateEmergencyContact);
                        firebaseDatabase.getReference("Users").child(user.getUid()).child("contactNumber").setValue(updateEC_Phone);
                        Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();


                        //Logging statements to test update feature of Realtime DB
                        Log.d(TAG, "TEST_updated_name: " + updateName);
                        Log.d(TAG, "TEST_updated_email: " + updateEmail);
                        Log.d(TAG, "TEST_updated_phone: " + updatePhone);
                        Log.d(TAG, "TEST_updated_EC: " + updateEmergencyContact);
                        Log.d(TAG, "TEST_updated_EC_phone: " + updateEC_Phone);
                    }

                }).

                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    //Method to delete the player profile
    private void deletePlayerProfile() {

        // Custom Dialog is called and user input is dealt with by the correct method
        DialogFragment deleteUser = new DeleteProfileDialog(new DeleteProfileDialog.NoticeDialogListener() {
            @Override
            public void onDialogPositiveClick(DialogInterface dialog) {
                //What to do incase of positive click
                deleteCurrentUser(user.getUid());

            }

            @Override
            public void onDialogNegativeClick(DialogFragment dialog) {
                //What to do incase of negative click
                dialog.dismiss();
            }
        });
        deleteUser.show(UserProfile.this.getSupportFragmentManager(), "DialogFragment");
    }


    private void deleteCurrentUser(String uid) {
        //Get a reference to the Users uid in the database
        DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        //Remove the user
        databaseReferenceUser.removeValue();

        //Show a toast to say user has been removed
        Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_LONG).show();
        //Logging statements to test delete feature of Realtime DB
        Log.d(TAG, "TEST_DELETE: USER=" + user.getUid());
        Log.d(TAG, "USER DELETED");

        //Take the user back to the start up screen
        Intent splashScreen = new Intent(getApplicationContext(), SplashScreen.class);
        startActivity(splashScreen);

    }

    //Validation for name
    private Boolean validateName() {
        //Get the entry from the nameTextView field
        String entry = nameTextView.getText().toString();
        //If empty display error
        if (entry.isEmpty()) {
            nameView.setError("Name field cannot be empty");
            return false;
        } else {
            //no Error
            nameView.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            nameView.setErrorEnabled(false);
            return true;
        }

    }

    //Validation for email
    public Boolean validateEmail() {
        String entry = emailTextView.getText().toString();
        //Characters accepted for email address
        String emailCharacters = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                + "+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|";


        //If entry left empty error shown
        if (entry.isEmpty()) {
            emailView.setError("Email field cannot be empty");
            return false;
        }
        //If entry doesn't match email regex then error shown
        if (!entry.matches(emailCharacters)) {
            emailView.setError("Invalid email address");
            return false;
        } else {
            //no Error
            emailView.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            emailView.setErrorEnabled(false);
            return true;
        }
    }


    //Validation for phone number
    private boolean validatePhone() {
        String entry = phoneNoTextView.getText().toString();
        //If the entry is left blank or a short number entered show error
        if (entry.isEmpty()) {
            phoneView.setError("Phone number must not be blank");
            return false;
        } else if (entry.length() < 6) {
            phoneView.setError("Is number correctly formatted?");
            return false;
        } else {
            //no Error
            phoneView.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            phoneView.setErrorEnabled(false);
            return true;

        }
    }

    //Validation for Emergency contact name
    private Boolean validateContactName() {
        String entry = emergencyContactTextView.getText().toString();

        //If empty display error
        if (entry.isEmpty()) {
            emergencyContactView.setError("Name field cannot be empty");
            return false;
        } else {
            //No Error
            emergencyContactView.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            emergencyContactView.setErrorEnabled(false);
            return true;
        }

    }

    //Validation for Emergency Contact phone number
    private boolean validateECPhone() {

        String entry = emergencyContactPhoneNoTextView.getText().toString();
        //If the entry is left blank or a short number entered show error
        if (entry.isEmpty()) {
            emergencyContactPhoneView.setError("Phone number must not be blank");
            return false;
        } else if (entry.length() < 6) {
            emergencyContactPhoneView.setError("Is number correctly formatted?");
            return false;
        } else {
            //no Error
            emergencyContactPhoneView.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            emergencyContactPhoneView.setErrorEnabled(false);
            return true;
        }
    }

    //Method to direct button clicks to correct action
    public void onButtonClicked(@NonNull View view) {
        //If recover image clocked recover advice page launched
        if (view.getId() == R.id.recovery_image) {
            Intent recoveryIntent = new Intent(getApplicationContext(), RecoveryActivity.class);
            startActivity(recoveryIntent);
            //If incident image clicked then list of incidents returned
        } else if (view.getId() == R.id.incident_image) {
            Intent incident = new Intent(getApplicationContext(), IncidentListView.class);
            startActivity(incident);
            //If signout button clicked then start up screen shown
        } else if (view.getId() == R.id.sign_out_image) {
            Intent logoutIntent = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(logoutIntent);
            //If update button clicked proceed with update method
        } else if (view.getId() == R.id.btn_updatePlayerProfile) {
            updatePlayerProfile();
            //If delete button clicked proceed with method
        } else if (view.getId() == R.id.btn_deleteProfile) {
            deletePlayerProfile();
        }
    }
}
