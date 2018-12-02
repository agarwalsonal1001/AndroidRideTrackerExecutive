package com.wondercars.ridetrackerexecutive.Activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wondercars.ridetrackerexecutive.BaseClasses.BaseActivity;
import com.wondercars.ridetrackerexecutive.CustomClasses.AppProgressDialog;
import com.wondercars.ridetrackerexecutive.Manager.PreferenceManager;
import com.wondercars.ridetrackerexecutive.R;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.OtpDTOs.SendOtpRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.OtpDTOs.SendOtpResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertRideDTOs.UpsertRideRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.ValidateOtpDTOs.ValidateOtpRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.ValidateOtpDTOs.ValidateOtpResponseObj;
import com.wondercars.ridetrackerexecutive.Utils.APIConstants;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;
import umer.accl.utils.MyAppsLog;

import static android.text.TextUtils.isEmpty;
import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_ADMIN_UID;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.ERROR;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.ToastMessages.SOMETHING_WENT_WRONG;

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
        setActionBar(toolbar, "Enter OTP");
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
       /* if (getIntent().hasExtra("fromActivity")) {
            // sendOtpRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_RIDE_ID));
            sendOtpRequestObj.setIsTestDrive("N");

        } else {
            // sendOtpRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_TESTDRIVE_ID));
            sendOtpRequestObj.setIsTestDrive("Y");
        }*/
        sendOtpRequestObj.setIsTestDrive("Y");
        sendOtpRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_TESTDRIVE_ID));
        MyAppsLog.LogI("Send OTP", PreferenceManager.getObjectToString(sendOtpRequestObj));
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


    private ValidateOtpRequestObj getValidateOtpRequestObj() {
        ValidateOtpRequestObj validateOtpRequestObj = new ValidateOtpRequestObj();
        if (getIntent().hasExtra("fromActivity")) {
            //  validateOtpRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_RIDE_ID));
            validateOtpRequestObj.setIsTestDrive("N");
        } else {
            // validateOtpRequestObj.setAdmin_uid(PreferenceManager.readString(PREF_ADMIN_UID));
            validateOtpRequestObj.setIsTestDrive("Y");
        }
        validateOtpRequestObj.setAdmin_uid(PreferenceManager.readString(PREF_ADMIN_UID));
        validateOtpRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_TESTDRIVE_ID));
        validateOtpRequestObj.setOtp(etOtp.getText().toString());
        MyAppsLog.LogI("Validate OTP", PreferenceManager.getObjectToString(validateOtpRequestObj));
        return validateOtpRequestObj;

    }

    private void callValidateOTPAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getValidateOtpRequestObj(), ValidateOtpResponseObj.class,
                APIConstants.RetrofitMethodConstants.VALIDATE_OTP, VALIDATE_OTP_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    private void setTimerForButton() {
        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ivResendOtp.setClickable(true);
                        ivResendOtp.setEnabled(true);
                        ivResendOtp.setImageResource(R.drawable.ic_active_resend_otp);
                    }
                });
            }
        }, 50000);
    }

    private void setResendOTPButtonClickState(boolean isClickable) {
        ivResendOtp.setClickable(isClickable);
        if (isClickable == false) {
            setTimerForButton();
            ivResendOtp.setImageResource(R.drawable.ic_inactive_resend_otp);
        }
    }


    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            switch (serviceId) {

                case SEND_OTP_SERVICE_ID:
                    SendOtpResponseObj sendOtpResponseObj = (SendOtpResponseObj) object;
                    if (sendOtpResponseObj != null && sendOtpResponseObj.getStatus() != null) {
                        if (sendOtpResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            setResendOTPButtonClickState(false);
                            showLongSnackBar("OTP send to customer mobile number successfully");
                        } else if (sendOtpResponseObj.getStatus().getStatusCode() == FAILURE) {

                            showLongSnackBar(sendOtpResponseObj.getStatus().getErrorDescription());
                        }
                    }
                    break;

                case VALIDATE_OTP_SERVICE_ID:
                    ValidateOtpResponseObj validateOtpResponseObj = (ValidateOtpResponseObj) object;
                    if (validateOtpResponseObj != null && validateOtpResponseObj.getStatus() != null) {
                        if (validateOtpResponseObj.getStatus().getStatusCode() == SUCCESS && validateOtpResponseObj.isValid()) {
                            showLongSnackBar("OTP validated successfully");
                            if (getIntent().hasExtra("fromActivity") && getIntent().getStringExtra("fromActivity").equalsIgnoreCase("customerListActivity")) {
                                PreferenceManager.writeInteger(PreferenceManager.PREF_ON_GOING_RIDE_TYPE, -1);
                                callActivity(StartRideFromTestDriveOptionActivity.class);
                                finish();
                            } else {

                                callActivity(StartTestDriveActivity.class);
                            }
                        } else if (validateOtpResponseObj.getStatus().getStatus().equalsIgnoreCase(ERROR)) {

                            showLongSnackBar(validateOtpResponseObj.getStatus().getErrorDescription());
                        }
                    }
                    break;
            }
        }

        @Override
        public void onError(int serviceId) {
            showLongSnackBar(SOMETHING_WENT_WRONG);
        }
    };
}
