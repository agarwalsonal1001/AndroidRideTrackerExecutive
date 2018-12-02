package com.wondercars.ridetrackerexecutive.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wondercars.ridetrackerexecutive.Adapters.GenericSpinnerAdapter;
import com.wondercars.ridetrackerexecutive.BaseClasses.BaseActivity;
import com.wondercars.ridetrackerexecutive.CustomClasses.AppProgressDialog;
import com.wondercars.ridetrackerexecutive.Manager.PreferenceManager;
import com.wondercars.ridetrackerexecutive.R;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertCustomerDetailsDTOs.UpsertCustomerDetailsRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertCustomerDetailsDTOs.UpsertCustomerDetailsResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertRideDTOs.UpsertRideRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertRideDTOs.UpsertRideResponseObj;
import com.wondercars.ridetrackerexecutive.Utils.APIConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static android.text.TextUtils.isEmpty;
import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_ADMIN_UID;
import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_INDIVISUAL_ID;
import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_RIDECUSTOMER_IFO;
import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_RIDE_ID;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.ResponseObjectType.TYPES_OF_RIDES;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.ResponseObjectType.TYPE_OF_VEHICLE;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.ToastMessages.SOMETHING_WENT_WRONG;

public class EnterCustomerDetailsActivityOld extends BaseActivity {

    private static final int UPSERT_RIDE_SERVICE_ID = 0, GET_SLOTS_SERVICE_ID = 1, SEND_OTP_SERVICE_ID = 4, UPSERT_TEST_DRIVE_SERVICE_ID = 5;
    private static final int UPSERT_CUSTOMER_API_SERVICE_ID = 6;
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
    @BindView(R.id.ll_type_of_vehicle)
    LinearLayout llTypeOfVehicle;
    String typeOfRideSelected = "", rideID = "", typeOfVehicleSelected = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    UpsertRideRequestObj upsertRideRequestObj;
    String userObj;
    Intent intent;
    /*@BindView(R.id.ll_typeOfRide)
    LinearLayout llTypeOfRide;*/
    @BindView(R.id.tv_somethingwrong)
    TextView tvSomethingwrong;
    @BindView(R.id.et_customerEmailId)
    EditText etCustomerEmailId;
    @BindView(R.id.spinner_type_of_vehicle)
    Spinner spinnerTypeOfVehicle;
    @BindView(R.id.et_vehiclenumber)
    EditText etVehiclenumber;
    private String customerID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_customer_details);
        ButterKnife.bind(this);

        init();
        setActionBar(toolbar, "Enter Customer Details");
    }


    private void init() {
        intent = getIntent();
        userObj = PreferenceManager.readString(PreferenceManager.PREF_RIDECUSTOMER_IFO);
        if (!isEmpty(userObj)) {
            upsertRideRequestObj = (UpsertRideRequestObj) PreferenceManager.getStringToObject(userObj, UpsertRideRequestObj.class);
        } else {
            upsertRideRequestObj = new UpsertRideRequestObj();
        }
        GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(this, -1, applicationClass.getTypesOfRide(), TYPES_OF_RIDES);
        spinnerTypeOfRide.setAdapter(genericSpinnerAdapter);
        spinnerTypeOfRide.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeOfRideSelected = ((String) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position));
//                if (typeOfRideSelected.equalsIgnoreCase("Test Drive")) {
//                    callGetSlotAPI();
//                } else {
                //  showOrHideCustomerDetailsLayout(true);
                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeOfRideSelected = ((String) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0));
            }
        });

        GenericSpinnerAdapter genericSpinnerAdapterForVehicleType = new GenericSpinnerAdapter(this, -1, applicationClass.getTypesOfVehicle(), TYPE_OF_VEHICLE);
        spinnerTypeOfVehicle.setAdapter(genericSpinnerAdapterForVehicleType);
        spinnerTypeOfVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeOfVehicleSelected = ((String) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position));
                if (typeOfVehicleSelected.equalsIgnoreCase("Company Vehicle")) {
                    showOrHideEnterVehicleNumberLayout(true);
                } else {
                    showOrHideEnterVehicleNumberLayout(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeOfVehicleSelected = ((String) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0));
            }
        });

       /* if (intent.hasExtra("CommingFromTestDriveOption")) {
            llTypeOfRide.setVisibility(View.GONE);
            callGetSlotAPI();
            showOrHideCustomerDetailsLayout(true);
            if (!isEmpty(userObj) && upsertRideRequestObj != null && !isEmpty(upsertRideRequestObj.getRideType()) && upsertRideRequestObj.getRideType().equalsIgnoreCase("Test Drive")) {
                // UpsertRideRequestObj upsertRideRequestObj = (UpsertRideRequestObj) PreferenceManager.getStringToObject(PreferenceManager.readString(PreferenceManager.PREF_RIDECUSTOMER_IFO), UpsertRideRequestObj.class);
                spinnerTypeOfRide.setSelection(applicationClass.getTypesOfRide().indexOf(upsertRideRequestObj.getRideType()));
                etEnquirynumber.setText(upsertRideRequestObj.getCustomerEnquiryNo());
                etCustomerMobileNumber.setText(upsertRideRequestObj.getCustomerMobile());
                etCustomername.setText(upsertRideRequestObj.getCustomerName());
                // rideID = upsertRideRequestObj.getId();
            }
        }*/
    }


    private UpsertCustomerDetailsRequestObj getUpsertCustomerDetailsRequestObj() {
        UpsertCustomerDetailsRequestObj upsertCustomerDetailsRequestObj = new UpsertCustomerDetailsRequestObj();
        upsertCustomerDetailsRequestObj.setUid(PreferenceManager.readString(PREF_INDIVISUAL_ID));
        upsertCustomerDetailsRequestObj.setAdminUid(PreferenceManager.readString(PREF_ADMIN_UID));
        upsertCustomerDetailsRequestObj.setEnquiryNumber(etEnquirynumber.getText().toString());
        upsertCustomerDetailsRequestObj.setName(etCustomername.getText().toString());
        upsertCustomerDetailsRequestObj.setMobileNumber(etCustomerMobileNumber.getText().toString());
        upsertCustomerDetailsRequestObj.setEmailID(etCustomerEmailId.getText().toString());
        return upsertCustomerDetailsRequestObj;
    }


    private void callUpsertCustomerDetailsApi() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getUpsertCustomerDetailsRequestObj(), UpsertCustomerDetailsResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPSERT_CUSTOMER_API, UPSERT_CUSTOMER_API_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


   /* private UpsertTestDriveRequestObj getUpsertTestDriveRequestObj() {
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
    }*/

    private UpsertRideRequestObj getUpsertRideRequestObj() {
        UpsertRideRequestObj upsertRideRequestObj = new UpsertRideRequestObj();
        upsertRideRequestObj.setEnquiryNumber(etEnquirynumber.getText().toString());
        upsertRideRequestObj.setName(etCustomername.getText().toString());
        upsertRideRequestObj.setMobileNumber(etCustomerMobileNumber.getText().toString());
        upsertRideRequestObj.setEmailID(etCustomerEmailId.getText().toString());
        upsertRideRequestObj.setAdminUid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        upsertRideRequestObj.setRideType(typeOfRideSelected);
        upsertRideRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        upsertRideRequestObj.setCustomer_id(customerID);
        upsertRideRequestObj.setVehicle_type(typeOfVehicleSelected);
        upsertRideRequestObj.setVehicle_number(etVehiclenumber.getText().append("").toString());//etVehiclenumber
        upsertRideRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_RIDE_ID));
        return upsertRideRequestObj;
    }

    private void callUpsertRideAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getUpsertRideRequestObj(), UpsertRideResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPSERT_RIDE_API, UPSERT_RIDE_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

 /*   private SendOtpRequestObj getSendOtpRequestObj() {
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
*/

    private void showOrHideEnterVehicleNumberLayout(boolean isWantToShow) {
        if (isWantToShow) {
            // tvSomethingwrong.setVisibility(View.GONE);
            llTypeOfVehicle.setVisibility(View.VISIBLE);
        } else {
            // tvSomethingwrong.setVisibility(View.VISIBLE);
            llTypeOfVehicle.setVisibility(View.GONE);
        }
    }

    /*private GetSlotsRequestObj getSlotsRequestObj() {
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
    }*/


    @OnClick({R.id.button_next})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_next:
/*                boolean hasExtra = intent.hasExtra("CommingFromTestDriveOption");
                //  boolean testDrive = typeOfRideSelected.equalsIgnoreCase("Test Drive");
                if (hasExtra) {
                    if (validateUpsertRideRequestObject()) {
                        if (isEmpty(userObj)) {
                            upsertRideRequestObj.setCustomerName(etCustomername.getText().toString());
                            upsertRideRequestObj.setCustomerEnquiryNo(etEnquirynumber.getText().toString());
                            upsertRideRequestObj.setCustomerMobile(etCustomerMobileNumber.getText().toString());
                            upsertRideRequestObj.setUid(PreferenceManager.readString(PREF_INDIVISUAL_ID));
                            upsertRideRequestObj.setAdminUid(PreferenceManager.readString(PREF_ADMIN_UID));
                            String getCusInfo = PreferenceManager.getObjectToString(upsertRideRequestObj);
                            PreferenceManager.writeString(PreferenceManager.PREF_RIDECUSTOMER_IFO, getCusInfo);
                        }
                        callUpsertTestDriveAPI();
                    }
                } else*/
                if (validateUpsertRideRequestObject()) {
                    callUpsertCustomerDetailsApi();
                    //callUpsertRideAPI();
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
                            showLongToast("Details saved successfully");
                            UpsertRideRequestObj upsertRideRequestObj = getUpsertRideRequestObj();
                            //upsertRideRequestObj.setId(upsertRideResponseObj.getId());
                            String upsertRideCustomerInfo = PreferenceManager.getObjectToString(upsertRideRequestObj);
                            PreferenceManager.writeString(PREF_RIDECUSTOMER_IFO, upsertRideCustomerInfo);
                            PreferenceManager.writeString(PREF_RIDE_ID, upsertRideResponseObj.getId());
                            callActivity(StartRideActivity.class);
                            finish();
                        } else if (upsertRideResponseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongSnackBar(upsertRideResponseObj.getStatus().getErrorDescription());
                        }
                    }
                    break;
                case UPSERT_CUSTOMER_API_SERVICE_ID:
                    UpsertCustomerDetailsResponseObj upsertCustomerDetailsResponseObj = (UpsertCustomerDetailsResponseObj) object;
                    if (upsertCustomerDetailsResponseObj != null && upsertCustomerDetailsResponseObj.getStatus() != null) {
                        if (upsertCustomerDetailsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            customerID = upsertCustomerDetailsResponseObj.getCustomerId();
                            callUpsertRideAPI();
                            /*Customer customer = new Customer();
                            customer.setName(etCustomername.getText().toString());
                            customer.setAdminUid(PreferenceManager.readString(PREF_ADMIN_UID));
                            customer.setEmailID(etCustomerEmailId.getText().toString());
                            customer.setEnquiryNumber(etEnquirynumber.getText().toString());
                            customer.setMobileNumber(etCustomerMobileNumber.getText().toString());
                            customer.setCustomer_id(upsertCustomerDetailsResponseObj.getCustomer_id());*/
                            showLongToast("Details saved successfully");
                        }
                    }
                    break;


                /*case GET_SLOTS_SERVICE_ID:

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
                                        showSnackBar("Car is available");
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
                                showSnackBar("Please book a slot first for the test drive", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        callActivity(NavigationActivity.class);
                                        finish();
                                    }
                                });

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
                            showLongSnackBar("Details saved successfully");
                            /*UpsertRideRequestObj upsertRideRequestObj = getUpsertRideRequestObj();
                            upsertRideRequestObj.setId(upsertRideResponseObj.getId());
                            String upsertRideCustomerInfo = PreferenceManager.getObjectToString(upsertRideRequestObj);
                            PreferenceManager.writeString(PREF_RIDECUSTOMER_IFO, upsertRideCustomerInfo); //
                            PreferenceManager.writeString(PREF_TESTDRIVE_ID, upsertTestDriveResponseObj.getId());
                            callSendOTP_API();
                        } else if (upsertTestDriveResponseObj.getStatus().getStatusCode() == FAILURE) {
                            showSnackBar(upsertTestDriveResponseObj.getStatus().getErrorDescription());
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
                    break;*/
            }
        }

        @Override
        public void onError(int serviceId) {
            showLongSnackBar(SOMETHING_WENT_WRONG);
        }
    };


    public boolean isAlphanumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c))
                return true;
        }

        return false;
    }

    public boolean isAlphaChar(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetter(c))
                return true;
        }

        return false;
    }


    private boolean validateEmailAddress(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            return true;
        }

        return false;
    }

    private boolean validateUpsertRideRequestObject() {

        if (isEmpty(etEnquirynumber.getText().toString())) {
            showLongSnackBar("Please enter Customer Enquiry Number");
            etEnquirynumber.setError("Please enter Customer Enquiry Number");
            return false;
        } else {
            if (!(isAlphanumeric(etEnquirynumber.getText().toString()) && isAlphaChar(etEnquirynumber.getText().toString()))) {
                showLongSnackBar("Customer Enquiry Number should be alphanumeric");
                etEnquirynumber.setError("Customer Enquiry Number should be alphanumeric");
                return false;
            }
        }

        if (isEmpty(etCustomername.getText().toString())) {
            etCustomername.setError("Please enter Customer Name");
            showLongSnackBar("Please enter Customer Name");
            return false;
        }

        if (isEmpty(etCustomerMobileNumber.getText().toString())) {
            etCustomerMobileNumber.setError("Please enter Customer Mobile Number");
            showLongSnackBar("Please enter Customer Mobile Number");
            return false;
        } else if (etCustomerMobileNumber.getText().toString().length() < 10) {
            etCustomerMobileNumber.setError("Please enter valid Customer Mobile Number");
            showLongSnackBar("Please enter valid Customer Mobile Number");
            return false;
        }

        if (isEmpty(etCustomerEmailId.getText().toString())) {
            etCustomerEmailId.setError("Please enter Customer Email ID");
            showLongSnackBar("Please enter Customer Customer Email ID");
            return false;
        } else if (!validateEmailAddress(etCustomerEmailId.getText().toString())) {
            etCustomerEmailId.setError("Please enter correct Customer Email ID");
            showLongSnackBar("Please enter correct Customer Customer Email ID");
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
