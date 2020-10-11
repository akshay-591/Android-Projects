package com.kumar.akshay.tasktimer;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AppDialog extends DialogFragment {
    private static final String TAG = "AppDialog";

    //Declaring constant
    public static final String DIALOG_ID = "id";
    public static final String DIALOG_MESSAGE = "Message";
    public static final String DIALOG_POSITIVE_ID = "positive_id";
    public static final String DIALOG_NEGATIVE_ID = "negative_id";

    /**
     * this dialog callback interface notify the calling activity/ fragment about the user input
     */
    interface DialogEvents{
        public void onPositive(int dialogID, Bundle args);
        public void onNegative(int dialogID, Bundle args);
        public void onCancelled(int dialogID, Bundle args);
    }
private DialogEvents dialogEvents;


    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach: called");
        super.onAttach(context);
        //checking if context contain the instance of DialogEvents or Not
        if(!(context instanceof DialogEvents)) throw new ClassCastException(context.toString()+"must implements the methods of DialogEvents Interface");
        dialogEvents = (DialogEvents)context;
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: called");
        super.onDetach();
        dialogEvents=null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog: called");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //geting Argument
        final Bundle arguments = getArguments();
        //Declaring vaaribale which will be used in Dialog
        final int dialogId;
        String messageString;
        int positiveStringID ;
        int negativeStringID;
        //checking if Bundle object is null or not
        if(arguments!=null) {
            //if not null get the Input provided by the calling activity/fragment
            dialogId = arguments.getInt(DIALOG_ID);
            messageString = arguments.getString(DIALOG_MESSAGE);
            positiveStringID = arguments.getInt(DIALOG_POSITIVE_ID);

            //setting the buttons name according to the Input received
            if(positiveStringID==0){
                positiveStringID=R.string.yes;
            }
            negativeStringID = arguments.getInt(DIALOG_NEGATIVE_ID);
            if(negativeStringID==0){
                negativeStringID=R.string.no;
            }

            //if Bundle object is null throw exception
        }else throw new IllegalStateException("must pass Dialog ID and Dialog Message in the Bundle");

        //Setting the Input in Dialog Box
        builder.setMessage(messageString) //Setting Message to Show
                .setPositiveButton(positiveStringID, new DialogInterface.OnClickListener() { //setting Positive Button Name and Its Listener
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //callback onPositive method and Pass dialogId and Bundle
                        dialogEvents.onPositive(dialogId,arguments);
                    }
                })
                .setNegativeButton(negativeStringID, new DialogInterface.OnClickListener() {//setting Negative Button Name and Its Listener
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //callback onNegative method and pass dialogId and Bundle
                        dialogEvents.onNegative(dialogId,arguments);
                    }
                });
        //returning created Dialog to show
        return builder.create();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        Log.d(TAG, "onCancel: called");
        super.onCancel(dialog);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Log.d(TAG, "onDismiss: called");
        super.onDismiss(dialog);
    }
}
