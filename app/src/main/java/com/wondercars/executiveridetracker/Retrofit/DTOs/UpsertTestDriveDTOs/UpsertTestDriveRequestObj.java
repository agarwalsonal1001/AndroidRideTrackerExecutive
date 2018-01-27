package com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertTestDriveDTOs;

/**
 * Created by acer on 27/1/18.
 */

public class UpsertTestDriveRequestObj {

    String carId;
    String uid;
    String admin_uid;
    String customer_enquiry_no;
    String customer_mobile;
    String customer_name;
    String license_image_url;
    String distance_travelled;
    String time_travelled;
    String id;
    String ride_type;

    public String getRide_type() {
        return ride_type;
    }

    public void setRide_type(String ride_type) {
        this.ride_type = ride_type;
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

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }

    public String getCustomer_enquiry_no() {
        return customer_enquiry_no;
    }

    public void setCustomer_enquiry_no(String customer_enquiry_no) {
        this.customer_enquiry_no = customer_enquiry_no;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getLicense_image_url() {
        return license_image_url;
    }

    public void setLicense_image_url(String license_image_url) {
        this.license_image_url = license_image_url;
    }

    public String getDistance_travelled() {
        return distance_travelled;
    }

    public void setDistance_travelled(String distance_travelled) {
        this.distance_travelled = distance_travelled;
    }

    public String getTime_travelled() {
        return time_travelled;
    }

    public void setTime_travelled(String time_travelled) {
        this.time_travelled = time_travelled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
