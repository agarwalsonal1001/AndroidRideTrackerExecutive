package com.wondercars.ridetrackerexecutive.BaseClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wondercars.ridetrackerexecutive.Application.ExecutiveRideTrackerApplicationClass;
import com.wondercars.ridetrackerexecutive.Manager.PreferenceManager;
import com.wondercars.ridetrackerexecutive.Utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import umer.accl.retrofit.RetrofitHeaders;

/**
 * Created by ubuntu-dev001 on 28/12/16.
 */
public class BaseActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    protected ExecutiveRideTrackerApplicationClass applicationClass;
    public static ArrayList<RetrofitHeaders> retrofitHeadersArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getApplicationContext();
        this.mActivity = this;
        setApplicationClassObject();
        //setUserInfo();
    }


    public void setActionBar(Toolbar toolbar, String title) {
        try {
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                if (TextUtils.isEmpty(title)) {
                    getSupportActionBar().setTitle("");
                } else {
                    getSupportActionBar().setTitle(title);
                }
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setActionBar(Toolbar toolbar, String title, boolean setHomeButtonEndable) {
        try {
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                if (TextUtils.isEmpty(title)) {
                    getSupportActionBar().setTitle("");
                } else {
                    getSupportActionBar().setTitle(title);
                }
                getSupportActionBar().setHomeButtonEnabled(setHomeButtonEndable);
                getSupportActionBar().setDisplayHomeAsUpEnabled(setHomeButtonEndable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSnackBar(String message, View.OnClickListener listener) {
        if (!TextUtils.isEmpty(message)) {
            final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
            snackBar.setAction("Dismiss", listener)
                    .setActionTextColor(Color.RED);
            View sbView = snackBar.getView();
            sbView.setBackgroundColor(Color.parseColor(AppConstants.ColorStrings.lightestBlue));
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.parseColor(AppConstants.ColorStrings.colorPrimaryDark));
            // textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP ,getResources().getDimension(R.dimen.text_size_18dp));
            snackBar.show();
        }
    }

    public void showLongSnackBar(String message) {
        if (!TextUtils.isEmpty(message)) {
            final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
            snackBar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                }
            })
                    .setActionTextColor(Color.RED);
            View sbView = snackBar.getView();
            sbView.setBackgroundColor(Color.parseColor(AppConstants.ColorStrings.lightestBlue));
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.parseColor(AppConstants.ColorStrings.colorPrimaryDark));
            //  textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP ,getResources().getDimension(R.dimen.text_size_18dp));
            snackBar.show();
        }
    }

    public void showSnackBar(String message) {
        if (!TextUtils.isEmpty(message)) {
            final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
            snackBar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                }
            })
                    .setActionTextColor(Color.RED);
            View sbView = snackBar.getView();
            sbView.setBackgroundColor(Color.parseColor(AppConstants.ColorStrings.lightestBlue));
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.parseColor(AppConstants.ColorStrings.colorPrimaryDark));
            //  textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP ,getResources().getDimension(R.dimen.text_size_18dp));
            snackBar.show();
        }
    }


    public void showShortToast(String s) {
        if (!TextUtils.isEmpty(s))
            Toast.makeText(this.mContext, s, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String s) {
        if (!TextUtils.isEmpty(s))
            Toast.makeText(this.mContext, s, Toast.LENGTH_LONG).show();
    }

    public void callActivity(Class<?> cls) {
        Intent callDestinationActivity = new Intent(this, cls);
        callDestinationActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(callDestinationActivity);
    }


    private void setApplicationClassObject() {
        applicationClass = (ExecutiveRideTrackerApplicationClass) getApplicationContext();
    }

    public String createUUId() {
        UUID uu = UUID.randomUUID();
        String uuid = uu.toString();
        return uuid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public List<RetrofitHeaders> getRetrofitHeaderses() {
        if (retrofitHeadersArrayList == null) {
            RetrofitHeaders retrofitHeaders = new RetrofitHeaders("uid", PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
            retrofitHeadersArrayList = new ArrayList<RetrofitHeaders>();
            retrofitHeadersArrayList.add(retrofitHeaders);
        }
        return retrofitHeadersArrayList;
    }
}
