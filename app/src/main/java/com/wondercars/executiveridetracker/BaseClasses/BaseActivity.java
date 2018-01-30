package com.wondercars.executiveridetracker.BaseClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.wondercars.executiveridetracker.Application.ExecutiveRideTrackerApplicationClass;

import java.util.UUID;

/**
 * Created by ubuntu-dev001 on 28/12/16.
 */
public class BaseActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    protected ExecutiveRideTrackerApplicationClass applicationClass;


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

    public void showShortToast(String s) {
        Toast.makeText(this.mContext, s, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String s) {
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
}
