package com.wondercars.executiveridetracker.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.wondercars.executiveridetracker.BaseClasses.BaseActivity;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_book_slot)
    LinearLayout llBookSlot;
    @BindView(R.id.ll_manage_slots)
    LinearLayout llManageSlots;
    @BindView(R.id.ll_start_ride)
    LinearLayout llStartRide;
    @BindView(R.id.ll_view_rides)
    LinearLayout llViewRides;
    @BindView(R.id.ll_start_testdrive)
    LinearLayout llStartTestdrive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        setActionBar(toolbar, "Dashboard", false);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.logout:
                PreferenceManager.clearAllPreference();
                callActivity(LoginActivity.class);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //
    @OnClick({R.id.ll_book_slot, R.id.ll_manage_slots, R.id.ll_start_ride, R.id.ll_view_rides, R.id.ll_start_testdrive})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_book_slot:
                callActivity(BookSlotActivity.class);
                break;
            case R.id.ll_manage_slots:
                callActivity(ManageSlotsActivity.class);
                break;

            case R.id.ll_start_testdrive:
                // if (!isEmpty(PreferenceManager.readString(PreferenceManager.PREF_RIDECUSTOMER_IFO))) {
                Intent callDestinationActivity = new Intent(this, CustomerListActivity.class);
                callDestinationActivity.putExtra("CommingFromTestDriveOption", "CommingFromTestDriveOption");
                startActivity(callDestinationActivity);
              /*  } else {
                    showLongToast("Please enter customer details from Start Ride Option");
                }*/
                break;
            case R.id.ll_start_ride:
                // if (isEmpty(PreferenceManager.readString(PreferenceManager.PREF_RIDECUSTOMER_IFO))) {
                callActivity(EnterCustomerDetailsActivityOld.class);
               /* } else {
                    callActivity(StartRideActivity.class);
                }*/

                break;
            case R.id.ll_view_rides:
                callActivity(ViewAllRidesActivity.class);
                break;
        }
    }
}
