package com.wondercars.executiveridetracker.Retrofit.DTOs.ViewAllRidesDTOs;

import com.wondercars.executiveridetracker.Retrofit.DTOs.Status;

import java.util.ArrayList;

/**
 * Created by acer on 29/1/18.
 */

public class ViewAllRidesResponseObj {

    Status status;

    ArrayList<RidesDetails> rides;

    public ArrayList<RidesDetails> getRides() {
        return rides;
    }

    public void setRides(ArrayList<RidesDetails> rides) {
        this.rides = rides;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
