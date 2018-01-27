package com.wondercars.executiveridetracker.Retrofit.DTOs.GetCarModelsDTOs;



import com.wondercars.executiveridetracker.Retrofit.DTOs.Status;

import java.util.ArrayList;

/**
 * Created by acer on 9/12/17.
 */

public class GetCarModelsResponseObject {

    Status status;
    ArrayList<CarModels> carModels;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<CarModels> getCarModels() {
        return carModels;
    }

    public void setCarModels(ArrayList<CarModels> carModels) {
        this.carModels = carModels;
    }
}
