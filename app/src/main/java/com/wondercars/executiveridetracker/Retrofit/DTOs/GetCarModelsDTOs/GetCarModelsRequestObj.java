package com.wondercars.executiveridetracker.Retrofit.DTOs.GetCarModelsDTOs;

/**
 * Created by acer on 9/12/17.
 */

public class GetCarModelsRequestObj {


    String admin_uid;

    public GetCarModelsRequestObj() {
    }

    public GetCarModelsRequestObj(String admin_uid) {
        this.admin_uid = admin_uid;
    }

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }
}
