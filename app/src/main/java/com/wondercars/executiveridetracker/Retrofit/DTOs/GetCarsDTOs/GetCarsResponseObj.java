package com.wondercars.executiveridetracker.Retrofit.DTOs.GetCarsDTOs;



import com.wondercars.executiveridetracker.Retrofit.DTOs.Status;

import java.util.ArrayList;

/**
 * Created by acer on 19/11/17.
 */

public class GetCarsResponseObj {

    Status status;
    ArrayList<CarDetailObj> cars;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<CarDetailObj> getCars() {
        return cars;
    }

    public void setCars(ArrayList<CarDetailObj> cars) {
        this.cars = cars;
    }
}
