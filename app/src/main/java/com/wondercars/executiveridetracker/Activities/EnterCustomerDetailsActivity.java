package com.wondercars.executiveridetracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.wondercars.executiveridetracker.Adapters.GenericSpinnerAdapter;
import com.wondercars.executiveridetracker.BaseClasses.BaseActivity;
import com.wondercars.executiveridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.R;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.BookingSlotsObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.GetSlotsRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.GetSlotsResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.OtpDTOs.SendOtpRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.OtpDTOs.SendOtpResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertRideDTOs.UpsertRideRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertRideDTOs.UpsertRideResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertTestDriveDTOs.UpsertTestDriveRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertTestDriveDTOs.UpsertTestDriveResponseObj;
import com.wondercars.executiveridetracker.Utils.APIConstants;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;
import umer.accl.utils.DateUtils;

import static android.text.TextUtils.isEmpty;
import static com.wondercars.executiveridetracker.Manager.PreferenceManager.PREF_RIDECUSTOMER_IFO;
import static com.wondercars.executiveridetracker.Manager.PreferenceManager.PREF_RIDE_ID;
import static com.wondercars.executiveridetracker.Manager.PreferenceManager.PREF_TESTDRIVE_ID;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.executiveridetracker.Utils.AppConstants.ResponseObjectType.TYPES_OF_RIDES;
import static umer.accl.utils.DateUtils.DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT;
import static umer.accl.utils.DateUtils.MMM_DD_YYYY_hh_mm_ss_a_DATE_FORMAT;

public class EnterCustomerDetailsActivity extends BaseActivity {

    private static final int UPSERT_RIDE_SERVICE_ID = 0, GET_SLOTS_SERVICE_ID = 1, SEND_OTP_SERVICE_ID = 4, UPSERT_TEST_DRIVE_SERVICE_ID = 5;
    @BindView(R.id.spinner_type_of_ride)
    Spinner spinnerTypeOfRide;
    @BindView(R.id.et_enquirynumber)
    EditText etEnquirynumber;
    @BindView(R.id.et_customername)
    EditText etCustomername;
    @BindView(R.id.et_customerMobileNumber)
    EditText etCustomerMobileNumber;
    @BindView(R.id.button_next)
    Button buttonNext;
    @BindView(R.id.ll_customerDetails)
    LinearLayout llCustomerDetails;
    String typeOfRideSelected = "", rideID = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    UpsertRideRequestObj upsertRideRequestObj;
    String userObj;
    Intent intent;
    @BindView(R.id.ll_typeOfRide)
    LinearLayout llTypeOfRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_customer_details);
        ButterKnife.bind(this);

        init();
        setActionBar(toolbar, "Enter Customer Details");
    }


    private UpsertTestDriveRequestObj getUpsertTestDriveRequestObj() {
        UpsertTestDriveRequestObj upsertTestDriveRequestObj = new UpsertTestDriveRequestObj();
        upsertTestDriveRequestObj.setCarId(upsertRideRequestObj.getCarId());
        upsertTestDriveRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        upsertTestDriveRequestObj.setRide_type(typeOfRideSelected);
        upsertTestDriveRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        upsertTestDriveRequestObj.setCustomer_enquiry_no(etEnquirynumber.getText().toString());
        upsertTestDriveRequestObj.setCustomer_name(etCustomername.getText().toString());
        upsertTestDriveRequestObj.setCustomer_mobile(etCustomerMobileNumber.getText().toString());
        return upsertTestDriveRequestObj;

    }

    private void callUpsertTestDriveAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getUpsertTestDriveRequestObj(), UpsertTestDriveResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPSERT_TEST_DRIVE, UPSERT_TEST_DRIVE_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    private UpsertRideRequestObj getUpsertRideRequestObj() {
        UpsertRideRequestObj upsertRideRequestObj = new UpsertRideRequestObj();
        upsertRideRequestObj.setAdminUid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        upsertRideRequestObj.setRideType(typeOfRideSelected);
        upsertRideRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        upsertRideRequestObj.setCustomerEnquiryNo(etEnquirynumber.getText().toString());
        upsertRideRequestObj.setCustomerName(etCustomername.getText().toString());
        upsertRideRequestObj.setCustomerMobile(etCustomerMobileNumber.getText().toString());
        upsertRideRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_RIDE_ID));
        return upsertRideRequestObj;
    }

    private void callUpsertRideAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getUpsertRideRequestObj(), UpsertRideResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPSERT_RIDE_API, UPSERT_RIDE_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    private SendOtpRequestObj getSendOtpRequestObj() {
        SendOtpRequestObj sendOtpRequestObj = new SendOtpRequestObj();
        sendOtpRequestObj.setCustomerMonileNumber("91" + etCustomerMobileNumber.getText().toString());
        sendOtpRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_TESTDRIVE_ID));
        return sendOtpRequestObj;
    }

    private void callSendOTP_API() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getSendOtpRequestObj(), SendOtpResponseObj.class,
                APIConstants.RetrofitMethodConstants.SEND_OTP_TO_CUSTOMER, SEND_OTP_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    private void init() {
        intent = getIntent();
        userObj = PreferenceManager.readString(PreferenceManager.PREF_RIDECUSTOMER_IFO);
        if (!isEmpty(userObj)) {
            upsertRideRequestObj = (UpsertRideRequestObj) PreferenceManager.getStringToObject(userObj, UpsertRideRequestObj.class);
        }
        GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(this, -1, applicationClass.getTypesOfRide(), TYPES_OF_RIDES);
        spinnerTypeOfRide.setAdapter(genericSpinnerAdapter);
        spinnerTypeOfRide.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeOfRideSelected = ((String) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position));
                if (typeOfRideSelected.equalsIgnoreCase("Test Drive")) {
                    callGetSlotAPI();
                } else {
                    showOrHideCustomerDetailsLayout(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeOfRideSelected = ((String) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0));
            }
        });

        if (intent.hasExtra("CommingFromTestDriveOption")) {
            llTypeOfRide.setVisibility(View.GONE);
            callGetSlotAPI();
            showOrHideCustomerDetailsLayout(true);
            if (!isEmpty(userObj) && upsertRideRequestObj != null && upsertRideRequestObj.getRideType().equalsIgnoreCase("Test Drive")) {
                // UpsertRideRequestObj upsertRideRequestObj = (UpsertRideRequestObj) PreferenceManager.getStringToObject(PreferenceManager.readString(PreferenceManager.PREF_RIDECUSTOMER_IFO), UpsertRideRequestObj.class);
                spinnerTypeOfRide.setSelection(applicationClass.getTypesOfRide().indexOf(upsertRideRequestObj.getRideType()));
                etEnquirynumber.setText(upsertRideRequestObj.getCustomerEnquiryNo());
                etCustomerMobileNumber.setText(upsertRideRequestObj.getCustomerMobile());
                etCustomername.setText(upsertRideRequestObj.getCustomerName());
                // rideID = upsertRideRequestObj.getId();
            }
        }
    }


    private void showOrHideCustomerDetailsLayout(boolean isWantToShow) {
        if (isWantToShow) {
            llCustomerDetails.setVisibility(View.VISIBLE);
        } else {
            llCustomerDetails.setVisibility(View.GONE);
        }
    }

    private GetSlotsRequestObj getSlotsRequestObj() {
        GetSlotsRequestObj getSlotsRequestObj = new GetSlotsRequestObj();
        getSlotsRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        getSlotsRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        getSlotsRequestObj.setBookingDate(DateUtils.convertLongDateToString(System.currentTimeMillis(), DateUtils.DD_MMM_YYYY_DASH_DATE_FORMAT));
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


    @OnClick({R.id.button_next})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_next:
                boolean hasExtra = intent.hasExtra("CommingFromTestDriveOption");
                //  boolean testDrive = typeOfRideSelected.equalsIgnoreCase("Test Drive");
                if (hasExtra) {
                    if (validateUpsertRideRequestObject()) {
                        callUpsertTestDriveAPI();
                    }
                } else if (validateUpsertRideRequestObject()) {
                    callUpsertRideAPI();
                }

                break;
        }

    }

    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            switch (serviceId) {

                case UPSERT_RIDE_SERVICE_ID:
                    UpsertRideResponseObj upsertRideResponseObj = (UpsertRideResponseObj) object;
                    if (upsertRideResponseObj != null && upsertRideResponseObj.getStatus() != null) {
                        if (upsertRideResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            showShortToast("Details saved successfully");
                            UpsertRideRequestObj upsertRideRequestObj = getUpsertRideRequestObj();
                            //upsertRideRequestObj.setId(upsertRideResponseObj.getId());
                            String upsertRideCustomerInfo = PreferenceManager.getObjectToString(upsertRideRequestObj);
                            PreferenceManager.writeString(PREF_RIDECUSTOMER_IFO, upsertRideCustomerInfo);
                            PreferenceManager.writeString(PREF_RIDE_ID, upsertRideResponseObj.getId());
                            callActivity(StartRideActivity.class);
                            finish();
                        } else if (upsertRideResponseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongToast(upsertRideResponseObj.getStatus().getErrorDescription());
                        }
                    }
                    break;

                case GET_SLOTS_SERVICE_ID:

                    GetSlotsResponseObj getSlotsResponseObj = (GetSlotsResponseObj) object;
                    if (getSlotsResponseObj != null && getSlotsResponseObj.getStatus() != null) {
                        if (getSlotsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            boolean slotIsAllocated = false;
                            if (getSlotsResponseObj.getBookingSlots() != null && getSlotsResponseObj.getBookingSlots().size() > 0) {

                                String currentDateTime = DateUtils.getCurrentDate(DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT);
                                Date currentDatenTime = DateUtils.getStringToDate(currentDateTime, MMM_DD_YYYY_hh_mm_ss_a_DATE_FORMAT);
                                for (BookingSlotsObj bookingSlotsObj : getSlotsResponseObj.getBookingSlots()) {
                                    Date slotFromDateTime = DateUtils.getStringToDate(bookingSlotsObj.getFromTime(), MMM_DD_YYYY_hh_mm_ss_a_DATE_FORMAT);
                                    Date slotToDateTime = DateUtils.getStringToDate(bookingSlotsObj.getToTime(), MMM_DD_YYYY_hh_mm_ss_a_DATE_FORMAT);
                                    if (slotFromDateTime.getHours() == currentDatenTime.getHours() || (slotToDateTime.getHours() > currentDatenTime.getHours()) && (slotToDateTime.getHours() - currentDatenTime.getHours()) <= 2) {
                                        showShortToast("Car is available");
                                        upsertRideRequestObj.setCarId(bookingSlotsObj.getCarId());
                                        String getCusInfo = PreferenceManager.getObjectToString(upsertRideRequestObj);
                                        PreferenceManager.writeString(PreferenceManager.PREF_RIDECUSTOMER_IFO, getCusInfo);
                                        slotIsAllocated = true;
                                        break;
                                    }
                                }

                            }
                            if (!slotIsAllocated) {
                                showOrHideCustomerDetailsLayout(false);
                                showLongToast("Please book a slot first for the test drive car");
                                callActivity(NavigationActivity.class);
                                finish();
                            } else {
                                showOrHideCustomerDetailsLayout(true);
                            }
                        }
                    }
                    break;

                case UPSERT_TEST_DRIVE_SERVICE_ID:
                    UpsertTestDriveResponseObj upsertTestDriveResponseObj = (UpsertTestDriveResponseObj) object;
                    if (upsertTestDriveResponseObj != null && upsertTestDriveResponseObj.getStatus() != null) {
                        if (upsertTestDriveResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            showShortToast("Details saved successfully");
                            /*UpsertRideRequestObj upsertRideRequestObj = getUpsertRideRequestObj();
                            upsertRideRequestObj.setId(upsertRideResponseObj.getId());
                            String upsertRideCustomerInfo = PreferenceManager.getObjectToString(upsertRideRequestObj);
                            PreferenceManager.writeString(PREF_RIDECUSTOMER_IFO, upsertRideCustomerInfo);*/
                            PreferenceManager.writeString(PREF_TESTDRIVE_ID, upsertTestDriveResponseObj.getId());
                            callSendOTP_API();
                        } else if (upsertTestDriveResponseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongToast(upsertTestDriveResponseObj.getStatus().getErrorDescription());
                        }
                    }

                    break;

                case SEND_OTP_SERVICE_ID:

                    SendOtpResponseObj sendOtpResponseObj = (SendOtpResponseObj) object;
                    if (sendOtpResponseObj != null) {
                        if (sendOtpResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            callActivity(EnterOtpActivity.class);
                        }
                    }
                    break;
            }
        }

        @Override
        public void onError(int serviceId) {
            showShortToast("Something went wrong...");
        }
    };

    private boolean validateUpsertRideRequestObject() {

        if (isEmpty(etCustomerMobileNumber.getText().toString())) {
            showShortToast("Please enter Customer Mobile Number");
            return false;
        }
        if (isEmpty(etCustomername.getText().toString())) {
            showShortToast("Please enter Customer Name");
            return false;
        }

        if (isEmpty(etEnquirynumber.getText().toString())) {
            showShortToast("Please enter Customer Enquiry Number");
            return false;
        }
        return true;
    }
    /*"uid" : "f4a4790f-4d0e-4dd0-b347-31ab47d178e7",
        "admin_uid": "6f33355e-e27c-4389-bc55-d1e0506ce035",
        "ride_type": "Document Pick-up", 
        "customer_enquiry_no": "1234",
        "customer_mobile": "9877777777",
        "customer_name": "Mr. XYZ",*/
}
