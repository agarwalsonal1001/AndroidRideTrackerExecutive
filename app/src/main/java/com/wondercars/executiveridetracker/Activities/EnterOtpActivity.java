package com.wondercars.executiveridetracker.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wondercars.executiveridetracker.BaseClasses.BaseActivity;
import com.wondercars.executiveridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.R;
import com.wondercars.executiveridetracker.Retrofit.DTOs.OtpDTOs.SendOtpRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.OtpDTOs.SendOtpResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertRideDTOs.UpsertRideRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.ValidateOtpDTOs.ValidateOtpRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.ValidateOtpDTOs.ValidateOtpResponseObj;
import com.wondercars.executiveridetracker.Utils.APIConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static android.text.TextUtils.isEmpty;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.ERROR;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;

public class EnterOtpActivity extends BaseActivity {

    private static final int SEND_OTP_SERVICE_ID = 0, VALIDATE_OTP_SERVICE_ID = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_otp)
    EditText etOtp;
    @BindView(R.id.iv_resendOtp)
    ImageView ivResendOtp;
    @BindView(R.id.button_validate)
    Button buttonValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);
        ButterKnife.bind(this);
        setActionBar(toolbar,"Enter OTP");
    }

    @OnClick({R.id.iv_resendOtp, R.id.button_validate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_resendOtp:
                callSendOTP_API();
                break;
            case R.id.button_validate:
                if (isEmpty(etOtp.getText().toString())) {
                    showLongSnackBar("Please enter otp");
                } else {
                    callValidateOTPAPI();

                }
                break;
        }
    }


    private SendOtpRequestObj getSendOtpRequestObj() {
        UpsertRideRequestObj upsertRideRequestObj = (UpsertRideRequestObj) PreferenceManager.getStringToObject(PreferenceManager.readString(PreferenceManager.PREF_RIDECUSTOMER_IFO), UpsertRideRequestObj.class);
        SendOtpRequestObj sendOtpRequestObj = new SendOtpRequestObj();
        sendOtpRequestObj.setCustomerMonileNumber("91" + upsertRideRequestObj.getMobileNumber());
        sendOtpRequestObj.setCustomer_email(upsertRideRequestObj.getEmailID());
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


    private ValidateOtpRequestObj getValidateOtpRequestObj() {
        ValidateOtpRequestObj validateOtpRequestObj = new ValidateOtpRequestObj();
        validateOtpRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_TESTDRIVE_ID));
        validateOtpRequestObj.setOtp(etOtp.getText().toString());
        return validateOtpRequestObj;

    }

    private void callValidateOTPAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getValidateOtpRequestObj(), ValidateOtpResponseObj.class,
                APIConstants.RetrofitMethodConstants.VALIDATE_OTP, VALIDATE_OTP_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            switch (serviceId) {

                case SEND_OTP_SERVICE_ID:

                    SendOtpResponseObj sendOtpResponseObj = (SendOtpResponseObj) object;
                    if (sendOtpResponseObj != null && sendOtpResponseObj.getStatus() != null) {
                        if (sendOtpResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            showSnackBar("OTP send to customer mobile number successfully");
                        } else if (sendOtpResponseObj.getStatus().getStatusCode() == FAILURE) {

                            showSnackBar(sendOtpResponseObj.getStatus().getErrorDescription());
                        }
                    }
                    break;

                case VALIDATE_OTP_SERVICE_ID:
                    ValidateOtpResponseObj validateOtpResponseObj = (ValidateOtpResponseObj) object;
                    if (validateOtpResponseObj != null && validateOtpResponseObj.getStatus() != null) {
                        if (validateOtpResponseObj.getStatus().getStatusCode() == SUCCESS && validateOtpResponseObj.isValid()) {
                            showSnackBar("OTP validated successfully");
                            callActivity(StartTestDriveActivity.class);
                        } else if (validateOtpResponseObj.getStatus().getStatus().equalsIgnoreCase(ERROR)) {

                            showSnackBar(validateOtpResponseObj.getStatus().getErrorDescription());
                        }
                    }
                    break;
            }
        }

        @Override
        public void onError(int serviceId) {

        }
    };
}
