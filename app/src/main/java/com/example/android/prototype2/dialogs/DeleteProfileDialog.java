package com.example.android.prototype2.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.android.prototype2.R;

import static android.content.ContentValues.TAG;

//Custom alert sourced to handle the delete of profile
public class DeleteProfileDialog extends DialogFragment {

    private NoticeDialogListener mNoticeDialogListener;

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogInterface dialog);

        void onDialogNegativeClick(DialogFragment dialog);
    }

    //Custom constructor to initialise NoticeDialogListener
    public DeleteProfileDialog(NoticeDialogListener mNoticeDialogListener) {
        super();
        this.mNoticeDialogListener = mNoticeDialogListener;
    }

    //Empty constructor
    public DeleteProfileDialog() {
        super();
    }

    // Use this instance of the interface to deliver action events
    CustomDialog.NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        //remove the check that verifies if your activity has the DialogListener Attached because you want to attach it into your list view onClick()
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Builder class to build dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Log the delete dialog
        Log.d(TAG, "Alert Dialog");

        //Set the title of the dialog
        builder.setTitle(R.string.delete_title);

        //Set the message
        builder.setMessage(R.string.delete_message);

        //Set the text and action of the positive button
        builder.setPositiveButton(R.string.delete_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mNoticeDialogListener.onDialogPositiveClick(dialog);

            }
        })
                //Set the text and action of the negative button
                .setNegativeButton(R.string.delete_negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        // //Create the dialog
        return builder.create();
    }
}
