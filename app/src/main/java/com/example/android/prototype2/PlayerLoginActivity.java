package com.example.android.prototype2;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PlayerLoginActivity extends AppCompatActivity {


    //Declare the variables
    private TextView regLink;
    private TextInputLayout loginEmail, loginPass, loginPhoneNo;
    private Button forgotPass, register;

    //Firebase variables
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseDatabase mAuth;

    ProgressBar progressBar;


    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set layout
        setContentView(R.layout.activity_login);

        //Initialise the variables to their corresponding views
        loginEmail = findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_password);
        forgotPass = findViewById(R.id.forgot_pass_btn);
        register = findViewById(R.id.player_reg_btn);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        //regLink textView when click allows user to register
        regLink = findViewById(R.id.reglink_text_view);


        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
    }

    public void loginUser(View view) {
        //Validate Login Info
        //If email or password doesnt pass validation then errors are returned
        if (!validateLoginEmail() | !validateLoginPassword()) {
            return;
        } else {
            //method to retrieve users data from Firebase
            isUser();
        }

    }


    //Validation for email
    private Boolean validateLoginEmail() {
        String entry = loginEmail.getEditText().getText().toString();
        //If left blank error shown
        if (entry.isEmpty()) {
            loginEmail.setError("Email field cannot be empty");
            return false;
        } else {
            loginEmail.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            loginEmail.setErrorEnabled(false);
            return true;
        }

    }

    //Validation for password
    private Boolean validateLoginPassword() {
        String entry = loginPass.getEditText().getText().toString();
        //If left blank error shown

        if (entry.isEmpty()) {
            loginPass.setError("Password field cannot be empty");
            return false;
        } else {
            loginPass.setError(null);
            //setErrorEnabled(false) ensures layout will not change size when an error is displayed
            loginPass.setErrorEnabled(false);
            return true;
        }
    }

    //Method to check the details against the firebase database and retrieve the information if present
    public void isUser() {
        //Retrieve the user entered details
        final String userEnteredEmail = loginEmail.getEditText().getText().toString().trim();
        final String userEnteredPassword = loginPass.getEditText().getText().toString().trim();

        rootNode = FirebaseDatabase.getInstance();
        //Define the reference in the database that should be called
        reference = rootNode.getReference("Users");
        //Tag for printing log details
        Log.d(TAG, "Firebase contacted");

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //   final String user = currentFirebaseUser.getUid();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();


        final Query[] checkUser = {reference.orderByChild("email").equalTo(userEnteredEmail)};

        mAuth.signInWithEmailAndPassword(userEnteredEmail, userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                final String user = currentFirebaseUser.getUid();

             /*   checkUser[0].addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        //If data exists inside the snapshot
                        if (snapshot.exists()) {


                            //Retrieve all information needed to fill the profile page

                            String dbEmail = snapshot.child(user).child("email").getValue(String.class);
                            String dbPhoneNumber = snapshot.child(user).child("phoneNo").getValue(String.class);
                            String dbName = snapshot.child(user).child("name").getValue(String.class);


                            //Move to the UserProfile screen
                            Intent intent = new Intent(getApplicationContext(), UserProfile.class);

                            //Pass the information to the UserProfile screen
                            intent.putExtra("name", dbName);
                            intent.putExtra("phoneNo", dbPhoneNumber);
                            intent.putExtra("email", dbEmail);


                        }*/

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), UserProfile.class));
                } else {
                    loginEmail.setError("Email/Password Incorrect");
                    loginPass.setError("Email/Password Incorrect");
                    //    Toast.makeText(getApplicationContext(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
/*
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
*/
        });
    }


    protected void forgotPassword() {

        final String entry = loginEmail.getEditText().getText().toString();
        final String emailCharacters = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();


        if (entry.isEmpty()) {
            loginEmail.setError("Email field cannot be empty");

        } else if (!entry.matches(emailCharacters)) {
            loginEmail.setError("Invalid email address");
        } else {

            progressBar.setVisibility(View.VISIBLE);

            mAuth.sendPasswordResetEmail(entry)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Reset password email sent!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error! Reset email not sent", Toast.LENGTH_SHORT).show();
                            }

                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }
    }


    //Method to direct user to appropriate page
    public void onButtonClicked(View view) {
        if (view.getId() == R.id.player_reg_btn) {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intent);

        }
    }


}
     /*   else if (view.getId()== R.id.forgot_pass_btn) {
            Button forgotPass = findViewById(R.id.forgot_pass_btn);
            final String entry = loginEmail.getEditText().getText().toString();
            final String emailCharacters = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            final FirebaseAuth mAuth = FirebaseAuth.getInstance();


            if (entry.isEmpty()) {
                loginEmail.setError("Email field cannot be empty");

            } else if (!entry.matches(emailCharacters)) {
                loginEmail.setError("Invalid email address");
            } else {

                progressBar.setVisibility(View.VISIBLE);

                mAuth.sendPasswordResetEmail(entry)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Reset password email sent!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error! Reset email not sent", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });

        }
    }
    }
}

            /*forgotPass = findViewById(R.id.forgot_pass_btn);
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            final String userEnteredEmail = loginEmail.getEditText().getText().toString().trim();

            mAuth.sendPasswordResetEmail(userEnteredEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Reset email sent", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error, email link not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            });
        }*/








        /*Query matches any email in the database with the one entered by the user
        Query checkUser[0] = reference.orderByChild("email").equalTo(userEnteredEmail);

        checkUser[0].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //If data exists inside the snapshot
                if (snapshot.exists()) {

                    //Remove errors as data exists
                    loginEmail.setError(null);
                    loginEmail.setErrorEnabled(false);

                    //get the password of the user entered email address
                    String dbPhoneNumber = snapshot.child(userEnteredEmail).child("phoneNo").getValue(String.class);
                    String dbPassword = snapshot.child(dbPhoneNumber).child("password").getValue(String.class);

                    //Check if the password in the database matches the password entered by the user
                    assert dbPassword != null;
                    if (dbPassword.equals(userEnteredPassword)) {
                        Log.d(TAG, "Password matches");

                        //Remove errors as password matches
                        loginPass.setError(null);
                        loginPass.setErrorEnabled(false);


                        //Retrieve all information needed to fill the profile page

                        String dbEmail = snapshot.child(userEnteredEmail).child("email").getValue(String.class);
                        String dbEmergencyContact = snapshot.child(userEnteredEmail).child("emergencyContact").getValue(String.class);
                        String dbName = snapshot.child(userEnteredEmail).child("name").getValue(String.class);

                        //Move to the UserProfile screen
                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);

                        //Pass the information to the UserProfile screen
                        intent.putExtra("name", dbName);
                        intent.putExtra("phoneNo", dbPhoneNumber);
                        intent.putExtra("email", dbEmail);

                        //Start the UserProfile activity
                        startActivity(intent);


                        //If issue with password
                    } else {
                        //Wrong password error
                        loginPass.setError("Wrong password entered");
                        //requestFocus highlights the field where the error exists
                        loginPass.requestFocus();
                    }
                } else
                    //user does not exist
                    loginPass.setError("User email address doesnt not exist");
                loginPass.requestFocus();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


    /*        protected void forgotPassword() {
                loginEmail = findViewById(R.id.reg_email);
                String email = loginEmail.getEditText().getText().toString().trim();

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Reset email sent", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Email sent.");
                                }
                            }
                        });

            }

        @Override
        public void onClick(View v){
             forgotPassword();
        }
    }

*/