package com.wondercars.executiveridetracker.Activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.wondercars.executiveridetracker.Adapters.ViewAllRidesRecyclerAdapter;
import com.wondercars.executiveridetracker.BaseClasses.BaseActivity;
import com.wondercars.executiveridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.R;
import com.wondercars.executiveridetracker.Retrofit.DTOs.ViewAllRidesDTOs.ViewAllRidesResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.ViewAllRidesDTOs.ViewRidesRequestObj;
import com.wondercars.executiveridetracker.Utils.APIConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;

public class ViewAllRidesActivity extends BaseActivity {


    private static final int VIEW_ALLRIDES_SERVICE_ID = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_rides);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        callViewAllRidesAPI();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setActionBar(toolbar, "All Rides");

    }

    private ViewRidesRequestObj getViewRidesRequestObj() {
        ViewRidesRequestObj viewRidesRequestObj = new ViewRidesRequestObj();
        viewRidesRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        viewRidesRequestObj.setAdminUid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        return viewRidesRequestObj;

    }

    private void callViewAllRidesAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getViewRidesRequestObj(), ViewAllRidesResponseObj.class,
                APIConstants.RetrofitMethodConstants.VIEW_ALL_RIDES, VIEW_ALLRIDES_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            ViewAllRidesResponseObj viewAllRidesResponseObj = (ViewAllRidesResponseObj) object;
            if (viewAllRidesResponseObj != null && viewAllRidesResponseObj.getStatus() != null) {

                if (viewAllRidesResponseObj.getStatus().getStatusCode() == SUCCESS) {
                    if (viewAllRidesResponseObj.getRides().size() > 0) {

                        ViewAllRidesRecyclerAdapter viewAllRidesRecyclerAdapter = new ViewAllRidesRecyclerAdapter(ViewAllRidesActivity.this, viewAllRidesResponseObj.getRides(), null);
                        recyclerView.setAdapter(viewAllRidesRecyclerAdapter);
                    }
                } else {
                    showSnackBar(viewAllRidesResponseObj.getStatus().getErrorDescription());
                }
            }

        }

        @Override
        public void onError(int serviceId) {

        }
    };

}



