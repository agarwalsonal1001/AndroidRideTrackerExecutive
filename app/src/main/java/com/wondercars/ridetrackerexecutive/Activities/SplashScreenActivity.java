package com.wondercars.ridetrackerexecutive.Activities;

import android.os.Bundle;
import android.os.Handler;

import com.wondercars.ridetrackerexecutive.BaseClasses.BaseActivity;
import com.wondercars.ridetrackerexecutive.Manager.PreferenceManager;
import com.wondercars.ridetrackerexecutive.R;


public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler ha = new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                //call function
                if(PreferenceManager.readBoolean(PreferenceManager.PREF_LOGIN_CURRENT_TAG)){
                    callActivity(NavigationActivity.class);
                    finish();
                }else {
                    callActivity(LoginActivity.class);
                    finish();
                }

            }
        }, 3100);
    }

    // demo change
}
