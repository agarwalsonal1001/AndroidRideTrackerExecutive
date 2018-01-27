package com.wondercars.executiveridetracker.BaseClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.wondercars.executiveridetracker.Application.ExecutiveRideTrackerApplicationClass;

import java.util.UUID;

/**
 * Created by ubuntu-dev003 on 10/3/17.
 */

public class BaseFragment extends Fragment {
    private ExecutiveRideTrackerApplicationClass applicationClass;

    public BaseFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setApplicationClassObject();
        //   setUserInfo();
    }


    public void showShortToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    public void callActivity(Class<?> cls) {

        Intent callDestinationActivity = new Intent(getActivity(), cls);
        callDestinationActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(callDestinationActivity);

    }

    private void setApplicationClassObject() {
        applicationClass = (ExecutiveRideTrackerApplicationClass) getActivity().getApplicationContext();
    }



    public String createUUId() {
        UUID uu = UUID.randomUUID();
        String uuid = uu.toString();
        return uuid;
    }
}
