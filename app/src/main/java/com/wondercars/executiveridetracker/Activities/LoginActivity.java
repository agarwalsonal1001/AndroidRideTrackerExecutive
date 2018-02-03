package com.wondercars.executiveridetracker.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;


import com.wondercars.executiveridetracker.BaseClasses.BaseActivity;
import com.wondercars.executiveridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.R;
import com.wondercars.executiveridetracker.Retrofit.DTOs.LoginServiceDTOs.LoginRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.LoginServiceDTOs.LoginResponseObj;
import com.wondercars.executiveridetracker.Utils.APIConstants;
import com.wondercars.executiveridetracker.Utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static com.wondercars.executiveridetracker.Manager.PreferenceManager.PREF_ADMIN_UID;
import static com.wondercars.executiveridetracker.Manager.PreferenceManager.PREF_INDIVISUAL_ID;
import static com.wondercars.executiveridetracker.Manager.PreferenceManager.PREF_LOGIN_CURRENT_TAG;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.executiveridetracker.Utils.AppConstants.LOGIN_SUCCESSFULLY;
import static com.wondercars.executiveridetracker.Utils.AppConstants.PLEASE_ENTER_EMAIL;
import static com.wondercars.executiveridetracker.Utils.AppConstants.PLEASE_ENTER_PASSWORD;


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
                                    showLongToast(LOGIN_SUCCESSFULLY);
                                    callActivity(NavigationActivity.class);
                                    finish();
                                } else if (loginResponseObj.getStatus().getStatusCode() == FAILURE) {
                                    showLongToast(loginResponseObj.getStatus().getErrorDescription());
                                }
                            }

                            break;

                        default:

                            break;
                    }


                }

                @Override
                public void onError(int serviceId) {
                    showLongToast(AppConstants.ToastMessages.SOMETHING_WENT_WRONG);
                }
            };

    private boolean validateFields() {
        if (TextUtils.isEmpty(edtEnterUsername.getText().toString())) {
            showShortToast(PLEASE_ENTER_EMAIL);
            return false;
        }
        if (TextUtils.isEmpty(edtEnterPassword.getText().toString())) {
            showShortToast(PLEASE_ENTER_PASSWORD);
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
