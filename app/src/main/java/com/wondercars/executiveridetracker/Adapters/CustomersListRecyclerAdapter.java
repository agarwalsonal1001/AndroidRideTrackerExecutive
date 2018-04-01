package com.wondercars.executiveridetracker.Adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wondercars.executiveridetracker.R;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetCustomersListDTOs.Customer;
import com.wondercars.executiveridetracker.Retrofit.DTOs.ViewAllRidesDTOs.RidesDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 18/11/17.
 */

public class CustomersListRecyclerAdapter extends RecyclerView.Adapter<CustomersListRecyclerAdapter.MyViewHolder> {


    ArrayList<Customer> customerArrayList;
    Activity activity;
    OnItemClickListener onItemClickListener;


    public CustomersListRecyclerAdapter(Activity activity, ArrayList<Customer> customerArrayList, OnItemClickListener onItemClickListener) {
        this.customerArrayList = customerArrayList;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, Object object);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.childview_viewcustomers_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            if (customerArrayList.get(position) != null) {
                holder.tvEnquirynumber.setText(customerArrayList.get(position).getEnquiryNumber());
                holder.tvCustomername.setText(customerArrayList.get(position).getName());
                holder.tvCustomerNumber.setText(customerArrayList.get(position).getMobileNumber());
                holder.tvEmailId.setText(customerArrayList.get(position).getEmailID());
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onItemClickListener!=null)
                        onItemClickListener.onItemClick(v, position, customerArrayList.get(position));
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return customerArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_enquirynumber)
        TextView tvEnquirynumber;
        @BindView(R.id.tv_customername)
        TextView tvCustomername;
        @BindView(R.id.tv_customerNumber)
        TextView tvCustomerNumber;
        @BindView(R.id.tv_email_id)
        TextView tvEmailId;
        @BindView(R.id.card_view)
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ArrayList<Customer> getCustomerArrayList() {
        return customerArrayList;
    }
}
