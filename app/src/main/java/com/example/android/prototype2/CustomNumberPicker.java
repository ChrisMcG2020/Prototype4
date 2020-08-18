package com.example.android.prototype2;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class CustomNumberPicker extends DialogFragment {
    private NoticeDialogListener ndl;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogInterface dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    //add a custom constructor so that you have an initialised NoticeDialogListener
    public CustomNumberPicker(NoticeDialogListener ndl){
        super();
        this.ndl=ndl;
    }

    //make sure you maintain an empty constructor
    public CustomNumberPicker( ){
        super();
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //remove the check that verifies if your activity has the DialogListener Attached because you want to attach it into your list view onClick()
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Profile")
                .setMessage("Do you really want to delete your profile?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                     ndl.onDialogPositiveClick(dialog);

                    }
                })
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

