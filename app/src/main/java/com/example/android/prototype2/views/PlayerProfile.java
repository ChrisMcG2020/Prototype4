package com.example.android.prototype2.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.android.prototype2.R;
import com.example.android.prototype2.dialogs.DeleteProfileDialog;
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


public class PlayerProfile extends AppCompatActivity {

    //Variables
    private TextInputEditText emailTextView, phoneNoTextView, nameTextView, emergencyContactTextView, emergencyContactPhoneNoTextView;
    private TextInputLayout nameView, emailView, phoneView, emergencyContactView, emergencyContactPhoneView;
    private TextView displayName, displayPhone;

    //Firebase variables
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set layout
        setContentView(R.layout.player_profile);

        //Firebase reference for the Players
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Players");




        //Initialise the variables to their corresponding views
        phoneView = findViewById(R.id.edit_text_profile_phone);
        emailView = findViewById(R.id.edit_text_profile_email);
        nameView = findViewById(R.id.edit_text_profile_full_name);
        emergencyContactView = findViewById(R.id.edit_text_emergency_contact);
        emergencyContactPhoneView = findViewById(R.id.edit_text_profile_emergency_phone);
        displayName = findViewById(R.id.user_name);
        displayPhone = findViewById(R.id.user_phone_small);
        emergencyContactPhoneView = findViewById(R.id.edit_text_profile_emergency_phone);

        nameTextView = findViewById(R.id.player_profile_name);
        emailTextView = findViewById(R.id.player_profile_email);
        phoneNoTextView = findViewById(R.id.player_profile_phone);
        emergencyContactTextView = findViewById(R.id.player_profile_emergency_contact);
        emergencyContactPhoneNoTextView = findViewById(R.id.player_prof_emergency_contact_phone);

        nameTextView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        emailTextView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        phoneNoTextView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        emergencyContactTextView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        emergencyContactPhoneNoTextView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);




        //Query the database to get the current user
        Query query = databaseReference.orderByChild("uid").equalTo(currentUser.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    //If no information found ,show error toast
                    if (snapshot.getValue() == null) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                    //Retrieve required information at that UID
                    String name = (String) userSnapshot.child("name").getValue();
                    String email = (String) userSnapshot.child("email").getValue();
                    String phone = (String) userSnapshot.child("phoneNo").getValue();
                    String emergencyContact = (String) userSnapshot.child("emergencyContact").getValue();
                    String contactNumber = (String) userSnapshot.child("contactNumber").getValue();


                    //Set the information in the text fields
                    displayName.setText(name);
                    displayPhone.setText(phone);
                    nameTextView.setText(name);
                    phoneNoTextView.setText(phone);
                    emailTextView.setText(email);
                    emergencyContactTextView.setText(emergencyContact);
                    emergencyContactPhoneNoTextView.setText(contactNumber);


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

        //If the new profile data doesn't pass the validations then return
        if (!validateName() | !validatePhone() | !validateEmail() | !validateContactName() | !validateECPhone()) {
            return;
        }
        //Firebase updateEmail method used to update email and other profile information

        currentUser.updateEmail(updateEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        //Find the path in the database for the required information and update it
                        firebaseDatabase.getReference("Players").child(currentUser.getUid()).child("name").setValue(updateName);
                        //Update the displayed name in the top of profile page
                        displayName.setText(updateName);
                        firebaseDatabase.getReference("Players").child(currentUser.getUid()).child("phoneNo").setValue(updatePhone);
                        firebaseDatabase.getReference("Players").child(currentUser.getUid()).child("email").setValue(updateEmail);
                        //Update the displayed phone in the top of profile page
                        displayPhone.setText(phoneNoTextView.getText().toString());
                        firebaseDatabase.getReference("Players").child(currentUser.getUid()).child("emergencyContact").setValue(updateEmergencyContact);
                        firebaseDatabase.getReference("Players").child(currentUser.getUid()).child("contactNumber").setValue(updateEC_Phone);
                        //Display success Toast
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
                        //Show an error Toast
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

                    }
                });

    }

    //Method to delete the player profile
    private void deletePlayerProfile() {

        // Custom Dialog is called and user input is dealt with by the correct method
        DialogFragment deleteUser = new DeleteProfileDialog(new DeleteProfileDialog.NoticeDialogListener() {
            @Override
            public void onDialogPositiveClick(DialogInterface dialog) {
                //If positive click run delete user method
                deleteCurrentUser(currentUser.getUid());

            }

            @Override
            public void onDialogNegativeClick(DialogFragment dialog) {
                //If negative click dismiss the dialog
                dialog.dismiss();
            }
        });
        deleteUser.show(PlayerProfile.this.getSupportFragmentManager(), "DialogFragment");
    }


    private void deleteCurrentUser(String uid) {
        //Get a reference to the Players uid in the database
        DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Players").child(uid);

        //Remove the player
        databaseReferenceUser.removeValue();

        //Show a Toast to say user has been removed
        Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_LONG).show();
        //Logging statements to test delete feature of Realtime DB
        Log.d(TAG, "TEST_DELETE: USER=" + currentUser.getUid());
        Log.d(TAG, "USER DELETED");

        //Take the user back to the start up screen
        Intent splashScreen = new Intent(getApplicationContext(), SplashScreen.class);
        startActivity(splashScreen);

    }

    //Validation for name
    private Boolean validateName() {
        //Retrieve the user's entry from the text field
        String entry = nameTextView.getText().toString();
        //If empty display error
        if (entry.isEmpty()) {
            nameView.setError("Name field cannot be empty");
            return false;
        } else {
            //No Error
            nameView.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            nameView.setErrorEnabled(false);
            return true;
        }

    }

    //Validation for email
    private Boolean validateEmail() {
        //Retrieve the user's entry from the text field
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
            //No error
            emailView.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            emailView.setErrorEnabled(false);
            return true;
        }
    }


    //Validation for phone number
    private boolean validatePhone() {
        //Retrieve the user's entry from the text field
        String entry = phoneNoTextView.getText().toString();
        //If the entry is left blank or a short number entered show error
        if (entry.isEmpty()) {
            phoneView.setError("Phone number must not be blank");
            return false;
            //If less than 6 numbers entered invalid number
        } else if (entry.length() < 6) {
            phoneView.setError("Is number correctly formatted?");
            return false;
        } else {
            //No error
            phoneView.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            phoneView.setErrorEnabled(false);
            return true;

        }
    }

    //Validation for Emergency contact name
    private Boolean validateContactName() {
        //Retrieve the user's entry from the text field
        String entry = emergencyContactTextView.getText().toString();

        //If empty display error
        if (entry.isEmpty()) {
            emergencyContactView.setError("Name field cannot be empty");
            return false;
        } else {
            //No error
            emergencyContactView.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            emergencyContactView.setErrorEnabled(false);
            return true;
        }

    }

    //Validation for Emergency Contact phone number
    private boolean validateECPhone() {
        //Retrieve the user's entry from the text field
        String entry = emergencyContactPhoneNoTextView.getText().toString();
        //If the entry is left blank or a short number entered show error
        if (entry.isEmpty()) {
            emergencyContactPhoneView.setError("Phone number must not be blank");
            return false;
        } else if (entry.length() < 6) {
            emergencyContactPhoneView.setError("Is number correctly formatted?");
            return false;
        } else {
            //no error
            emergencyContactPhoneView.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            emergencyContactPhoneView.setErrorEnabled(false);
            return true;
        }
    }

    //Method to direct button clicks to correct action
    public void onButtonClicked(@NonNull View view) {
        //Switch statement implemented as a lot of choices available
        switch (view.getId()) {
            //If recover image clocked recover advice page launched
            case R.id.recovery_image:
                Intent recoveryIntent = new Intent(getApplicationContext(), RecoveryActivity.class);
                startActivity(recoveryIntent);
                break;
            //If incident image clicked then list of incidents returned
            case R.id.incident_image:
                Intent incident = new Intent(getApplicationContext(), IncidentListView.class);
                startActivity(incident);
                break;
            //If logout button clicked then start up screen shown
            case R.id.sign_out_image:
                Intent logoutIntent = new Intent(getApplicationContext(), SplashScreen.class);
                startActivity(logoutIntent);
                break;
            //If update button click run update player method
            case R.id.btn_updatePlayerProfile:
                updatePlayerProfile();
                break;
            //If delete button click run delete player method
            case R.id.btn_deleteProfile:
                deletePlayerProfile();
                break;
            //If info button clicked then start up info page
            case R.id.player_info_btn:
                Intent info = new Intent(getApplicationContext(), AppInformationPage.class);
                startActivity(info);
                break;
            //Default for when no case matches the action
            default:
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }
}