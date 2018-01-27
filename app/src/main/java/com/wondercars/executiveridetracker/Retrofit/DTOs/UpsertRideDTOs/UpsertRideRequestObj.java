package com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertRideDTOs;

/**
 * Created by acer on 26/1/18.
 */

public class UpsertRideRequestObj {
    private String carId;
    private String uid;
    private String admin_uid;
    private String ride_type;
    private String customer_enquiry_no;
    private String customer_mobile;
    private String customer_name;
    private String start_lat;
    private String start_long;
    private String end_lat;
    private String end_long;
    private String id;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
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

    public String getRideType() {
        return ride_type;
    }

    public void setRideType(String ride_type) {
        this.ride_type = ride_type;
    }

    public String getCustomerEnquiryNo() {
        return customer_enquiry_no;
    }

    public void setCustomerEnquiryNo(String customer_enquiry_no) {
        this.customer_enquiry_no = customer_enquiry_no;
    }

    public String getCustomerMobile() {
        return customer_mobile;
    }

    public void setCustomerMobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getCustomerName() {
        return customer_name;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getStartLat() {
        return start_lat;
    }

    public void setStartLat(String start_lat) {
        this.start_lat = start_lat;
    }

    public String getStartLong() {
        return start_long;
    }

    public void setStartLong(String start_long) {
        this.start_long = start_long;
    }

    public String getEndLat() {
        return end_lat;
    }

    public void setEndLat(String end_lat) {
        this.end_lat = end_lat;
    }

    public String getEndLong() {
        return end_long;
    }

    public void setEndLong(String end_long) {
        this.end_long = end_long;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
