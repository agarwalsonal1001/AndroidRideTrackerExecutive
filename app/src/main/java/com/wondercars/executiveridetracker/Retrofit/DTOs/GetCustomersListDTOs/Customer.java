package com.wondercars.executiveridetracker.Retrofit.DTOs.GetCustomersListDTOs;

/**
 * Created by acer on 17/3/18.
 */

public class Customer {

    private String name;
    private String mobileNumber;
    private String emailID;
    private String enquiryNumber;
    private String uid;
    private String admin_uid;
    private String customer_id;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEnquiryNumber() {
        return enquiryNumber;
    }

    public void setEnquiryNumber(String enquiryNumber) {
        this.enquiryNumber = enquiryNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAdminUid() {
        return admin_uid;
    }

    public void setAdminUid(String admin_uid) {
        this.admin_uid = admin_uid;
    }
}
