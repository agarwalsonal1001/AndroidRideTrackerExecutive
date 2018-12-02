package com.wondercars.ridetrackerexecutive.Utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by acer on 28/1/18.
 */

public class AppAlertDialog {

    public static void showAlertDialog(Activity activity, String title, String message, boolean isDialogCancelable ,String possitiveButtonTitle, String negativeButtonTitle,
                                       DialogInterface.OnClickListener possitiveOnClickListener, DialogInterface.OnClickListener negatitiveOnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage(message)
                .setCancelable(isDialogCancelable)
                .setPositiveButton(possitiveButtonTitle, possitiveOnClickListener)
                .setNegativeButton(negativeButtonTitle, negatitiveOnClickListener);

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(title);
        alert.show();
    }
}
