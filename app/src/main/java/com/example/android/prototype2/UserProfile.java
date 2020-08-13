package com.example.android.prototype2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class UserProfile extends AppCompatActivity {

    //Declare variables
    private ImageView recovery;
    private Button updateProfile, deleteUser, logoutUser;
    private TextInputEditText emailTextView, phoneNoTextView, nameTextView, emergencyContactTextView, emergencyContactPhoneNoTextView;
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
        setContentView(R.layout.activity_user_profile);

        //Firebase reference for the Users
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        final String uid = user.getUid();
        Log.d(TAG, "OnCreate USERUID" + uid);
        Intent intent = getIntent();
        playerEmail = intent.getStringExtra("email");


        //Initialise the variables to their corresponding views
        nameTextView = findViewById(R.id.edit_text_profile_full_name);
        phoneNoTextView = findViewById(R.id.edit_text_profile_phone);
        emailTextView = findViewById(R.id.edit_text_profile_email);
        displayName = findViewById(R.id.user_name);
        displayPhone = findViewById(R.id.user_phone_small);
        emergencyContactTextView = findViewById(R.id.edit_text_emergency_contact);
        emergencyContactPhoneNoTextView = findViewById(R.id.edit_text_profile_emergency_phone);
        incidents = findViewById(R.id.incident_text);
        updateProfile = findViewById(R.id.btn_updatePlayerProfile);
        logoutUser = findViewById(R.id.btn_logoutProfile);

        deleteUser = findViewById(R.id.btn_deleteProfile);


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

                    displayName.setText(name);

                    displayPhone.setText(phone);

                    nameTextView.setText(name);
                    Log.d (TAG,"TEST__name: "+name);
                    phoneNoTextView.setText(phone);
                    Log.d(TAG, "TEST_phone: " + phone);
                    emailTextView.setText(email);
                    Log.d (TAG,"TEST_email: "+email);
                    emergencyContactTextView.setText(emergencyContact);
                    Log.d (TAG,"TEST_EC: "+emergencyContact);
                    emergencyContactPhoneNoTextView.setText(contactNumber);
                    Log.d (TAG,"TEST_EC_phone: "+contactNumber);


                    Intent intent = new Intent(getIntent());
                    intent.putExtra("PlayerID", playerID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String updateName = nameTextView.getText().toString();
                final String updateEmail = emailTextView.getText().toString();
                final String updatePhone = phoneNoTextView.getText().toString();
                final String updateEmergencyContact = emergencyContactTextView.getText().toString();
                final String updateEC_Phone = emergencyContactPhoneNoTextView.getText().toString();


                user.updateEmail(updateEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        firebaseDatabase.getReference("Users").child(uid).child("name").setValue(updateName);
                        displayName.setText(updateName);
                        Log.d (TAG,"TEST_updated_name: "+updateName);
                        firebaseDatabase.getReference("Users").child(uid).child("email").setValue(updateEmail);
                        Log.d (TAG,"TEST_updated_email: "+updateEmail);
                        firebaseDatabase.getReference("Users").child(uid).child("phoneNo").setValue(updatePhone);
                        displayPhone.setText(updatePhone);
                        Log.d (TAG,"TEST_updated_phone: "+updatePhone);
                        firebaseDatabase.getReference("Users").child(uid).child("emergencyContact").setValue(updateEmergencyContact);
                        Log.d (TAG,"TEST_updated_EC: "+updateEmergencyContact);
                        firebaseDatabase.getReference("Users").child(uid).child("contactNumber").setValue(updateEC_Phone);
                        Log.d (TAG,"TEST_updated_EC_phone: "+updateEC_Phone);
                        Toast.makeText(getApplicationContext(), "UPDATED", Toast.LENGTH_LONG).show();

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteUser.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              // This is where the Dialog should be called and
                                              // the user input from the Dialog should be returned
                                              //

                                              // Here I would like to implement the interface of CustomNumberPicker
                                              // in order to get the user input entered in the Dialog
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
                                              deleteUser.show(UserProfile.this.getSupportFragmentManager(), "DialogFragment");
                                          }
                                      }
        );
    }


    private void deleteCurrentUser(String uid) {
        //Get a reference to the Users uid in the database
        DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        //Get a reference to ant incident data from the current user
        DatabaseReference databaseReferenceIncident = FirebaseDatabase.getInstance().getReference("Incidents").child("Player_incidents").child(uid);

        //Remove the user
        databaseReferenceUser.removeValue();

        //Remove the incident
        databaseReferenceIncident.removeValue();


        //Show a toast to say user has been removed
        Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_LONG).show();
        //Take the user back to the start up screen
        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
        startActivity(intent);


    }

    //Method to direct button clicks to correct action
    public void onButtonClicked(View view) {
        //If recover image clocked recover advice page launched
        if (view.getId() == R.id.recovery_image) {
            Intent intent = new Intent(getApplicationContext(), RecoveryActivity.class);
            startActivity(intent);
            //If incident image clicked then list of images returned
        } else if (view.getId() == R.id.incident_text) {
            Intent intent2 = new Intent(getApplicationContext(), IncidentListView.class);
            startActivity(intent2);
            //If logout button clicked then start up screen shown
        } else if (view.getId() == R.id.btn_logoutProfile) {
            Intent logoutIntent = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(logoutIntent);
        }
    }
}
