package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.LoginServiceDTOs;


import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.Status;

/**
 * Created by umer on 8/11/17.
 */

public class LoginResponseObj {

    Status status;
    String uid;
    String admin_uid;

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
