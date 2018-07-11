package com.wondercars.executiveridetracker.Retrofit.DTOs.GetExecutivesDTOs;

/**
 * Created by acer on 18/11/17.
 */

public class GetExecutivesRequestObj {

    String admin_uid;
    String uid;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public GetExecutivesRequestObj(String admin_uid) {
        this.admin_uid = admin_uid;
    }

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }
}
