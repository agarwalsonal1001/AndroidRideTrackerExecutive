package com.wondercars.executiveridetracker.Retrofit.DTOs.ValidateOtpDTOs;

import com.wondercars.executiveridetracker.Retrofit.DTOs.Status;

/**
 * Created by acer on 29/1/18.
 */

public class ValidateOtpResponseObj {

    Status status;
    String id;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
