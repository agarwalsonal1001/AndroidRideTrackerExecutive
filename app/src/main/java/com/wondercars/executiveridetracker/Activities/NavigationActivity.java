package com.wondercars.executiveridetracker.Activities;

import android.content.DialogInterface;
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
import com.wondercars.executiveridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.executiveridetracker.Database._BookingSlotsDB;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.R;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.GetSlotsRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.GetSlotsResponseObj;
import com.wondercars.executiveridetracker.Services.SetNotificationService;
import com.wondercars.executiveridetracker.Utils.APIConstants;
import com.wondercars.executiveridetracker.Utils.AppAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;

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
    private final int GET_SLOTS_SERVICE_ID = 1;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        setActionBar(toolbar, "Dashboard", false);
        //callGetSlotAPI();
        //getApplicationContext().startForegroundService(new Intent(getApplicationContext(),SetNotificationService.class));
        startService(new Intent(getApplicationContext(), SetNotificationService.class));
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
                AppAlertDialog.showAlertDialog(this, "Alert", "Are you sure you want to Logout?", true, "Yes", "No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PreferenceManager.clearAllPreference();
                                callActivity(LoginActivity.class);
                                dialog.dismiss();
                                finish();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                return true;

            case R.id.profile:
                callActivity(ExecutiveProfileActivity.class);
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


    private GetSlotsRequestObj getSlotsRequestObj() {
        GetSlotsRequestObj getSlotsRequestObj = new GetSlotsRequestObj();
        getSlotsRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        getSlotsRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        getSlotsRequestObj.setRide_completed_flg("N");
        return getSlotsRequestObj;
    }

    private void callGetSlotAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getSlotsRequestObj(), GetSlotsResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_SLOTS_API, GET_SLOTS_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object o, int i) {
            switch (i) {
                case GET_SLOTS_SERVICE_ID:
                    GetSlotsResponseObj getSlotsResponseObj = (GetSlotsResponseObj) o;
                    if (getSlotsResponseObj != null && getSlotsResponseObj.getStatus() != null) {
                        if (getSlotsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            if (getSlotsResponseObj.getBookingSlots() != null && getSlotsResponseObj.getBookingSlots().size() > 0) {

                                boolean inserted = _BookingSlotsDB.getInstance(getApplicationContext()).insertbookingSlotsList(getSlotsResponseObj.getBookingSlots());
                                // if(inserted)
                                //  showLongSnackBar("Data inserted");

                            }
                        }
                    }
                    break;
            }
        }

        @Override
        public void onError(int i) {

        }
    };
}
