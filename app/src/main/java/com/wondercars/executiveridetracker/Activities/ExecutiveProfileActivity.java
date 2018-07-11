package com.wondercars.executiveridetracker.Activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wondercars.executiveridetracker.BaseClasses.BaseActivity;
import com.wondercars.executiveridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.R;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetExecutivesDTOs.GetExecutivesRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetExecutivesDTOs.GetExecutivesResponseObj;
import com.wondercars.executiveridetracker.Utils.APIConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.executiveridetracker.Utils.AppConstants.ToastMessages.SOMETHING_WENT_WRONG;

public class ExecutiveProfileActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private final int GET_EXECUTIVES_SERVICE_ID = 1;
    @BindView(R.id.et_name)
    TextView etName;
    @BindView(R.id.et_mobilenumber)
    TextView etMobilenumber;
    @BindView(R.id.et_email)
    TextView etEmail;
    @BindView(R.id.tv_designation)
    TextView tvDesignation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executive_profile);
        ButterKnife.bind(this);
        setActionBar(toolbar, "Profile", true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        callGetExecutivesAPI();
    }

    private GetExecutivesRequestObj getExecutivesRequestObj() {
        GetExecutivesRequestObj getExecutivesRequestObj = new GetExecutivesRequestObj(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        getExecutivesRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        return getExecutivesRequestObj;
    }

    private void callGetExecutivesAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getExecutivesRequestObj(), GetExecutivesResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_EXECUTIVESBYID, GET_EXECUTIVES_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int i) {
            switch (i) {

                case GET_EXECUTIVES_SERVICE_ID:
                    try {
                        GetExecutivesResponseObj responseObj = (GetExecutivesResponseObj) object;
                        if (responseObj != null && responseObj.getStatus() != null) {
                            if (responseObj.getStatus().getStatusCode() == SUCCESS) {
                                if (responseObj.getExecutivesDetailsObj() != null) {
                                    etName.setText(responseObj.getExecutivesDetailsObj().getFullName() + "");
                                    etMobilenumber.setText(responseObj.getExecutivesDetailsObj().getPhoneNumber() + "");
                                    etEmail.setText(responseObj.getExecutivesDetailsObj().getEmail() + "");
                                    tvDesignation.setText(responseObj.getExecutivesDetailsObj().getDesignation() + "");

                                }
                            } else if (responseObj.getStatus().getStatusCode() == FAILURE) {
                                showLongToast(responseObj.getStatus().getErrorDescription());
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
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
