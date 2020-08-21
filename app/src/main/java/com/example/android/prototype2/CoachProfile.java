package com.example.android.prototype2;

import android.app.Activity;
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
import com.example.android.prototype2.helperClass.CoachHelperClass;
import com.example.android.prototype2.views.AllIncidentsListView;
import com.example.android.prototype2.views.PlayerListViewActivity;
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

import java.util.List;

public class CoachProfile extends AppCompatActivity {

    //Variables
    private Activity context;
    //Declare a list using the CoachHelperClass
    private List<CoachHelperClass> coachesList;


    private TextInputEditText coachNameTextView, coachPhoneNoTextView, coachEmailTextView, teamCoachedTextView;
    private TextView displayCoachName, displayCoachphone;
    private TextInputLayout emailView, phoneView, nameView, teamView;
    private String coachEmail;
    private Button updateCoach, deleteCoach;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //Tag for printing log det
    // Fails
    private final String TAG = getClass().getSimpleName();
    // private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set layout
        setContentView(R.layout.coach_profile);


        //Firebase reference for the Users
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Coaches");


        Intent intent = getIntent();
        coachEmail = intent.getStringExtra("email");


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

        updateCoach = findViewById(R.id.btn_updateCoachProfile);
        deleteCoach = findViewById(R.id.btn_deleteCoachProfile);


        final String uid = user.getUid();

        //Query the database to get the current user
        Query query = databaseReference.orderByChild("coachEmail").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Loop until required user found
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    //Retrieve required information
                    String name = (String) userSnapshot.child("coachName").getValue();
                    String email = (String) userSnapshot.child("coachEmail").getValue();
                    String phone = (String) userSnapshot.child("coachPhoneNumber").getValue();
                    String teamCoached = (String) userSnapshot.child("teamCoached").getValue();


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
        user.updateEmail(updateEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Find the path in the database for the required information and update it
                firebaseDatabase.getReference("Coaches").child(user.getUid()).child("coachName").setValue(updateName);
                displayCoachName.setText(updateName);
                firebaseDatabase.getReference("Coaches").child(user.getUid()).child("coachEmail").setValue(updateEmail);
                firebaseDatabase.getReference("Coaches").child(user.getUid()).child("coachPhoneNumber").setValue(updatePhone);
                displayCoachphone.setText(updatePhone);
                firebaseDatabase.getReference("Coaches").child(user.getUid()).child("teamCoached").setValue(updateTeamCoached);

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

            }
        });

    }


    //Method to delete the player profile
    private void deleteCoachProfile() {
        // Custom Dialog is called and user input is dealt with by the correct method
        DialogFragment deleteUser = new DeleteProfileDialog(new DeleteProfileDialog.NoticeDialogListener() {
            @Override
            public void onDialogPositiveClick(DialogInterface dialog) {
                //What you want to do incase of positive click
                deleteCurrentUser(user.getUid());

            }

            @Override
            public void onDialogNegativeClick(DialogFragment dialog) {
                //What you want to do incase of negative click
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
        Log.d(TAG, "TEST_DELETE: USER=" + user.getUid());
        Log.d(TAG, "USER DELETED");

        //Take the user back to the start up screen
        Intent return_toSplash_intent = new Intent(getApplicationContext(), SplashScreen.class);
        startActivity(return_toSplash_intent);


    }


    //Validation for name
    private Boolean validateCoachName() {
        //Get the entry from the nameTextView field
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
    public Boolean validateCoachEmail() {
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
        String entry = coachPhoneNoTextView.getText().toString();
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
        }

        return true;
    }

    //Validation for team coached
    private Boolean validateTeamCoached() {
        String entry = teamCoachedTextView.getText().toString();

        //If empty display error
        if (entry.isEmpty()) {
            teamView.setError("Team field cannot be empty");
            return false;
        } else {
            //No Error
            teamView.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            teamView.setErrorEnabled(false);
            return true;
        }

    }


    //Method to direct button clicks to correct action
    public void onButtonClicked(View view) {
        //If diagnose concussion button clicked launch the player list
        if (view.getId() == R.id.diagnose_concussion_button) {
            Intent diagnoseIntent = new Intent(getApplicationContext(), PlayerListViewActivity.class);
            startActivity(diagnoseIntent);

            //If sign-out image clicked then start up screen shown
        } else if (view.getId() == R.id.coach_sign_out_image) {
            Intent logoutIntent = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(logoutIntent);
            //If history image clicked launch the history screen
        } else if (view.getId() == R.id.history_image) {
            Intent allIncidentsIntent = new Intent(getApplicationContext(), AllIncidentsListView.class);
            startActivity(allIncidentsIntent);

        } else if
            //If player search image clicked launch the player list
        (view.getId() == R.id.player_search) {
            Intent searchPlayers = new Intent(getApplicationContext(), PlayerListViewActivity.class);
            startActivity(searchPlayers);
        } else if
            //If update button clicked proceed with update method
        (view.getId() == R.id.btn_updateCoachProfile) {
            updateCoachProfile();
            //If delete button clicked proceed with method
        } else if (view.getId() == R.id.btn_deleteCoachProfile) {
            deleteCoachProfile();
        }
    }
}