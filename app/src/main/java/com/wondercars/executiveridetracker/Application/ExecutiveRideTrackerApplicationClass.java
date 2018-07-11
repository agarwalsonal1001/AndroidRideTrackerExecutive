package com.wondercars.executiveridetracker.Application;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.wondercars.executiveridetracker.Database.DatabaseManager;
import com.wondercars.executiveridetracker.Database._BookingSlotsDB;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.BookingSlotsObj;


import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by umer on 8/11/17.
 */

public class ExecutiveRideTrackerApplicationClass extends Application {
    public static ExecutiveRideTrackerApplicationClass myInstance;
    public static boolean isGreaterKitkat = false;
    private Gson mGson;
    private static TelephonyManager tManager;
    public static long startTestDriveTime;
    @Override
    public void onCreate() {
        super.onCreate();
        myInstance = this;
        isGreaterKitkat = versionK();
        mGson = new Gson();
        tManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        PreferenceManager.setSharedPreferences();
        DatabaseManager.getInstance(getApplicationContext());
        _BookingSlotsDB.getInstance(getApplicationContext());
    }

    public static ExecutiveRideTrackerApplicationClass getInstance() {

        //Environment.configure(Environment.PRODUCTION, Constants.MerchantId.MERCHANT_ID);
        return ExecutiveRideTrackerApplicationClass.myInstance;
    }

    public Gson getGsonObject() {
        return mGson;
    }

    public String getIMEINumber(Context context) {
        String identifier = null;
        if (PreferenceManager.readString(PreferenceManager.PREF_DEVICE_IDENTIFICATION_ID).equalsIgnoreCase("")) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                UUID uuid = UUID.randomUUID();
                identifier = uuid.toString();
                PreferenceManager.writeString(PreferenceManager.PREF_DEVICE_IDENTIFICATION_ID, identifier);
            } else {
                identifier = tManager.getDeviceId();
                PreferenceManager.writeString(PreferenceManager.PREF_DEVICE_IDENTIFICATION_ID, identifier);
            }

        } else {
            identifier = PreferenceManager.readString(PreferenceManager.PREF_DEVICE_IDENTIFICATION_ID);
        }
        return identifier;
    }

    private static boolean versionK() {
        boolean isGreaterKitkat = false;
        try {
            int currentapiVersion = Build.VERSION.SDK_INT;
            if (currentapiVersion >= Build.VERSION_CODES.KITKAT) {
                isGreaterKitkat = true;
            }
            //export PATH=$PATH{}:/path/to/android-sdk/tools:/Android/Sdk/platform-tools
        } catch (Exception e) {
            e.printStackTrace();
            return isGreaterKitkat;
        }
        return isGreaterKitkat;
    }


    public static ArrayList getTimeSlotsList() {
        ArrayList<BookingSlotsObj> timeList = new ArrayList<>();
        for (int k = 0; k < 24; k++) {
            BookingSlotsObj bookingSlotsObj = new BookingSlotsObj();
            bookingSlotsObj.setFromTime(k + ":00");
            bookingSlotsObj.setToTime((k + 1) + ":00");

            timeList.add(bookingSlotsObj);
        }
        return timeList;

    }

    public static ArrayList getTypesOfRide(){
        ArrayList<String> typesOfRide = new ArrayList<>();
        typesOfRide.add("Document Pick-up");
        //typesOfRide.add("Test Drive");
        typesOfRide.add("Customer Visit");
        return typesOfRide;
    }


    public static ArrayList getTypesOfVehicle(){
        ArrayList<String> typesOfVehicle = new ArrayList<>();
        typesOfVehicle.add("Own Vehicle");
        //typesOfRide.add("Test Drive");
        typesOfVehicle.add("Company Vehicle");
        return typesOfVehicle;
    }


}
