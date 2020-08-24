package com.example.android.prototype2.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.AddReportActivity;
import com.example.android.prototype2.R;
import com.example.android.prototype2.dialogs.AmbulanceFragment;


public class MemoryActivity extends AppCompatActivity {

    //Checkbox variables
    CheckBox q1Yes, q1No, q2Yes, q2No, q3Yes, q3No, q4Yes, q4No, q5Yes, q5No;

    //Intent strings
    String intentUid4, intentRedFlag4, intentObs4, intentEmail4, intentName4, intentSymptoms4;

    //Variable for storing the result of the memory questions
    int result = 0;

    //Variable for storing result
    String memoryResult;

    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set layout
        setContentView(R.layout.activity_memory);

        //Initialise the variables to their corresponding views
        q1Yes = findViewById(R.id.memory_q1_yes);
        q1No = findViewById(R.id.memory_q1_no);
        q2Yes = findViewById(R.id.memory_q2_yes);
        q2No = findViewById(R.id.memory_q2_no);
        q3Yes = findViewById(R.id.memory_q3_yes);
        q3No = findViewById(R.id.memory_q3_no);
        q4Yes = findViewById(R.id.memory_q4_yes);
        q4No = findViewById(R.id.memory_q4_no);
        q5Yes = findViewById(R.id.memory_q5_yes);
        q5No = findViewById(R.id.memory_q5_no);


    }

    public void quizDiagnosis() {
        //Retrieve the previous results and assign them
        intentUid4 = getIntent().getStringExtra("uid3");
        intentEmail4 = getIntent().getStringExtra("email3");
        intentName4 = getIntent().getStringExtra("name3");
        intentRedFlag4 = getIntent().getStringExtra("redFlag3");
        intentObs4 = getIntent().getStringExtra("obs3");
        intentSymptoms4 = getIntent().getStringExtra("symptom3");


        //If statements for each question and update result based on answer
        if (q1Yes.isChecked()) result = result + 1;
        if (q1No.isChecked()) result = result - 1;

        if (q2Yes.isChecked()) result = result + 1;
        if (q2No.isChecked()) result = result - 1;

        if (q3Yes.isChecked()) result = result + 1;
        if (q3No.isChecked()) result = result - 1;

        if (q4Yes.isChecked()) result = result + 1;
        if (q4No.isChecked()) result = result - 1;

        if (q5Yes.isChecked()) result = result + 1;
        if (q5No.isChecked()) result = result - 1;



        //Less than 1 then test is a fail
        if (result < 1) memoryResult = "Memory Activity: Failed";
        else {
            //Otherwise it is a pass
            memoryResult = "Memory Activity: Passed";
        }

    }

   //Method to make sure correct number of boxes ticked
        public boolean validateQuestions(){
        //Initialise number of ticked boxes to 0
        int noOfBoxes=0;
        //If statements for each question and update number of boxes ticked
        if (q1Yes.isChecked()) noOfBoxes=noOfBoxes+1;
        if (q1No.isChecked()) noOfBoxes=noOfBoxes+1;
        if (q2Yes.isChecked()) noOfBoxes=noOfBoxes+1;
        if (q2No.isChecked()) noOfBoxes=noOfBoxes+1;
        if (q3Yes.isChecked()) noOfBoxes=noOfBoxes+1;
        if (q3No.isChecked()) noOfBoxes=noOfBoxes+1;
        if (q4Yes.isChecked()) noOfBoxes=noOfBoxes+1;
        if (q4No.isChecked()) noOfBoxes=noOfBoxes+1;
        if (q5Yes.isChecked()) noOfBoxes=noOfBoxes+1;
        if (q5No.isChecked()) noOfBoxes=noOfBoxes+1;

        //If greater than 5 show error toast
        if (noOfBoxes >5){
            Toast.makeText(getApplicationContext(),"Too many boxes ticked, try again", Toast.LENGTH_LONG).show();
            Log.d(TAG,"Too many boxes");
            return false;
        }
        //If less than 5 show error toast
        if (noOfBoxes<5){
            Toast.makeText(getApplicationContext(),"Too few boxes ticked, try again", Toast.LENGTH_LONG).show();
            Log.d(TAG,"Too few boxes");
            return false;
        }
        return true;
    }

    //Method to direct button clicks to correct action
    public void onButtonClicked(View view) {
        //If the callAmbulance button clicked, launch that activity
        if (view.getId() == R.id.memory_call_ambulance) {
            AmbulanceFragment callAmbulance = new AmbulanceFragment();
            callAmbulance.setCancelable(false);
            callAmbulance.show(getSupportFragmentManager(), "Fragment Alert Dialog");

        }
        //If continue button is clicked run the diagnosis and then launch the next activity
        else if (view.getId() == R.id.memory_continue_btn) {
            //Run the memory quiz method
            quizDiagnosis();
            //Check the correct number of boxes ticked
            if(!validateQuestions()){
                return;}
            //Pass the intents to the next activity
            Intent memoryIntent = new Intent(getApplicationContext(), AddReportActivity.class);
            memoryIntent.putExtra("uid4", intentUid4);
            memoryIntent.putExtra("email4", intentEmail4);
            memoryIntent.putExtra("name4", intentName4);
            memoryIntent.putExtra("redFlag4", intentRedFlag4);
            memoryIntent.putExtra("obs4", intentObs4);
            memoryIntent.putExtra("symptom4", intentSymptoms4);
            memoryIntent.putExtra("memory4", memoryResult);
            //Start the next activity
            startActivity(memoryIntent);


        }
    }
}

