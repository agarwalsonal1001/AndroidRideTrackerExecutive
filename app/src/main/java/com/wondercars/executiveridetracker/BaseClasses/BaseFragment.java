package com.wondercars.executiveridetracker.BaseClasses;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wondercars.executiveridetracker.Application.ExecutiveRideTrackerApplicationClass;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.Utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import umer.accl.retrofit.RetrofitHeaders;

import static com.wondercars.executiveridetracker.BaseClasses.BaseActivity.retrofitHeadersArrayList;

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


    public void showSnackBar(String message, View.OnClickListener listener) {
        if (!TextUtils.isEmpty(message)) {
            final Snackbar snackBar = Snackbar.make(this.getView().findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
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
            final Snackbar snackBar = Snackbar.make(this.getView().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
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
            final Snackbar snackBar = Snackbar.make(this.getView().findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
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
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String s) {
        if (!TextUtils.isEmpty(s))
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }

    public void callActivity(Class<?> cls) {
        Intent callDestinationActivity = new Intent(getContext(), cls);
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

    public List<RetrofitHeaders> getRetrofitHeaderses() {
        if (retrofitHeadersArrayList == null) {
            RetrofitHeaders retrofitHeaders = new RetrofitHeaders("uid", PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
            retrofitHeadersArrayList = new ArrayList<RetrofitHeaders>();
            retrofitHeadersArrayList.add(retrofitHeaders);
        }
        return retrofitHeadersArrayList;
    }
}
