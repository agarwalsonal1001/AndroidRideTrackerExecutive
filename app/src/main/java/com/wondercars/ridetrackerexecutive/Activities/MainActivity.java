package com.wondercars.ridetrackerexecutive.Activities;

import android.os.Bundle;

import com.wondercars.ridetrackerexecutive.BaseClasses.BaseActivity;
import com.wondercars.ridetrackerexecutive.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callActivity(NavigationActivity.class);
    }
}
