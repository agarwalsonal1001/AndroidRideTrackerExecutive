package com.wondercars.ridetrackerexecutive.CustomClasses;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;

import com.wondercars.ridetrackerexecutive.R;


/**
 * Created by acer on 9/12/17.
 */

public class ManageCarsAlertDialog {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AlertDialog createAlertDialog(final Activity activity) {

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        //  alert.setTitle("Apply Filter");
        alert.setView(R.layout.alertdialog_manage_cars);
        // this is set the view from XML inside AlertDialog
        //  alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        dialog.show();
        return dialog;
    }

}
