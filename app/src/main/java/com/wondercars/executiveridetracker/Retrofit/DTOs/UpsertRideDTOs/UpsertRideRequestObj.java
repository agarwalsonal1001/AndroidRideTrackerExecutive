package com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertRideDTOs;

/**
 * Created by acer on 26/1/18.
 */

public class UpsertRideRequestObj {
    private String carId;
    private String uid;
    private String admin_uid;
    private String ride_type;
    private String customer_id;
    private String start_lat;
    private String start_long;
    private String end_lat;
    private String end_long;
    private String id;
    private String vehicle_type;
    private String vehicle_number;
    private String expected_duration_of_travel;

    private String ride_completed_flg;

    public String getRide_completed_flg() {
        return ride_completed_flg;
    }

    public void setRide_completed_flg(String ride_completed_flg) {
        this.ride_completed_flg = ride_completed_flg;
    }
    public String getExpected_duration_of_travel() {
        return expected_duration_of_travel;
    }

    public void setExpected_duration_of_travel(String expected_duration_of_travel) {
        this.expected_duration_of_travel = expected_duration_of_travel;
    }

    //Not to send in APIs
    private String name;
    private String enquiryNumber;
    private String mobileNumber;
    private String emailID;

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

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

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
