package com.wondercars.ridetrackerexecutive.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wondercars.ridetrackerexecutive.Adapters.ViewAllRidesRecyclerAdapter;
import com.wondercars.ridetrackerexecutive.BaseClasses.BaseFragment;
import com.wondercars.ridetrackerexecutive.CustomClasses.AppProgressDialog;
import com.wondercars.ridetrackerexecutive.Manager.PreferenceManager;
import com.wondercars.ridetrackerexecutive.R;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.ViewAllRidesDTOs.ViewAllRidesResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.ViewAllRidesDTOs.ViewRidesRequestObj;
import com.wondercars.ridetrackerexecutive.Utils.APIConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.ToastMessages.SOMETHING_WENT_WRONG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RidesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RidesFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private final int VIEW_ALLRIDES_SERVICE_ID = 1;

    // TODO: Rename and change types of parameters


    public RidesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RidesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RidesFragment newInstance() {
        RidesFragment fragment = new RidesFragment();
       /* Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callViewAllRidesAPI();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rides, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private ViewRidesRequestObj getViewRidesRequestObj() {
        ViewRidesRequestObj viewRidesRequestObj = new ViewRidesRequestObj();
        viewRidesRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        viewRidesRequestObj.setAdminUid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        //  viewRidesRequestObj.setRide_completed_flg(false);
        return viewRidesRequestObj;

    }

    private void callViewAllRidesAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(getActivity(),
                APIConstants.baseurl, getViewRidesRequestObj(), ViewAllRidesResponseObj.class,
                APIConstants.RetrofitMethodConstants.VIEW_ALL_RIDES, VIEW_ALLRIDES_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(getActivity()))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
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

                        ViewAllRidesRecyclerAdapter viewAllRidesRecyclerAdapter = new ViewAllRidesRecyclerAdapter(getActivity(), viewAllRidesResponseObj.getRides(), null);
                        recyclerView.setAdapter(viewAllRidesRecyclerAdapter);
                    }
                } else {
                    showLongSnackBar(viewAllRidesResponseObj.getStatus().getErrorDescription());
                }
            }

        }

        @Override
        public void onError(int serviceId) {
            showLongSnackBar(SOMETHING_WENT_WRONG);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
