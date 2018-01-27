package com.wondercars.executiveridetracker.Retrofit.DTOs.OtpDTOs;

/**
 * Created by acer on 27/1/18.
 */

public class SendOtpRequestObj {

    String customerMonileNumber;
    String id;

    public String getCustomerMonileNumber() {
        return customerMonileNumber;
    }

    public void setCustomerMonileNumber(String customerMonileNumber) {
        this.customerMonileNumber = customerMonileNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
