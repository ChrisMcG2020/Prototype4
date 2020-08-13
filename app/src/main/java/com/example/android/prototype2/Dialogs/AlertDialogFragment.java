package com.example.android.prototype2.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.android.prototype2.R;

import static android.content.ContentValues.TAG;

/*an alert to instruct the user that professional medical advice is the only way
                to safely assess a concussion*/

public class AlertDialogFragment extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Log.d(TAG, "Alert Dialog");

        //Set the title of the dialog
        builder.setTitle(getString(R.string.warning_title));

        //Set the icon to display
        builder.setIcon(R.drawable.warning);

        //Set the message
        builder.setMessage(getString(R.string.medical_advice));

        //Set the text and action of the positive button
        builder.setPositiveButton("I understand", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });


        //Create the dialog
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
