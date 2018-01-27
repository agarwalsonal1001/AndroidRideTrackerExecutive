package com.wondercars.executiveridetracker.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wondercars.executiveridetracker.BaseClasses.BaseActivity;
import com.wondercars.executiveridetracker.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callActivity(NavigationActivity.class);
    }
}
