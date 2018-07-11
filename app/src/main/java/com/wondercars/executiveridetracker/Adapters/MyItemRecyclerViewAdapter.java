package com.wondercars.executiveridetracker.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wondercars.executiveridetracker.CallBacks.OnListFragmentInteractionListener;
import com.wondercars.executiveridetracker.R;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetExecutivesDTOs.ExecutivesDetailsObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.ViewAllTestDrives.RidesDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<RidesDetails> testDrives;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(ArrayList<RidesDetails> testDrives, OnListFragmentInteractionListener listener) {
        this.testDrives = testDrives;
        mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try {
            holder.mItem = testDrives.get(position).getRideTrackerUser();
            RidesDetails ridesDetails = testDrives.get(position);

            if (ridesDetails != null) {
                if (ridesDetails.getRideTrackerUser() != null) {
                    holder.tv_executive_name.setText(ridesDetails.getRideTrackerUser().getFullName());
                }

                holder.tvEnquirynumber.setText(ridesDetails.getCustomer_enquiry_no());
                holder.tvCustomername.setText(ridesDetails.getCustomer_name());
                holder.tvCustomerNumber.setText(ridesDetails.getCustomer_mobile());

                if (!TextUtils.isEmpty(ridesDetails.getCreateDate())) {
                    holder.tv_ride_date.setText(ridesDetails.getCreateDate());
                } else {
                    holder.tv_ride_date.setText("No Date found");
                }
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            mListener.onListFragmentInteraction(holder.mItem);
                        }
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return testDrives.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_executive_name)
        TextView tv_executive_name;
        @BindView(R.id.tv_enquirynumber)
        TextView tvEnquirynumber;
        @BindView(R.id.tv_customername)
        TextView tvCustomername;
        @BindView(R.id.tv_customerNumber)
        TextView tvCustomerNumber;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.tv_ride_date)
        TextView tv_ride_date;

        public ExecutivesDetailsObj mItem;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
