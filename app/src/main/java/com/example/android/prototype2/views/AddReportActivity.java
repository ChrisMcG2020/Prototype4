package com.example.android.prototype2.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.R;
import com.example.android.prototype2.dialogs.AmbulanceFragment;
import com.example.android.prototype2.helperClass.PlayerIncidentsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddReportActivity extends AppCompatActivity {

    //EditText and String variables
    private EditText incidentReport;
    private String reportIncident, coachName;

    //Firebase variables
    private DatabaseReference databaseReference, databaseReferenceUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;


    private String intentUid5, intentRedFlag5, intentObs5, intentMemory5, intentSymptoms5, intentEmail5, incidentDate, intentName5;


    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set layout
        setContentView(R.layout.activity_add_report);

        //Assign views to variables
        incidentReport = this.findViewById(R.id.report_edit_text);


        //Calculating date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        incidentDate = simpleDateFormat.format(calendar.getTime());

        //Get the current user (coach)
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();


        //Retrieve the incident node from the firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("Incidents");
        //Get a reference to the coach compiling the report
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Coaches");
        //AddValueEventListener to listen out for any reports from the coach currently logged in
        databaseReferenceUser.orderByChild("coachUID").equalTo(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    //Retrieve the information
                    //If no information found ,show error Toast
                    if (snapshot.getValue() == null) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                    } else {
                        //Get the name of the coach from the database and assign it to variable
                        coachName = (String) userSnapshot.child("coachName").getValue();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Show error Toast
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void saveReport() {


        //Pass the intents from previous activities
        intentRedFlag5 = getIntent().getStringExtra("redFlag4");
        intentName5 = getIntent().getStringExtra("name4");
        intentUid5 = getIntent().getStringExtra("uid4");
        intentEmail5 = getIntent().getStringExtra("email4");
        intentRedFlag5 = getIntent().getStringExtra("redFlag4");
        intentObs5 = getIntent().getStringExtra("obs4");
        intentSymptoms5 = getIntent().getStringExtra("symptom4");
        intentMemory5 = getIntent().getStringExtra("memory4");
        reportIncident = incidentReport.getText().toString().trim();

        //Log tags used for testing
        Log.d(TAG, "TEST_uid: " + intentUid5);
        Log.d(TAG, "TEST_Red_Flag: " + intentRedFlag5);
        Log.d(TAG, "TEST_Observable_Signs: " + intentObs5);
        Log.d(TAG, "TEST_Memory_Questions: " + intentMemory5);
        Log.d(TAG, "TEST_Symptoms: " + intentSymptoms5);
        Log.d(TAG, "TEST_Player_email: " + intentEmail5);
        Log.d(TAG, "TEST_Player_name: " + intentName5);
        Log.d(TAG, "TEST_Coach_Name: " + coachName);
        Log.d(TAG, "TEST_Report: " + reportIncident);
        Log.d(TAG, "TEST_Add_Report_Success: Passed");


        //Set a unique key for the incident
        String reportId = databaseReference.push().getKey();
        //Create a new player incidents object to store the details
        PlayerIncidentsModel playerIncidentsModel = new PlayerIncidentsModel();

        //Set the values from the report to the appropriate variable
        playerIncidentsModel.setUid(intentUid5);
        playerIncidentsModel.setRed_FLag_Test(intentRedFlag5);
        playerIncidentsModel.setObservable_Signs_Test(intentObs5);
        playerIncidentsModel.setMemory_Question(intentMemory5);
        playerIncidentsModel.setPlayerEmail(intentEmail5);
        playerIncidentsModel.setReports(reportIncident);
        playerIncidentsModel.setDate(incidentDate);
        playerIncidentsModel.setCoachName(coachName);
        playerIncidentsModel.setSymptoms(intentSymptoms5);
        playerIncidentsModel.setName(intentName5);


        assert reportId != null;
        //Set the full report details at the reportId path
        databaseReference.child(reportId).setValue(playerIncidentsModel);


        //Successful toast
        Toast.makeText(this, "Report saved", Toast.LENGTH_SHORT).show();
    }


    //Method to direct button clicks to correct action
   public void onButtonClicked(View view) {
        //Call ambulance clicked launches the CallAmbulance alert dialog
        if (view.getId() == R.id.report_call_ambulance) {
            //Create new instance of the fragment
            AmbulanceFragment callAmbulance = new AmbulanceFragment();
            callAmbulance.setCancelable(false);
            callAmbulance.show(getSupportFragmentManager(), "Fragment Alert Dialog");
        }
        //If report continued clicked launch method and then intent
        if (view.getId() == R.id.report_continue) {
            saveReport();
            Intent coach_Intent = new Intent(getApplicationContext(), CoachAdviceActivity.class);
            startActivity(coach_Intent);
        }
    }


}