package com.example.android.prototype2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

                        Toast.makeText(getApplicationContext(), "Profile updated", Toast.LENGTH_LONG).show();


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
            public void onClick(View view) {
                deleteCurrentUser(user.getUid());

            }
        });


    }

    private void deleteCurrentUser(String uid) {
        DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Coaches").child(uid);

        databaseReferenceUser.removeValue();

        Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
        startActivity(intent);


    }

    public void onButtonClicked(View view) {
        if (view.getId() == R.id.diagnose_concussion_button) {
            Intent diagnoseIntent = new Intent(getApplicationContext(), PlayerListViewActivity.class);
            startActivity(diagnoseIntent);


            } else if (view.getId() == R.id.btn_logoutCoachProfile) {
                Intent logoutIntent = new Intent(getApplicationContext(), SplashScreen.class);
                startActivity(logoutIntent);

            } else if (view.getId() == R.id.recovery_image) {
                Intent allIncidentsIntent = new Intent(getApplicationContext(), AllIncidentsListView.class);
                startActivity(allIncidentsIntent);

            } else if
            (view.getId() == R.id.incident_text) {
                Intent intent2 = new Intent(getApplicationContext(), PlayerListViewActivity.class);
                startActivity(intent2);
            }
        }
    }
