package com.wondercars.executiveridetracker.Retrofit.DTOs.ValidateOtpDTOs;

/**
 * Created by acer on 29/1/18.
 */

public class ValidateOtpRequestObj {

    String id;
    String otp;
    String isTestDrive;
    String admin_uid;

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }

    public String getIsTestDrive() {
        return isTestDrive;
    }

    public void setIsTestDrive(String isTestDrive) {
        this.isTestDrive = isTestDrive;
    }


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
