package com.wondercars.executiveridetracker.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wondercars.executiveridetracker.Adapters.ManageSlotsRecyclerAdapter;
import com.wondercars.executiveridetracker.BaseClasses.BaseActivity;
import com.wondercars.executiveridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.R;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.BookingSlotsObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.GetSlotsRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.GetSlotsResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsortSlotsDTOs.UpsertSlotsRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsortSlotsDTOs.UpsertSlotsResponseObj;
import com.wondercars.executiveridetracker.Utils.APIConstants;
import com.wondercars.executiveridetracker.Utils.AppAlertDialog;
import com.wondercars.executiveridetracker.Utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;
import umer.accl.utils.DateUtils;

import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;

public class ManageSlotsActivity extends BaseActivity {

    private static final int GET_SLOTS_SERVICE_ID = 0;
    private static final int UPSERT_SLOTS_SERVICE_ID = 1;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ManageSlotsRecyclerAdapter manageSlotsRecyclerAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_slots);
        ButterKnife.bind(this);
        init();
        callGetSlotAPI();
    }

    private void init() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setActionBar(toolbar, "Manage Slots");
    }

    private GetSlotsRequestObj getSlotsRequestObj() {
        GetSlotsRequestObj getSlotsRequestObj = new GetSlotsRequestObj();
        getSlotsRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        getSlotsRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        return getSlotsRequestObj;
    }

    private void callGetSlotAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getSlotsRequestObj(), GetSlotsResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_SLOTS_API, GET_SLOTS_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    private UpsertSlotsRequestObj getUpsertSlotsRequestObj(BookingSlotsObj object) {

        UpsertSlotsRequestObj upsertSlotsRequestObj = new UpsertSlotsRequestObj();
        try {
            upsertSlotsRequestObj.setAdminUid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
            upsertSlotsRequestObj.setBookingDate(DateUtils.formatStringDateFromOneToAnother(object.getBookingDate(), DateUtils.MMM_DD_COMMA_YYYY_DATE_FORMAT, DateUtils.DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT));
            upsertSlotsRequestObj.setCarId(object.getCarId());
            upsertSlotsRequestObj.setFromTime(DateUtils.formatStringDateFromOneToAnother(object.getFromTime(), DateUtils.MMM_DD_YYYY_hh_mm_ss_a_DATE_FORMAT, DateUtils.DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT));
            upsertSlotsRequestObj.setToTime(DateUtils.formatStringDateFromOneToAnother(object.getToTime(), DateUtils.MMM_DD_YYYY_hh_mm_ss_a_DATE_FORMAT, DateUtils.DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT));
            upsertSlotsRequestObj.setActiveFlg("N");
            upsertSlotsRequestObj.setId(object.getId());
            upsertSlotsRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upsertSlotsRequestObj;
    }

    private void callUpsertSlotAPI(BookingSlotsObj object) {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getUpsertSlotsRequestObj(object), UpsertSlotsResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPSERT_SLOTS_API, UPSERT_SLOTS_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            switch (serviceId) {
                case GET_SLOTS_SERVICE_ID:
                    GetSlotsResponseObj getSlotsResponseObj = (GetSlotsResponseObj) object;
                    if (getSlotsResponseObj != null && getSlotsResponseObj.getStatus() != null) {
                        if (getSlotsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            if (getSlotsResponseObj.getBookingSlots() != null && getSlotsResponseObj.getBookingSlots().size() > 0) {

                                manageSlotsRecyclerAdapter = new ManageSlotsRecyclerAdapter(ManageSlotsActivity.this, getSlotsResponseObj.getBookingSlots(), new ManageSlotsRecyclerAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, final BookingSlotsObj object) {
                                        AppAlertDialog.showAlertDialog(ManageSlotsActivity.this, "Alert", "Do you want to delete this slot", false, "Yes", "No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                callUpsertSlotAPI(object);
                                            }
                                        }, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                    }
                                });
                                recyclerView.setAdapter(manageSlotsRecyclerAdapter);
                            }
                        }
                    }
                    break;

                case UPSERT_SLOTS_SERVICE_ID:
                    UpsertSlotsResponseObj getManageSlotsResponseObj = (UpsertSlotsResponseObj) object;
                    if (getManageSlotsResponseObj != null && getManageSlotsResponseObj.getStatus() != null) {
                        if (getManageSlotsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            showLongSnackBar("Slot deleted Successfully");
                            callGetSlotAPI();
                        } else {
                            showSnackBar(getManageSlotsResponseObj.getStatus().getErrorDescription());
                        }
                    }

                    break;
            }


        }

        @Override
        public void onError(int serviceId) {
            showSnackBar(AppConstants.ToastMessages.SOMETHING_WENT_WRONG);
        }
    };
}