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

public class CoachProfile extends AppCompatActivity {

    //Variables
    private TextInputEditText coachNameTextView, coachPhoneNoTextView, coachEmailTextView, teamCoachedTextView;
    private TextView displayCoachName, displayCoachphone;
    private TextInputLayout emailView, phoneView, nameView, teamView;


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
        setContentView(R.layout.coach_profile);


        //Firebase reference for the Users
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Coaches");


        //Initialise the variables to their corresponding views
        nameView = findViewById(R.id.edit_text_coach_full_name);
        emailView = findViewById(R.id.edit_text_profile_email);
        phoneView = findViewById(R.id.edit_text_coach_phone);
        teamView = findViewById(R.id.edit_text_team);


        coachNameTextView = findViewById(R.id.coach_profile_name);
        coachPhoneNoTextView = findViewById(R.id.coach_profile_phone);
        coachEmailTextView = findViewById(R.id.coach_profile_email);
        displayCoachName = findViewById(R.id.coach_name);
        displayCoachphone = findViewById(R.id.coach_phone_small);
        teamCoachedTextView = findViewById(R.id.coach_profile_team);

        coachNameTextView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        coachPhoneNoTextView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        coachEmailTextView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        teamCoachedTextView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);


        //Query the database to get the current user
        Query query = databaseReference.orderByChild("coachEmail").equalTo(currentUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    //If no information found ,show error toast
                    if (snapshot.getValue() == null) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                    //Retrieve required information at that UID
                    String name = (String) userSnapshot.child("coachName").getValue();
                    String email = (String) userSnapshot.child("coachEmail").getValue();
                    String phone = (String) userSnapshot.child("coachPhoneNumber").getValue();
                    String teamCoached = (String) userSnapshot.child("teamCoached").getValue();

                    //Set the information in the text fields
                    displayCoachName.setText(name);
                    displayCoachphone.setText(phone);
                    coachNameTextView.setText(name);
                    coachPhoneNoTextView.setText(phone);
                    coachEmailTextView.setText(email);
                    teamCoachedTextView.setText(teamCoached);


                    //Logs for testing updates in Realtime DB
                    Log.d(TAG, "TEST__coachName: " + name);
                    Log.d(TAG, "TEST__coachPhone: " + phone);
                    Log.d(TAG, "TEST__coachEmail: " + email);
                    Log.d(TAG, "TEST__teamCoached: " + teamCoached);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });
    }

    //Method to update the coaches' profile
    private void updateCoachProfile() {

        //Assign variable to their views
        final String updateName = coachNameTextView.getText().toString();
        final String updateEmail = coachEmailTextView.getText().toString();
        final String updatePhone = coachPhoneNoTextView.getText().toString();
        final String updateTeamCoached = teamCoachedTextView.getText().toString();

        //If the new profile data doesn't pass the validations then return
        if (!validateCoachName() | !validateCoachPhone() | !validateCoachEmail() | !validateTeamCoached()) {
            return;
        }

        //Firebase updateEmail method used to update email and other profile information

        currentUser.updateEmail(updateEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Find the path in the database for the required information and update it
                firebaseDatabase.getReference("Coaches").child(currentUser.getUid()).child("coachName").setValue(updateName);
                //Update the displayed name in the top of profile page
                displayCoachName.setText(updateName);
                firebaseDatabase.getReference("Coaches").child(currentUser.getUid()).child("coachEmail").setValue(updateEmail);
                firebaseDatabase.getReference("Coaches").child(currentUser.getUid()).child("coachPhoneNumber").setValue(updatePhone);
                //Update the displayed phone in the top of profile page
                displayCoachphone.setText(updatePhone);
                firebaseDatabase.getReference("Coaches").child(currentUser.getUid()).child("teamCoached").setValue(updateTeamCoached);
                //Display success Toast
                Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();

                //Logs for testing updates in Realtime DB
                Log.d(TAG, "TEST__updated_coachName: " + updateName);
                Log.d(TAG, "TEST__updated_coachPhone: " + updatePhone);
                Log.d(TAG, "TEST__updated_coachEmail: " + updateEmail);
                Log.d(TAG, "TEST__updated_teamCoached: " + updateTeamCoached);


            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Show an error Toast
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }


    //Method to delete the player profile
    private void deleteCoachProfile() {
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
        deleteUser.show(CoachProfile.this.getSupportFragmentManager(), "DialogFragment");
    }


    private void deleteCurrentUser(String uid) {
        //Get a reference to the Coaches uid in the database
        DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Coaches").child(uid);
        //Remove the coach
        databaseReferenceUser.removeValue();

        //Show a toast to say user has been removed
        Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_LONG).show();

        //Logging statements to test delete feature of Realtime DB
        Log.d(TAG, "TEST_DELETE: USER=" + currentUser.getUid());
        Log.d(TAG, "USER DELETED");

        //Take the user back to the start up screen
        Intent splashScreen = new Intent(getApplicationContext(), SplashScreen.class);
        startActivity(splashScreen);


    }


    //Validation for name
    private Boolean validateCoachName() {
        //Retrieve the user's entry from the text field
        String entry = coachNameTextView.getText().toString();
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
    private Boolean validateCoachEmail() {
        //Retrieve the user's entry from the text field
        String entry = coachEmailTextView.getText().toString();
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
    private boolean validateCoachPhone() {
        //Retrieve the user's entry from the text field
        String entry = coachPhoneNoTextView.getText().toString();
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
        }

        return true;
    }

    //Validation for team coached
    private Boolean validateTeamCoached() {
        //Retrieve the user's entry from the text field
        String entry = teamCoachedTextView.getText().toString();

        //If empty display error
        if (entry.isEmpty()) {
            teamView.setError("Team field cannot be empty");
            return false;
        } else {
            //No error
            teamView.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            teamView.setErrorEnabled(false);
            return true;
        }

    }


    //Method to direct button clicks to correct action
    public void onButtonClicked(@NonNull View view) {
        //Switch statement implemented as a lot of choices available
        switch (view.getId()) {
            //If recover image clocked recover advice page launched
            case R.id.diagnose_concussion_button:
                Intent diagnoseIntent = new Intent(getApplicationContext(), PlayerListViewActivity.class);
                startActivity(diagnoseIntent);
                break;
            //If incident image clicked then list of incidents returned
            case R.id.coach_sign_out_image:
                Intent logoutIntent = new Intent(getApplicationContext(), SplashScreen.class);
                startActivity(logoutIntent);
                break;
            //If logout button clicked then start up screen shown
            case R.id.history_image:
                Intent allIncidentsIntent = new Intent(getApplicationContext(), AllIncidentsListView.class);
                startActivity(allIncidentsIntent);
                break;
            //If player search image clicked launch the player list
            case R.id.player_search:
                Intent searchPlayers = new Intent(getApplicationContext(), PlayerListViewActivity.class);
                startActivity(searchPlayers);
                break;
            //If update button clicked proceed with update method
            case R.id.updateCoachProfile_btn:
                updateCoachProfile();
                break;
            //If delete button clicked proceed with method
            case R.id.deleteCoachProfile_btn:
                deleteCoachProfile();
                break;
            //If info button clicked then start up info page
            case R.id.coach_info_btn:
                Intent info = new Intent(getApplicationContext(), AppCoachInformationPage.class);
                startActivity(info);
                break;
            //Default for when no case matches the action
            default:
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }

    }
}