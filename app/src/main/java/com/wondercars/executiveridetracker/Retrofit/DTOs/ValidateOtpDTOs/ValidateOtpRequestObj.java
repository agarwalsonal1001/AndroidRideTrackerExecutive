package com.wondercars.executiveridetracker.Retrofit.DTOs.ValidateOtpDTOs;

/**
 * Created by acer on 29/1/18.
 */

public class ValidateOtpRequestObj {

    String id;
    String otp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
