package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetVeriantsDTOs;

/**
 * Created by acer on 19/11/17.
 */

public class GetVeriantsRequestObj {

    String admin_uid;

    public GetVeriantsRequestObj(String admin_uid) {
        this.admin_uid = admin_uid;
    }

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }
}
