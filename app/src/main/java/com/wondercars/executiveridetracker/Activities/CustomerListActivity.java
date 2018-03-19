package com.wondercars.executiveridetracker.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.wondercars.executiveridetracker.Adapters.CustomersListRecyclerAdapter;
import com.wondercars.executiveridetracker.BaseClasses.BaseActivity;
import com.wondercars.executiveridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.R;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetCustomersListDTOs.Customer;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetCustomersListDTOs.GetCustomerListRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetCustomersListDTOs.GetCustomerListResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertCustomerDetailsDTOs.UpsertCustomerDetailsRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertCustomerDetailsDTOs.UpsertCustomerDetailsResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsortSlotsDTOs.UpsertSlotsRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsortSlotsDTOs.UpsertSlotsResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.ViewAllRidesDTOs.RidesDetails;
import com.wondercars.executiveridetracker.Utils.APIConstants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static com.wondercars.executiveridetracker.Manager.PreferenceManager.PREF_ADMIN_UID;
import static com.wondercars.executiveridetracker.Manager.PreferenceManager.PREF_INDIVISUAL_ID;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;

public class CustomerListActivity extends BaseActivity implements CustomersListRecyclerAdapter.OnItemClickListener {

    private static final int GET_CUSTOMER_LIST_API_SERVICE_ID = 0;
    private static final int UPSERT_SLOTS_API_SERVICE_ID = 1;
    protected static String booking_id = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.button_book)
    Button buttonBook;
    @BindView(R.id.floatingAddCustomer)
    FloatingActionButton floatingAddCustomer;

    CustomersListRecyclerAdapter customersListRecyclerAdapter;
    UpsertSlotsRequestObj upsertSlotsRequestObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        if (getIntent().hasExtra("slotInfo")) {
            upsertSlotsRequestObj = (UpsertSlotsRequestObj) getIntent().getSerializableExtra("slotInfo");
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setActionBar(toolbar, "Book Slot For Customers");
        callGetCustomerListApi();
    }

    private GetCustomerListRequestObj getCustomerListRequestObj() {
        GetCustomerListRequestObj getCustomerListRequestObj = new GetCustomerListRequestObj();
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
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    private void callUpsertSlotsREquestObj() {
        ArrayList<String> customersIDList = validateCustomersId();
        if (customersIDList.size() > 0) {
            upsertSlotsRequestObj.setCustomerIDs(customersIDList);
            callUpsertSlotsServiceAPI(upsertSlotsRequestObj);
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
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    private ArrayList<String> validateCustomersId() {
        ArrayList<String> customersIDList = new ArrayList<String>();
        if (((CustomersListRecyclerAdapter) recyclerView.getAdapter()).getCustomerArrayList() != null && ((CustomersListRecyclerAdapter) recyclerView.getAdapter()).getCustomerArrayList().size() > 0) {
            for (Customer customer :
                    ((CustomersListRecyclerAdapter) recyclerView.getAdapter()).getCustomerArrayList()) {
                customersIDList.add(customer.getCustomer_id());
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
                                }
                            }
                        }
                        break;

                    case UPSERT_SLOTS_API_SERVICE_ID:

                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(int i) {

        }
    };

    @Override
    public void onItemClick(View view, int position, Object object) {

    }
}
