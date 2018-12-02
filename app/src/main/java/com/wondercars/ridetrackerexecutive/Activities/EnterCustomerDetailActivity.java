package com.wondercars.ridetrackerexecutive.Activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wondercars.ridetrackerexecutive.BaseClasses.BaseActivity;
import com.wondercars.ridetrackerexecutive.CustomClasses.AppProgressDialog;
import com.wondercars.ridetrackerexecutive.Manager.PreferenceManager;
import com.wondercars.ridetrackerexecutive.R;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCustomersListDTOs.Customer;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertCustomerDetailsDTOs.UpsertCustomerDetailsRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertCustomerDetailsDTOs.UpsertCustomerDetailsResponseObj;
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
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.ToastMessages.SOMETHING_WENT_WRONG;

public class EnterCustomerDetailActivity extends BaseActivity {

    private static final int UPSERT_CUSTOMER_API_SERVICE_ID = 0;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_emailID)
    EditText etEmailID;
    @BindView(R.id.et_enquirynumber)
    EditText etEnquirynumber;
    @BindView(R.id.et_customername)
    EditText etCustomername;
    @BindView(R.id.et_customerMobileNumber)
    EditText etCustomerMobileNumber;
    @BindView(R.id.button_next)
    Button buttonNext;
    @BindView(R.id.tv_somethingwrong)
    TextView tvSomethingwrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_customer_detail);
        ButterKnife.bind(this);
        setActionBar(toolbar, "Enter Customer Details");
    }


    private UpsertCustomerDetailsRequestObj getUpsertCustomerDetailsRequestObj() {
        UpsertCustomerDetailsRequestObj upsertCustomerDetailsRequestObj = new UpsertCustomerDetailsRequestObj();
        upsertCustomerDetailsRequestObj.setUid(PreferenceManager.readString(PREF_INDIVISUAL_ID));
        upsertCustomerDetailsRequestObj.setAdminUid(PreferenceManager.readString(PREF_ADMIN_UID));
        upsertCustomerDetailsRequestObj.setEnquiryNumber(etEnquirynumber.getText().toString());
        upsertCustomerDetailsRequestObj.setName(etCustomername.getText().toString());
        upsertCustomerDetailsRequestObj.setMobileNumber(etCustomerMobileNumber.getText().toString());
        upsertCustomerDetailsRequestObj.setEmailID(etEmailID.getText().append("").toString());
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


    @OnClick({R.id.button_next, R.id.tv_somethingwrong})
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.button_next:
                    if (validateUpsertRideRequestObject()) {
                        callUpsertCustomerDetailsApi();
                    }
                    break;
                case R.id.tv_somethingwrong:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateUpsertRideRequestObject() {

        try {
            if (isEmpty(etEnquirynumber.getText().toString())) {
                showLongSnackBar("Please enter Customer Enquiry Number");
                etEnquirynumber.setError("Please enter Customer Enquiry Number");
                return false;
            } else {
                if (!(isAlphanumeric(etEnquirynumber.getText().toString()) && isAlphaChar(etEnquirynumber.getText().toString())))  {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isEmpty(etEmailID.getText().toString())) {
            showLongSnackBar("Please enter Customer Email ID");
            etEmailID.setError("Please enter Customer Email ID");
            return false;
        } else if (!validateEmailAddress(etEmailID.getText().toString())) {
            etEmailID.setError("Please enter correct Customer Email ID");
            showLongSnackBar("Please enter correct Customer Customer Email ID");
            return false;
        }
        return true;
    }

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
    public boolean isAlphaNumeric(String s) {
        String pattern = "^[a-zA-Z0-9]*$";
        return s.matches(pattern);
    }

    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object obj, int serviceId) {
            try {
                switch (serviceId) {
                    case UPSERT_CUSTOMER_API_SERVICE_ID:
                        UpsertCustomerDetailsResponseObj upsertCustomerDetailsResponseObj = (UpsertCustomerDetailsResponseObj) obj;
                        if (upsertCustomerDetailsResponseObj != null && upsertCustomerDetailsResponseObj.getStatus() != null) {
                            if (upsertCustomerDetailsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                                Customer customer = new Customer();
                                customer.setName(etCustomername.getText().toString());
                                customer.setAdminUid(PreferenceManager.readString(PREF_ADMIN_UID));
                                customer.setEmailID(etEmailID.getText().append("").toString());
                                customer.setEnquiryNumber(etEnquirynumber.getText().toString());
                                customer.setMobileNumber(etCustomerMobileNumber.getText().toString());
                                customer.setCustomer_id(upsertCustomerDetailsResponseObj.getCustomer_id());
                                // showLongToast("Details saved successfully");
                                CustomerListActivity.customerArrayList.add(customer);
                                //CustomerListActivity.booking_id = upsertCustomerDetailsResponseObj.getBooking_id();
                                onBackPressed();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
