package com.wondercars.ridetrackerexecutive.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wondercars.ridetrackerexecutive.Manager.PreferenceManager;
import com.wondercars.ridetrackerexecutive.R;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetSlotsDTOs.BookingSlotsObj;
import com.wondercars.ridetrackerexecutive.Utils.AppConstants;

import java.text.ParseException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import umer.accl.utils.DateUtils;

/**
 * Created by acer on 18/11/17.
 */

public class ManageSlotsRecyclerAdapter extends RecyclerView.Adapter<ManageSlotsRecyclerAdapter.MyViewHolder> {


    ArrayList<BookingSlotsObj> bookingSlotsObjArrayList;
    Activity activity;
    OnItemClickListener onItemClickListener;
    String userId;


    public ManageSlotsRecyclerAdapter(Activity activity, ArrayList<BookingSlotsObj> bookingSlotsObjArrayList, OnItemClickListener onItemClickListener) {
        this.bookingSlotsObjArrayList = bookingSlotsObjArrayList;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
        userId = PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID);
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, BookingSlotsObj object);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.childview_manageslots_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            if (bookingSlotsObjArrayList.get(position).getCar().getCars() != null && bookingSlotsObjArrayList.get(position).getCar().getCars().size() > 0) {
                holder.tvCarModelName.setText(bookingSlotsObjArrayList.get(position).getCar().getCars().get(0).getCarModelName());
                holder.tvCarNumber.setText(bookingSlotsObjArrayList.get(position).getCar().getCars().get(0).getRegistrationNumber());
                holder.tvCarVariant.setText(bookingSlotsObjArrayList.get(position).getCar().getCars().get(0).getVariantName());
                holder.tvCarFuelType.setText(bookingSlotsObjArrayList.get(position).getCar().getCars().get(0).getFuelType());
                holder.tvCarMode.setText(bookingSlotsObjArrayList.get(position).getCar().getCars().get(0).getMode());
            }

            holder.tvBookingDate.setText(DateUtils.formatStringDateFromOneToAnother(bookingSlotsObjArrayList.get(position).getBookingDate(), DateUtils.MMM_DD_COMMA_YYYY_DATE_FORMAT, DateUtils.DD_MMM_YYYY_DASH_DATE_FORMAT));
            holder.tvBookingTime.setText(DateUtils.formatStringDateFromOneToAnother(bookingSlotsObjArrayList.get(position).getFromTime(), DateUtils.MMM_DD_YYYY_hh_mm_ss_a_DATE_FORMAT, DateUtils.HH_MM_A_TIME_FORMAT)
                    + " to " + DateUtils.formatStringDateFromOneToAnother(bookingSlotsObjArrayList.get(position).getToTime(), DateUtils.MMM_DD_YYYY_hh_mm_ss_a_DATE_FORMAT, DateUtils.HH_MM_A_TIME_FORMAT));

            if(bookingSlotsObjArrayList.get(position).getRideTrackerUser()!=null){
                holder.tv_executiveName.setText(bookingSlotsObjArrayList.get(position).getRideTrackerUser().getFullName()+"");
            } else {
                holder.tv_executiveName.setText(" ");
            }

            if (userId.equalsIgnoreCase(bookingSlotsObjArrayList.get(position).getUid())) {
                holder.cardView.setBackgroundColor(Color.parseColor(AppConstants.ColorStrings.white));

                holder.ivDelete.setVisibility(View.VISIBLE);
                holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onItemClickListener.onItemClick(v, position, bookingSlotsObjArrayList.get(position));
                    }
                });
            } else {
                holder.cardView.setBackgroundColor(Color.parseColor(AppConstants.ColorStrings.light_gray));
                holder.ivDelete.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return bookingSlotsObjArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_car_ModelName)
        TextView tvCarModelName;
        @BindView(R.id.tv_car_number)
        TextView tvCarNumber;
        @BindView(R.id.tv_car_fuel_type)
        TextView tvCarFuelType;
        @BindView(R.id.tv_car_variant)
        TextView tvCarVariant;
        @BindView(R.id.tv_car_mode)
        TextView tvCarMode;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.tv_bookingDate)
        TextView tvBookingDate;
        @BindView(R.id.tv_bookingTime)
        TextView tvBookingTime;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.tv_executiveName)
        TextView tv_executiveName;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
