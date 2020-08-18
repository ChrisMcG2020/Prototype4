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
        coachNameTextView = findViewById(R.id.edit_text_profile_coach_name);
        coachPhoneNoTextView = findViewById(R.id.edit_text_coach_profile_phone);
        coachEmailTextView = findViewById(R.id.edit_text_coach_profile_email);
        displayCoachName = findViewById(R.id.coach_name);
        displayCoachphone = findViewById(R.id.coach_name_small);
        teamCoachedTextView = findViewById(R.id.edit_text_coach_team_coached);

        updateCoach = findViewById(R.id.btn_updateCoachProfile);
        deleteCoach = findViewById(R.id.btn_deleteCoachProfile);


        final String uid = user.getUid();

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
                    Log.d (TAG,"TEST__coachName: "+name);
                    Log.d (TAG,"TEST__coachPhone: "+phone);
                    Log.d (TAG,"TEST__coachEmail: "+email);
                    Log.d (TAG,"TEST__teamCoached: "+teamCoached);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateCoach.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String updateName = coachNameTextView.getText().toString();
                final String updateEmail = coachEmailTextView.getText().toString();
                final String updatePhone = coachPhoneNoTextView.getText().toString();
                final String updateTeamCoached = teamCoachedTextView.getText().toString();


                user.updateEmail(updateEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        firebaseDatabase.getReference("Coaches").child(uid).child("coachName").setValue(updateName);
                        displayCoachName.setText(updateName);
                        firebaseDatabase.getReference("Coaches").child(uid).child("coachEmail").setValue(updateEmail);
                        firebaseDatabase.getReference("Coaches").child(uid).child("coachPhoneNumber").setValue(updatePhone);
                        displayCoachphone.setText(updatePhone);
                        firebaseDatabase.getReference("Coaches").child(uid).child("teamCoached").setValue(updateTeamCoached);

                        Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();

                        //Logs for testing updates in Realtime DB
                        Log.d (TAG,"TEST__updated_coachName: "+updateName);
                        Log.d (TAG,"TEST__updated_coachPhone: "+updatePhone);
                        Log.d (TAG,"TEST__updated_coachEmail: "+updateEmail);
                        Log.d (TAG,"TEST__updated_teamCoached: "+updateTeamCoached);


                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteCoach.setOnClickListener(new View.OnClickListener() {
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
                                              deleteUser.show(CoachProfile.this.getSupportFragmentManager(), "DialogFragment");
                                          }
                                      }
        );
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

    public void onButtonClicked(View view) {
        if (view.getId() == R.id.diagnose_concussion_button) {
            Intent diagnoseIntent = new Intent(getApplicationContext(), PlayerListViewActivity.class);
            startActivity(diagnoseIntent);


            } else if (view.getId() == R.id.btn_logoutCoachProfile) {
                Intent logoutIntent = new Intent(getApplicationContext(), SplashScreen.class);
                startActivity(logoutIntent);

            } else if (view.getId() == R.id.history_image) {
                Intent allIncidentsIntent = new Intent(getApplicationContext(), AllIncidentsListView.class);
                startActivity(allIncidentsIntent);

            } else if
            (view.getId() == R.id.player_info_pic) {
                Intent searchPlayers = new Intent(getApplicationContext(), PlayerListViewActivity.class);
                startActivity(searchPlayers);
            }
        }
    }
