package com.wondercars.executiveridetracker.Activities;

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
import com.wondercars.executiveridetracker.Retrofit.DTOs.OtpDTOs.SendOtpRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.OtpDTOs.SendOtpResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertRideDTOs.UpsertRideRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertRideDTOs.UpsertRideResponseObj;
import com.wondercars.executiveridetracker.Utils.APIConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static android.text.TextUtils.isEmpty;
import static com.wondercars.executiveridetracker.Manager.PreferenceManager.PREF_RIDECUSTOMER_IFO;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.executiveridetracker.Utils.AppConstants.ResponseObjectType.TYPES_OF_RIDES;

public class EnterCustomerDetailsActivity extends BaseActivity {

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
    private final int UPSERT_RIDE_SERVICE_ID = 0;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_customer_details);
        ButterKnife.bind(this);
        init();
        setActionBar(toolbar,"Enter Customer Details");
    }

    private UpsertRideRequestObj getUpsertRideRequestObj() {
        UpsertRideRequestObj upsertRideRequestObj = new UpsertRideRequestObj();
        upsertRideRequestObj.setAdminUid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        upsertRideRequestObj.setRideType(typeOfRideSelected);
        upsertRideRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        upsertRideRequestObj.setCustomerEnquiryNo(etEnquirynumber.getText().toString());
        upsertRideRequestObj.setCustomerName(etCustomername.getText().toString());
        upsertRideRequestObj.setCustomerMobile(etCustomerMobileNumber.getText().toString());
        upsertRideRequestObj.setId(rideID);
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
        sendOtpRequestObj.setCustomerMonileNumber(etCustomerMobileNumber.getText().toString());
        sendOtpRequestObj.setId(rideID);
        return sendOtpRequestObj;
    }

    private void callSendOTP_API() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getSendOtpRequestObj(), SendOtpResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPSERT_RIDE_API, UPSERT_RIDE_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    private void init() {

        GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(this, -1, applicationClass.getTypesOfRide(), TYPES_OF_RIDES);
        spinnerTypeOfRide.setAdapter(genericSpinnerAdapter);
        spinnerTypeOfRide.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeOfRideSelected = ((String) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position));
                if (typeOfRideSelected.equalsIgnoreCase("Test Drive")) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeOfRideSelected = ((String) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0));
            }
        });

        if (getIntent().hasExtra("CommingFromTestDriveOption")) {
            if (!isEmpty(PreferenceManager.readString(PreferenceManager.PREF_RIDECUSTOMER_IFO))) {
                UpsertRideRequestObj upsertRideRequestObj = (UpsertRideRequestObj) PreferenceManager.getStringToObject(PreferenceManager.readString(PreferenceManager.PREF_RIDECUSTOMER_IFO), UpsertRideRequestObj.class);
                spinnerTypeOfRide.setSelection(applicationClass.getTypesOfRide().indexOf(upsertRideRequestObj.getRideType()));
                etEnquirynumber.setText(upsertRideRequestObj.getCustomerEnquiryNo());
                etCustomerMobileNumber.setText(upsertRideRequestObj.getCustomerMobile());
                etCustomername.setText(upsertRideRequestObj.getCustomerName());
                rideID = upsertRideRequestObj.getId();
            }
        }
    }

    @OnClick({R.id.button_next})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_next:
                if (getIntent().hasExtra("CommingFromTestDriveOption")) {
                    if (validateUpsertRideRequestObject()) {
                        callActivity(StartTestDriveActivity.class);
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
                            upsertRideRequestObj.setId(upsertRideResponseObj.getId());
                            String upsertRideCustomerInfo = PreferenceManager.getObjectToString(upsertRideRequestObj);
                            PreferenceManager.writeString(PREF_RIDECUSTOMER_IFO, upsertRideCustomerInfo);
                            callActivity(StartRideActivity.class);
                            finish();
                        } else if (upsertRideResponseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongToast(upsertRideResponseObj.getStatus().getErrorDescription());
                        }
                    }
                    break;
            }
        }

        @Override
        public void onError(int serviceId) {

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
