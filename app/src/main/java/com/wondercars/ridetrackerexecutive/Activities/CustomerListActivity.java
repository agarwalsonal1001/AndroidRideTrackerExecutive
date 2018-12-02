package com.wondercars.ridetrackerexecutive.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wondercars.ridetrackerexecutive.Adapters.CustomersListRecyclerAdapter;
import com.wondercars.ridetrackerexecutive.BaseClasses.BaseActivity;
import com.wondercars.ridetrackerexecutive.CustomClasses.AppProgressDialog;
import com.wondercars.ridetrackerexecutive.Manager.PreferenceManager;
import com.wondercars.ridetrackerexecutive.R;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCustomersListDTOs.Customer;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCustomersListDTOs.GetCustomerListRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCustomersListDTOs.GetCustomerListResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetSlotsDTOs.BookingSlotsObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetSlotsDTOs.GetSlotsRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetSlotsDTOs.GetSlotsResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.OtpDTOs.SendOtpRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.OtpDTOs.SendOtpResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertRideDTOs.UpsertRideRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertRideDTOs.UpsertRideResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertTestDriveDTOs.UpsertTestDriveRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertTestDriveDTOs.UpsertTestDriveResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsortSlotsDTOs.UpsertSlotsRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsortSlotsDTOs.UpsertSlotsResponseObj;
import com.wondercars.ridetrackerexecutive.Utils.APIConstants;
import com.wondercars.ridetrackerexecutive.Utils.AppAlertDialog;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;
import umer.accl.utils.DateUtils;

import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_ADMIN_UID;
import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_INDIVISUAL_ID;
import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_RIDECUSTOMER_IFO;
import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_RIDE_ID;
import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_TESTDRIVE_ID;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.ToastMessages.SOMETHING_WENT_WRONG;
import static umer.accl.utils.DateUtils.DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT;
import static umer.accl.utils.DateUtils.MMM_DD_YYYY_hh_mm_ss_a_DATE_FORMAT;

public class CustomerListActivity extends BaseActivity implements CustomersListRecyclerAdapter.OnItemClickListener {

    private static final int GET_CUSTOMER_LIST_API_SERVICE_ID = 0;
    private static final int UPSERT_SLOTS_API_SERVICE_ID = 1, GET_SLOTS_SERVICE_ID = 2;
    private static final int SEND_OTP_SERVICE_ID = 3;
    private static final int UPSERT_TEST_DRIVE_SERVICE_ID = 4;
    private static final int UPSERT_RIDE_SERVICE_ID = 5;
    protected static String booking_id = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.button_book)
    Button buttonBook;
    @BindView(R.id.floatingAddCustomer)
    FloatingActionButton floatingAddCustomer;
    String Booking_id = "", customerMobileNumber = "";
    private String CarId;
    protected static ArrayList<Customer> customerArrayList;
    CustomersListRecyclerAdapter customersListRecyclerAdapter;
    UpsertSlotsRequestObj upsertSlotsRequestObj;
    private UpsertRideRequestObj upsertRideRequestObj;
    private String customerEmailID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        ButterKnife.bind(this);
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (customerArrayList != null && customerArrayList.size() > 0) {
            CustomersListRecyclerAdapter customersListRecyclerAdapter = new CustomersListRecyclerAdapter(CustomerListActivity.this, customerArrayList, null);
            recyclerView.setAdapter(customersListRecyclerAdapter);
        }
    }

    private void init() {
        upsertRideRequestObj = new UpsertRideRequestObj();
        if (getIntent().hasExtra("slotInfo")) {
            upsertSlotsRequestObj = (UpsertSlotsRequestObj) getIntent().getSerializableExtra("slotInfo");
        }
        customerArrayList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setActionBar(toolbar, "Customers");

        if (getIntent().hasExtra("CommingFromTestDriveOption")) {
            callGetSlotAPI();
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            recyclerView.setLayoutParams(param);
            floatingAddCustomer.setVisibility(View.GONE);
            buttonBook.setVisibility(View.GONE);
        }
        //   callGetCustomerListApi();
    }

    private UpsertTestDriveRequestObj getUpsertTestDriveRequestObj() {
        UpsertTestDriveRequestObj upsertTestDriveRequestObj = new UpsertTestDriveRequestObj();
        upsertTestDriveRequestObj.setCarId(upsertRideRequestObj.getCarId());
        upsertTestDriveRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        upsertTestDriveRequestObj.setRide_type("Test Drive");
        upsertTestDriveRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        upsertTestDriveRequestObj.setCustomer_enquiry_no(upsertRideRequestObj.getEnquiryNumber());
        upsertTestDriveRequestObj.setCustomer_name(upsertRideRequestObj.getName());
        upsertTestDriveRequestObj.setCustomer_mobile(upsertRideRequestObj.getMobileNumber());
        upsertTestDriveRequestObj.setCustomer_id(upsertRideRequestObj.getCustomer_id());
        upsertTestDriveRequestObj.setRide_type("Test Drive");
        return upsertTestDriveRequestObj;

    }

    private void callUpsertTestDriveAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getUpsertTestDriveRequestObj(), UpsertTestDriveResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPSERT_TEST_DRIVE, UPSERT_TEST_DRIVE_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
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
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    private SendOtpRequestObj getSendOtpRequestObj() {
        SendOtpRequestObj sendOtpRequestObj = new SendOtpRequestObj();
        sendOtpRequestObj.setCustomerMonileNumber("91" + customerMobileNumber);
        sendOtpRequestObj.setCustomer_email(customerEmailID);
        sendOtpRequestObj.setIsTestDrive("N");
        sendOtpRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_TESTDRIVE_ID));

        return sendOtpRequestObj;
    }

    private void callSendOTP_API() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getSendOtpRequestObj(), SendOtpResponseObj.class,
                APIConstants.RetrofitMethodConstants.SEND_OTP_TO_CUSTOMER, SEND_OTP_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    private GetCustomerListRequestObj getCustomerListRequestObj() {
        GetCustomerListRequestObj getCustomerListRequestObj = new GetCustomerListRequestObj();
        getCustomerListRequestObj.setBooking_id(Booking_id);
        getCustomerListRequestObj.setUid(PreferenceManager.readString(PREF_INDIVISUAL_ID));
        getCustomerListRequestObj.setAdmin_uid(PreferenceManager.readString(PREF_ADMIN_UID));
        return getCustomerListRequestObj;
    }


    private void callGetCustomerListApi() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getCustomerListRequestObj(), GetCustomerListResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_CUSTOMER_LIST_API, GET_CUSTOMER_LIST_API_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    private void callUpsertSlotsREquestObj() {
        ArrayList<String> customersIDList = validateCustomersId();
        if (customersIDList.size() > 0) {
            upsertSlotsRequestObj.setCustomerIDs(customersIDList);
            callUpsertSlotsServiceAPI(upsertSlotsRequestObj);
           // MyAppsLog.LogI("Request Object", PreferenceManager.getObjectToString(upsertSlotsRequestObj));
        } else {
            showLongSnackBar("Please add Customer");
        }
    }


    private void callUpsertSlotsServiceAPI(UpsertSlotsRequestObj upsertSlotsRequestObj) {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, upsertSlotsRequestObj, UpsertSlotsResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPSERT_SLOTS_API, UPSERT_SLOTS_API_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    private ArrayList<String> validateCustomersId() {
        ArrayList<String> customersIDList = new ArrayList<String>();
        if (recyclerView.getAdapter() != null) {
            if (((CustomersListRecyclerAdapter) recyclerView.getAdapter()).getCustomerArrayList() != null && ((CustomersListRecyclerAdapter) recyclerView.getAdapter()).getCustomerArrayList().size() > 0) {
                for (Customer customer :
                        ((CustomersListRecyclerAdapter) recyclerView.getAdapter()).getCustomerArrayList()) {
                    customersIDList.add(customer.getCustomer_id());
                }
            }
        }

        return customersIDList;
    }

    @OnClick({R.id.button_book, R.id.floatingAddCustomer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_book:
                callUpsertSlotsREquestObj();
                break;
            case R.id.floatingAddCustomer:
                callActivity(EnterCustomerDetailActivity.class);
                break;
        }
    }


    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object obj, int serviceId) {
            try {
                switch (serviceId) {
                    case GET_CUSTOMER_LIST_API_SERVICE_ID:
                        GetCustomerListResponseObj getCustomerListResponseObj = (GetCustomerListResponseObj) obj;
                        if (getCustomerListResponseObj != null && getCustomerListResponseObj.getStatus() != null) {
                            if (getCustomerListResponseObj.getStatus().getStatusCode() == SUCCESS) {
                                if (getCustomerListResponseObj.getCustomers() != null && getCustomerListResponseObj.getCustomers().size() > 0) {
                                    CustomersListRecyclerAdapter customersListRecyclerAdapter = new CustomersListRecyclerAdapter(CustomerListActivity.this, (ArrayList) getCustomerListResponseObj.getCustomers(), CustomerListActivity.this);
                                    recyclerView.setAdapter(customersListRecyclerAdapter);
                                }else {
                                    showLongSnackBar("No Test Drive's Found. Please add Test Drive's first.");
                                }
                            }else {
                                showSnackBar(getCustomerListResponseObj.getStatus().getErrorDescription() + "", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                       onBackPressed();
                                    }
                                });
                            }
                        }
                        break;

                    case UPSERT_SLOTS_API_SERVICE_ID:
                        UpsertSlotsResponseObj upsertSlotsResponseObj = (UpsertSlotsResponseObj) obj;
                        if (upsertSlotsResponseObj != null && upsertSlotsResponseObj.getStatus() != null) {
                            if (upsertSlotsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                                customerArrayList.clear();
                                showLongSnackBar("Slot Booked Successfully");
                                callActivity(NavigationActivity.class);
                                finish();
                            }else {
                                showLongSnackBar(upsertSlotsResponseObj.getStatus().getErrorDescription());
                            }
                        }
                        break;

                    case GET_SLOTS_SERVICE_ID:

                        GetSlotsResponseObj getSlotsResponseObj = (GetSlotsResponseObj) obj;
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
                                            //showLongSnackBar("Car is available");

                                            Booking_id = bookingSlotsObj.getId();
                                            PreferenceManager.writeString(PreferenceManager.PREF_BOOKING_ID,Booking_id);
                                            CarId = bookingSlotsObj.getCarId();
                                            callGetCustomerListApi();
//                                            upsertRideRequestObj.setCarId(bookingSlotsObj.getCarId());
//                                            String getCusInfo = PreferenceManager.getObjectToString(upsertRideRequestObj);
                                            //PreferenceManager.writeString(PreferenceManager.PREF_RIDECUSTOMER_IFO, getCusInfo);
                                            slotIsAllocated = true;
                                            break;
                                        }
                                    }

                                }
                                if (!slotIsAllocated) {
                                    //showOrHideCustomerDetailsLayout(false);
                                    showSnackBar("Please book a slot first for the test drive", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            callActivity(NavigationActivity.class);
                                            finish();
                                        }
                                    });

                                } else {
                                    //showOrHideCustomerDetailsLayout(true);
                                }
                            }
                        }
                        break;

                    case SEND_OTP_SERVICE_ID:

                        SendOtpResponseObj sendOtpResponseObj = (SendOtpResponseObj) obj;
                        if (sendOtpResponseObj != null) {
                            if (sendOtpResponseObj.getStatus().getStatusCode() == SUCCESS) {
                                Intent callOTPActivityBeforeRideStart = new Intent(CustomerListActivity.this,EnterOtpActivity.class);
                                callOTPActivityBeforeRideStart.putExtra("fromActivity","customerListActivity");
                                startActivity(callOTPActivityBeforeRideStart);
                               // callActivity(EnterOtpActivity.class);
                            }
                        }
                        break;

                    case UPSERT_TEST_DRIVE_SERVICE_ID:
                        UpsertTestDriveResponseObj upsertTestDriveResponseObj = (UpsertTestDriveResponseObj) obj;
                        if (upsertTestDriveResponseObj != null && upsertTestDriveResponseObj.getStatus() != null) {
                            if (upsertTestDriveResponseObj.getStatus().getStatusCode() == SUCCESS) {
                                //showLongSnackBar("Details saved successfully");
                            UpsertRideRequestObj upsertRideRequestObj = getUpsertRideRequestObj();
                            upsertRideRequestObj.setId(upsertTestDriveResponseObj.getId());
                            String upsertRideCustomerInfo = PreferenceManager.getObjectToString(upsertRideRequestObj);
                            PreferenceManager.writeString(PREF_RIDECUSTOMER_IFO, upsertRideCustomerInfo);
                                PreferenceManager.writeString(PREF_TESTDRIVE_ID, upsertTestDriveResponseObj.getId());
                                PreferenceManager.writeInteger(PreferenceManager.PREF_ON_GOING_RIDE_TYPE, -1);
                                callActivity(StartRideFromTestDriveOptionActivity.class);
                                //callSendOTP_API();
                            } else if (upsertTestDriveResponseObj.getStatus().getStatusCode() == FAILURE) {
                                showLongSnackBar(upsertTestDriveResponseObj.getStatus().getErrorDescription());
                            }
                        }

                        break;


                    case UPSERT_RIDE_SERVICE_ID:
                        UpsertRideResponseObj upsertRideResponseObj = (UpsertRideResponseObj) obj;
                        if (upsertRideResponseObj != null && upsertRideResponseObj.getStatus() != null) {
                            if (upsertRideResponseObj.getStatus().getStatusCode() == SUCCESS) {
                               // showLongToast("Details saved successfully");
                                UpsertRideRequestObj upsertRideRequestObj = getUpsertRideRequestObj();
                                //upsertRideRequestObj.setId(upsertRideResponseObj.getId());
                                String upsertRideCustomerInfo = PreferenceManager.getObjectToString(upsertRideRequestObj);
                                PreferenceManager.writeString(PREF_RIDECUSTOMER_IFO, upsertRideCustomerInfo);
                                PreferenceManager.writeString(PREF_RIDE_ID, upsertRideResponseObj.getId());

                                callSendOTP_API();
                                /*callActivity(StartRideFromTestDriveOptionActivity.class);
                                finish();*/
                            } else if (upsertRideResponseObj.getStatus().getStatusCode() == FAILURE) {
                                showLongSnackBar(upsertRideResponseObj.getStatus().getErrorDescription());
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(int i) {
            showLongSnackBar(SOMETHING_WENT_WRONG);
        }
    };


    private UpsertRideRequestObj getUpsertRideRequestObj() {
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

    @Override
    public void onItemClick(View view, int position, Object object) {
        Customer customer = (Customer) object;
        upsertRideRequestObj.setCarId(CarId);
        upsertRideRequestObj.setName(customer.getName());
        upsertRideRequestObj.setEnquiryNumber(customer.getEnquiryNumber());
        upsertRideRequestObj.setMobileNumber(customer.getMobileNumber());
        upsertRideRequestObj.setUid(PreferenceManager.readString(PREF_INDIVISUAL_ID));
        upsertRideRequestObj.setAdminUid(PreferenceManager.readString(PREF_ADMIN_UID));
        upsertRideRequestObj.setCustomer_id(customer.getCustomer_id());
        upsertRideRequestObj.setRideType("Test Drive"); //dont send this
        //upsertRideRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_RIDE_ID));
//        String getCusInfo = PreferenceManager.getObjectToString(upsertRideRequestObj);
//        PreferenceManager.writeString(PreferenceManager.PREF_RIDECUSTOMER_IFO, getCusInfo);
        customerMobileNumber = customer.getMobileNumber();
        customerEmailID = customer.getEmailID();

        callUpsertTestDriveAPI();
       // callUpsertRideAPI();

        //callUpsertTestDriveAPI();
        //callSendOTP_API();
    }


    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra("CommingFromTestDriveOption")) {
            super.onBackPressed();
            finish();
        }else if (recyclerView.getAdapter() != null) {
            if (((CustomersListRecyclerAdapter) recyclerView.getAdapter()).getCustomerArrayList() != null && ((CustomersListRecyclerAdapter) recyclerView.getAdapter()).getCustomerArrayList().size() > 0) {
                AppAlertDialog.showAlertDialog(CustomerListActivity.this, "Alert", "Your details are not saved yet, please click 'Book' button else you will lose customer information.", false, "Proceed", "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callActivity(BookSlotActivity.class);
                        finish();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }else {
                super.onBackPressed();
                finish();
            }
        }else {
            super.onBackPressed();
            finish();
        }
    }
}
