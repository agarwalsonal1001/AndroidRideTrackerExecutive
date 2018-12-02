package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCarModelsDTOs;

/**
 * Created by acer on 9/12/17.
 */

public class CarModels {

    String carModelId;
    String carModelName;
    String admin_uid;


    public String getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(String carModelId) {
        this.carModelId = carModelId;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }
}
