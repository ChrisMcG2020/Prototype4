package com.example.android.prototype2.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.prototype2.AddReportActivity;
import com.example.android.prototype2.Dialogs.AmbulanceFragment;
import com.example.android.prototype2.R;


public class QuizActivity extends AppCompatActivity {

    //Checkbox variables
    CheckBox q1_yes, q1_no, q2_yes, q2_no, q3_yes, q3_no, q4_yes, q4_no, q5_yes, q5_no;

    //Intent strings
    String intent_uid4, intent_RedFlag4, intent_Obs4, intent_email4,intent_name4, intent_Symptoms4;

    //Varibale for storing the result of the memory questions
    int result = 0;

    //Variable for storing result
    String memoryResult;

    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set layout
        setContentView(R.layout.quiz_activity);

        //Initialise the variables to their corresponding views
        q1_yes = findViewById(R.id.memory_q1_yes);
        q1_no = findViewById(R.id.memory_q1_no);
        q2_yes = findViewById(R.id.memory_q2_yes);
        q2_no = findViewById(R.id.memory_q2_no);
        q3_yes = findViewById(R.id.memory_q3_yes);
        q3_no = findViewById(R.id.memory_q3_no);
        q4_yes = findViewById(R.id.memory_q4_yes);
        q4_no = findViewById(R.id.memory_q4_no);
        q5_yes = findViewById(R.id.memory_q5_yes);
        q5_no = findViewById(R.id.memory_q5_no);


    }

    public void quizDiagnosis() {
        //Retrieve the previous results and assign them
        intent_uid4 = getIntent().getStringExtra("uid3");
        intent_email4 = getIntent().getStringExtra("email3");
        intent_name4=getIntent().getStringExtra("name3");
        intent_RedFlag4 = getIntent().getStringExtra("redFlag3");
        intent_Obs4 = getIntent().getStringExtra("obs3");
        intent_Symptoms4 = getIntent().getStringExtra("symptom3");


        //If statements for each question and update result based on answer
        if (q1_yes.isChecked()) result = result + 1;
        if (q1_no.isChecked()) result = result - 1;



          if (q2_yes.isChecked()) result = result + 1;
        if (q2_no.isChecked()) result = result - 1;

        if (q3_yes.isChecked()) result = result + 1;
        if (q3_no.isChecked()) result = result - 1;

        if (q4_yes.isChecked()) result = result + 1;
        if (q4_no.isChecked()) result = result - 1;

        if (q5_yes.isChecked()) result = result + 1;
        if (q5_no.isChecked()) result = result - 1;

        //Less than 1 then test is a fail
        if (result < 1) memoryResult = "Memory Activity: Failed";
        else {
            //Otherwise it is a pass
            memoryResult = "Memory Activity: Passed";
        }

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
            //Run the momory quiz method
            quizDiagnosis();
            //Pass the intents to the next activity
            Intent memory_Intent = new Intent(getApplicationContext(), AddReportActivity.class);
            memory_Intent.putExtra("uid4", intent_uid4);
            memory_Intent.putExtra("email4", intent_email4);
            memory_Intent.putExtra("name4", intent_name4);
            Log.d(TAG,"NAMSESES+"+intent_name4);
            memory_Intent.putExtra("redFlag4", intent_RedFlag4);
            memory_Intent.putExtra("obs4", intent_Obs4);
            memory_Intent.putExtra("symptom4", intent_Symptoms4);
            memory_Intent.putExtra("memory4", memoryResult);
            //Start the next activity
            startActivity(memory_Intent);


        }
    }
}

