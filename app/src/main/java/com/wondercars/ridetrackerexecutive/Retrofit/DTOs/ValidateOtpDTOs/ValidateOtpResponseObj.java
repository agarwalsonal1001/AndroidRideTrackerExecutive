package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.ValidateOtpDTOs;

import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.Status;

/**
 * Created by acer on 29/1/18.
 */

public class ValidateOtpResponseObj {

    Status status;
    boolean isValid;
    String id;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
