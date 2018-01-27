package com.wondercars.executiveridetracker.Adapters;

/**
 * Created by acer on 21/1/18.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.wondercars.executiveridetracker.R;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.BookingSlotsObj;

import java.util.ArrayList;

public class GridViewAdapter<T> extends BaseAdapter {
    private final LayoutInflater inflater;
    private Context mContext;
    private ArrayList<T> listOfData;
    public static int numberOfSelectedSlots =0;
    // Constructor
    public GridViewAdapter(Context c, ArrayList<T> list) {
        mContext = c;
        listOfData = list;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return listOfData.size();
    }

    public Object getItem(int position) {
        return listOfData.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        TextView button;
        if (convertView == null) {
            row = inflater.inflate(R.layout.childview_timeslot_adapter, parent, false);
           /* button.setBackground(mContext.getResources().getDrawable(R.drawable.time_selector));
            button.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setPadding(8, 8, 8, 8);*/
        } else {
            row = convertView;
        }
        try {
            TextView textView = row.findViewById(R.id.textView);
            textView.setText(((BookingSlotsObj) listOfData.get(position)).getFromTime() + "to" + ((BookingSlotsObj) listOfData.get(position)).getToTime());
            if(!((BookingSlotsObj) listOfData.get(position)).isAvailable()){
                textView.setClickable(false);
                textView.setBackground(null);
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.blue_border_rectangle_with_circular_corners));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public ArrayList<T> getListOfData(){
        return listOfData;
    }
}
