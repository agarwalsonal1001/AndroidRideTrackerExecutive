package com.wondercars.executiveridetracker.Retrofit.DTOs.GetVeriantsDTOs;

import com.wondercars.executiveridetracker.Retrofit.DTOs.Status;

import java.util.ArrayList;

/**
 * Created by acer on 19/11/17.
 */

public class GetVeriantsResponseObj {

     Status status;
     ArrayList<VeriantsDetails> carVariants;

    public ArrayList<VeriantsDetails> getCarVariants() {
        return carVariants;
    }

    public void setCarVariants(ArrayList<VeriantsDetails> carVariants) {
        this.carVariants = carVariants;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
