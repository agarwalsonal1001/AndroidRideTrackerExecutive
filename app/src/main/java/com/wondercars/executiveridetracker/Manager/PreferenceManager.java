package com.wondercars.executiveridetracker.Manager;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.wondercars.executiveridetracker.Application.ExecutiveRideTrackerApplicationClass;


/**
 * Created by acer on 18/11/17.
 */

public class PreferenceManager {

    private static final String APP_ID = "RideTracker_pref";
    private static final int WORLD_READABLE = Activity.MODE_PRIVATE;


    public static final String PREF_LOGIN_CURRENT_TAG = "PREF_LOGIN_CURRENT_TAG";
    public static final String PREF_NAVIGATION_FLAG = "PREF_NAVIGATION_FLAG";
    public static final String PREF_ADMIN_UID = "PREF_ADMIN_UID";
    public static final String PREF_DEVICE_IDENTIFICATION_ID = "PREF_DEVICE_IDENTIFICATION_ID";
    public static final String PREF_NOTIFICATION_DEVICE_TOKEN = "PREF_NOTIFICATION_DEVICE_TOKEN";
    public static final String PREF_INDIVISUAL_ID = "PREF_INDIVISUAL_ID";
    public static final String PREF_INDIVISUAL_CONTACT_NUMBER = "PREF_INDIVISUAL_CONTACT_NUMBER";
    public static final String PREF_SESSION_ID = "PREF_SESSION_ID";
    public static final String PREF_RIDECUSTOMER_IFO = "PREF_RIDECUSTOMER_IFO";
    public static final String PREF_RIDE_ID = "PREF_RIDE_ID";
    public static final String PREF_TESTDRIVE_ID = "PREF_TESTDRIVE_ID";
    private static SharedPreferences pref;

    public static void setSharedPreferences() {
        pref = ExecutiveRideTrackerApplicationClass.getInstance().getApplicationContext().getSharedPreferences(APP_ID,
                WORLD_READABLE);
    }


    public static boolean readBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public static void writeBoolean(String key, boolean val) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, val);
        editor.commit();
    }

    public static String readString(String key) {
        return pref.getString(key, "");
    }

    public static void writeString(String key, String val) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public static int readInteger(String key) {
        return pref.getInt(key, 0);
    }

    public static void writeInteger(String key, int val) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, val);
        editor.commit();
    }

    public static String getObjectToString(Object mObject) {
        Gson mGson = (ExecutiveRideTrackerApplicationClass.getInstance()).getGsonObject();
        return mGson.toJson(mObject);
    }

    public static Object getStringToObject(String stringToConvert, Class<?> cls) {
        Gson mGson = ExecutiveRideTrackerApplicationClass.getInstance().getGsonObject();
        Object mObject = mGson.fromJson(stringToConvert, cls);
        return mObject;
    }

    public static void clearAllPreference() {
        //SharedPreferences preferences = pref.c
        pref.edit().clear().commit();
    }
}
