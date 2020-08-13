package com.example.android.prototype2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private DatabaseReference  databaseReference, databaseReferenceUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;



    private String intent_uid5, intent_RedFlag5, intent_Obs5, intent_Memory5, intent_Symptoms5, intent_Email5, incidentDate, intent_Name5;



    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set layout
        setContentView(R.layout.add_report);

        //Assign views to variables
        incidentReport = this.findViewById(R.id.report_edit_text);
        validateReport();


     /*   intent_Name5 = getIntent().getStringExtra("name4");
        intent_uid5 = getIntent().getStringExtra("uid4");
        intent_Email5 = getIntent().getStringExtra("email4");*/

        //Calculating date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        incidentDate = simpleDateFormat.format(calendar.getTime());

        //Get the current user (coach)
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();



        //Retrieve the incident node from the firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("Incidents").child("Player_incidents");
        //Get a reference to the coach compiling the report
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Coaches");
        databaseReferenceUser.orderByChild("coachEmail").equalTo(currentUser.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    //Retrieve the information
                    //If no values added , show error toast
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

            }
        });
    }

    //Validation for report
    private Boolean validateReport() {
        String report = incidentReport.getText().toString();
        //If empty display error
        if (report.isEmpty()) {
            incidentReport.setError("Report field cannot be empty");
            return false;

        } else {
            //no Error
            incidentReport.setError(null);
            return true;
        }
    }

    private void saveReport() {
        if (validateReport()) {
            return;
        }

        //Pass the intents from previous activities
        intent_RedFlag5 = getIntent().getStringExtra("redFlag4");
        intent_Name5 = getIntent().getStringExtra("name4");
        intent_uid5 = getIntent().getStringExtra("uid4");
        intent_Email5 = getIntent().getStringExtra("email4");
        intent_RedFlag5 = getIntent().getStringExtra("redFlag4");
        intent_Obs5 = getIntent().getStringExtra("obs4");
        intent_Symptoms5 = getIntent().getStringExtra("symptom4");
        intent_Memory5 = getIntent().getStringExtra("memory4");
        reportIncident = incidentReport.getText().toString().trim();


        //Set a unique key for the incident
        String reportId = databaseReference.push().getKey();
        //Create a new player incidents object to store the details
        PlayerIncidentsModel playerIncidentsModel = new PlayerIncidentsModel();

        //Set the values from the report to the appropriate variable
        playerIncidentsModel.setUid(intent_uid5);
        Log.d(TAG, "UIDDDDDDDDDD+"+intent_uid5);
        playerIncidentsModel.setRed_FLag_Test(intent_RedFlag5);
        playerIncidentsModel.setObservable_Signs_Test(intent_Obs5);
        playerIncidentsModel.setMemory_Question(intent_Memory5);
        playerIncidentsModel.setPlayerEmail(intent_Email5);
        playerIncidentsModel.setReports(reportIncident);
        playerIncidentsModel.setDate(incidentDate);
        playerIncidentsModel.setCoachName(coachName);
        playerIncidentsModel.setSymptoms(intent_Symptoms5);
        playerIncidentsModel.setName(intent_Name5);


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
        if (view.getId() == R.id.report_continue) {
            saveReport();
            Intent coach_Intent = new Intent(getApplicationContext(), CoachAdviceActivity.class);
            startActivity(coach_Intent);
        }
    }


}