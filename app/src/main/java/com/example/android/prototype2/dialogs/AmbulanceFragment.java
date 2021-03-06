package com.example.android.prototype2.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.android.prototype2.R;

import static android.content.ContentValues.TAG;



/*an alert to instruct the user that professional medical advice is the only way
                to safely assess a concussion*/

public class AmbulanceFragment extends DialogFragment {
    final Context context = getActivity();


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Log.d(TAG, "Alert Dialog");
        //Set the title of the dialog
        builder.setTitle(R.string.ambulance_title);

        //Set the message
        builder.setMessage(R.string.ambulance_message);

        //Set the icon to display
        builder.setIcon(R.drawable.warning);

        //Set the text and action of the button
        builder.setPositiveButton(R.string.ambulance_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Start a new intent to call using the dialler
                Intent call = new Intent(Intent.ACTION_DIAL);
                String number = "tel:" + getString(R.string.phone_number);
                //Set 999 as the number
                call.setData(Uri.parse(number));
                startActivity(call);
                Log.d(TAG, "Call button pressed and keypad launched");

            }


        });

        //Set the text and action of the  button
        builder.setNegativeButton(R.string.ambulance_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }


        });

        //Build the alert
        return builder.create();

    }

    //Methods for onCancel, onDismiss and onSaveInstanceState to handle lifecycle changes
    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(TAG, "onCancel:called");

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(TAG, "On dismiss called");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }


}

