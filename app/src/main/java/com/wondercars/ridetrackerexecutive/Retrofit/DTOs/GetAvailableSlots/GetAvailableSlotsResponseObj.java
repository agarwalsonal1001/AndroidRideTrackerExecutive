package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetAvailableSlots;

import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.Status;

import java.util.ArrayList;

/**
 * Created by acer on 28/1/18.
 */

public class GetAvailableSlotsResponseObj {

    Status status;
    ArrayList<Integer> fromTimesAvailable;
    ArrayList<Integer> toTimesAvailable;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Integer> getFromTimesAvailable() {
        return fromTimesAvailable;
    }

    public void setFromTimesAvailable(ArrayList<Integer> fromTimesAvailable) {
        this.fromTimesAvailable = fromTimesAvailable;
    }

    public ArrayList<Integer> getToTimesAvailable() {
        return toTimesAvailable;
    }

    public void setToTimesAvailable(ArrayList<Integer> toTimesAvailable) {
        this.toTimesAvailable = toTimesAvailable;
    }
}
