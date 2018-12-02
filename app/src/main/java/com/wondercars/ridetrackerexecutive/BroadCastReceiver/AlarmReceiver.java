package com.wondercars.ridetrackerexecutive.BroadCastReceiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context);
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
    }

    public void sendNotification(Context context) {

        //Get an instance of NotificationManager//

        android.support.v4.app.NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentTitle("Test Drive Notification")
                .setContentText("You have booking for Test Drive");
        // Toast.makeText(context,"Notification Send",Toast.LENGTH_LONG).show();


        // Gets an instance of the NotificationManager service//

        try {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder.setSound(alarmSound);
        } catch (Exception e) {
            e.printStackTrace();
        }
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, mBuilder.build());

        // When you issue multiple notifications about the same type of event,
        // it’s best practice for your app to try to update an existing notification
        // with this new information, rather than immediately creating a new notification.
        // If you want to update this notification at a later date, you need to assign it an ID.
        // You can then use this ID whenever you issue a subsequent notification.
        // If the previous notification is still visible, the system will update this existing notification,
        // rather than create a new one. In this example, the notification’s ID is 001//

        // NotificationManager.notify().mNotificationManager.notify(001, mBuilder.build());
    }
}
