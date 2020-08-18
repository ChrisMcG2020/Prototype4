package com.example.android.prototype2.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.android.prototype2.CustomDialog;

public class DeleteProfileDialog extends DialogFragment {

        private NoticeDialogListener mNoticeDialogListener;

        public interface NoticeDialogListener {
            public void onDialogPositiveClick(DialogInterface dialog);
            public void onDialogNegativeClick(DialogFragment dialog);
        }

        //add a custom constructor so that you have an initialised NoticeDialogListener
        public DeleteProfileDialog(NoticeDialogListener mNoticeDialogListener){
            super();
            this.mNoticeDialogListener=mNoticeDialogListener;
        }

        //make sure you maintain an empty constructor
        public DeleteProfileDialog( ){
            super();
        }

        // Use this instance of the interface to deliver action events
        CustomDialog.NoticeDialogListener mListener;

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
                        mNoticeDialogListener.onDialogPositiveClick(dialog);

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
}
