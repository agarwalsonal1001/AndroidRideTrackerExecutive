package com.wondercars.ridetrackerexecutive.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;


import com.wondercars.ridetrackerexecutive.BaseClasses.BaseActivity;
import com.wondercars.ridetrackerexecutive.CustomClasses.AppProgressDialog;
import com.wondercars.ridetrackerexecutive.Manager.PreferenceManager;
import com.wondercars.ridetrackerexecutive.R;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.LoginServiceDTOs.LoginRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.LoginServiceDTOs.LoginResponseObj;
import com.wondercars.ridetrackerexecutive.Utils.APIConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_ADMIN_UID;
import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_INDIVISUAL_ID;
import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_LOGIN_CURRENT_TAG;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.LOGIN_SUCCESSFULLY;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.PLEASE_ENTER_EMAIL;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.PLEASE_ENTER_PASSWORD;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.ToastMessages.SOMETHING_WENT_WRONG;


public class LoginActivity extends BaseActivity {

    private static final int LOGIN_API = 1;
    @BindView(R.id.edt_enter_username)
    EditText edtEnterUsername;
    @BindView(R.id.edt_enter_password)
    EditText edtEnterPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //  callUserLoginApi();

    }

    private LoginRequestObj getLoginApiRequestObject() {
        LoginRequestObj loginRequestObj = new LoginRequestObj();
        loginRequestObj.setEmail(edtEnterUsername.getText().toString());
        loginRequestObj.setPassword(edtEnterPassword.getText().toString());
        return loginRequestObj;
    }

    private void callUserLoginApi() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(LoginActivity.this,
                APIConstants.baseurl, getLoginApiRequestObject(), LoginResponseObj.class,
                APIConstants.RetrofitMethodConstants.LOGIN_API, LOGIN_API, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
        // mUserRegistrationService.userLogin(retrofitParamsDTO, getLoginApiRequestObject());
    }

     RetrofitInterface retrofitInterface = new RetrofitInterface() {

                @Override
                public void onSuccess(Object object, int serviceId) {

                    switch (serviceId) {

                        case LOGIN_API:
                            LoginResponseObj loginResponseObj = (LoginResponseObj) object;
                            if (loginResponseObj != null && loginResponseObj.getStatus() != null) {
                                if (loginResponseObj.getStatus().getStatusCode() == SUCCESS) {
                                    PreferenceManager.writeBoolean(PREF_LOGIN_CURRENT_TAG, true);
                                    PreferenceManager.writeString(PREF_INDIVISUAL_ID, loginResponseObj.getUid());
                                    PreferenceManager.writeString(PREF_ADMIN_UID,loginResponseObj.getAdmin_uid());
                                    showLongSnackBar(LOGIN_SUCCESSFULLY);
                                    callActivity(NavigationActivity.class);
                                    finish();
                                } else if (loginResponseObj.getStatus().getStatusCode() == FAILURE) {
                                    showLongSnackBar(loginResponseObj.getStatus().getErrorDescription());
                                }
                            }

                            break;

                        default:

                            break;
                    }


                }

                @Override
                public void onError(int serviceId) {
                    showLongSnackBar(SOMETHING_WENT_WRONG);
                }
            };

    private boolean validateFields() {
        if (TextUtils.isEmpty(edtEnterUsername.getText().toString())) {
            showLongSnackBar(PLEASE_ENTER_EMAIL);
            return false;
        }
        if (TextUtils.isEmpty(edtEnterPassword.getText().toString())) {
            showLongSnackBar(PLEASE_ENTER_PASSWORD);
            return false;
        }

        return true;
    }

    @OnClick(R.id.tv_login)
    public void onClick() {
        if (validateFields()) {
            callUserLoginApi();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
