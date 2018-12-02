package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.OtpDTOs;

/**
 * Created by acer on 27/1/18.
 */

public class SendOtpRequestObj {

   String customer_mobile;
    String id;
    String customer_email;
    String isTestDrive;

    public String getIsTestDrive() {
        return isTestDrive;
    }

    public void setIsTestDrive(String isTestDrive) {
        this.isTestDrive = isTestDrive;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomerMonileNumber() {
        return customer_mobile;
    }

    public void setCustomerMonileNumber(String customerMonileNumber) {
        this.customer_mobile = customerMonileNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
