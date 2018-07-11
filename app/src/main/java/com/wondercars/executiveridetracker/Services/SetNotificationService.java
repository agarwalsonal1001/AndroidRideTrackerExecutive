package com.wondercars.executiveridetracker.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.wondercars.executiveridetracker.BroadCastReceiver.AlarmReceiver;
import com.wondercars.executiveridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.executiveridetracker.Database._BookingSlotsDB;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.BookingSlotsObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.GetSlotsRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.GetSlotsResponseObj;
import com.wondercars.executiveridetracker.Utils.APIConstants;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;
import umer.accl.utils.DateUtils;

import static android.text.TextUtils.isEmpty;

public class SetNotificationService extends Service {

    Handler handler;

    public SetNotificationService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        handler.postDelayed(runnable, 10000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setAlarm();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void setAlarm() {
        ArrayList<BookingSlotsObj> bookingSlotsObjs = _BookingSlotsDB.getInstance(this).getbookingSlotssList();
        for (BookingSlotsObj bookingSlotsObj : bookingSlotsObjs) {

            if (bookingSlotsObj != null) {
                calculateDifference(bookingSlotsObj.getFromTime());
            }
        }

        handler.postDelayed(runnable, 100000);

    }


    private void calculateDifference(String bookingDateTime) {
        if (!isEmpty(bookingDateTime)) {
            java.util.Date date = new java.util.Date();
            Timestamp timestamp1 = new Timestamp(date.getTime());

       /* // create a calendar and assign it the same time
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp1.getTime());

        // add a bunch of seconds to the calendar
        cal.add(Calendar.SECOND, 98765);*/


            // create a  second time stamp
            Timestamp timestamp2 = new Timestamp(DateUtils.getStringToDate(bookingDateTime, DateUtils.MMM_DD_YYYY_hh_mm_ss_a_DATE_FORMAT).getTime());

            // get time difference in seconds
            long milliseconds = timestamp1.getTime() - timestamp2.getTime();
            int seconds = (int) milliseconds / 1000;

            // calculate hours minutes and seconds
            int hours = seconds / 3600;
            int minutes = (seconds % 3600) / 60;
            seconds = (seconds % 3600) % 60;

            if (minutes > 15 && minutes < 20) {
                Intent intent = new Intent();
                intent.setAction("com.wondercars.executiveridetracker.BroadCastReceiver.AlarmReceiver");
                sendBroadcast(intent);
            }
        }
    }
}
