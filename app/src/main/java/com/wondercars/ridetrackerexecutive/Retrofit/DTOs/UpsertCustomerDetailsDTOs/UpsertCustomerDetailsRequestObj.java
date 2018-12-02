package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertCustomerDetailsDTOs;

/**
 * Created by acer on 17/3/18.
 */

public class UpsertCustomerDetailsRequestObj {
    private String booking_id;
    private String name;
    private String enquiryNumber;
    private String mobileNumber;
    private String emailID;
    private String admin_uid;
    private String uid;

    public String getBookingId() {
        return booking_id;
    }

    public void setBookingId(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnquiryNumber() {
        return enquiryNumber;
    }

    public void setEnquiryNumber(String enquiryNumber) {
        this.enquiryNumber = enquiryNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getAdminUid() {
        return admin_uid;
    }

    public void setAdminUid(String admin_uid) {
        this.admin_uid = admin_uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
