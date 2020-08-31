package com.example.android.prototype2.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

//CustomDialog  sourced online to build dialog which is called in deleteProfile
public class CustomDialog extends DialogFragment {
    private NoticeDialogListener ndl;

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogInterface dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    //Custom constructor to  initialise NoticeDialogListener
    public CustomDialog(NoticeDialogListener ndl){
        super();
        this.ndl=ndl;
    }

    //Empty constructor
    public CustomDialog( ){
        super();
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        //remove the check that verifies if your activity has the DialogListener Attached because you want to attach it into your list view onClick()
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Set the title of the dialog
        builder.setTitle("Delete Profile")
                //Set the message
                .setMessage("Do you really want to delete your profile?")
                //Set the text and action of the positive button
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                     ndl.onDialogPositiveClick(dialog);

                    }
                })
                //Set the text and action of the negative button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}

