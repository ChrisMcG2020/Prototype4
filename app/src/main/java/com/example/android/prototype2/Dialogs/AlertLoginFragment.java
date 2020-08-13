
package com.example.android.prototype2.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.android.prototype2.CoachLoginActivity;
import com.example.android.prototype2.PlayerLoginActivity;
import com.example.android.prototype2.R;

import static android.content.ContentValues.TAG;

/*an alert to direct the user to either the player or coach login*/

public class AlertLoginFragment extends DialogFragment {

    final Context context = getActivity();


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Log.d(TAG, "Alert Dialog");

        //Set the title of the dialog
        builder.setTitle("Choice");

        //Set the icon to display
        builder.setIcon(R.drawable.ic_baseline_sports_volleyball_24);
        //Set the message
        builder.setMessage("Choose an option...");

        //Set the text and action of the button
        builder.setPositiveButton("Player", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Choosing player starts PlayerLoginActivity
                final Intent intent = new Intent(getActivity(), PlayerLoginActivity.class);
                getActivity().startActivity(intent);
            }


        });

        //Set the text and action of the  button
        builder.setNegativeButton("Coach", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Choosing coach starts CoachLoginActivity
                final Intent intent = new Intent(getActivity(), CoachLoginActivity.class);
                getActivity().startActivity(intent);
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
