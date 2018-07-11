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
    String expected_duration_of_travel;
    String ongoing_test_drive_flg;
    String customer_id;
    private String ride_completed_flg;
    private String ride_start_lat;
    private String ride_start_long;
    private String ride_end_lat;
    private String ride_end_long;


    public String getStart_lat() {
        return ride_start_lat;
    }

    public void setStart_lat(String ride_start_lat) {
        this.ride_start_lat = ride_start_lat;
    }

    public String getStart_long() {
        return ride_start_long;
    }

    public void setStart_long(String ride_start_long) {
        this.ride_start_long = ride_start_long;
    }

    public String getEnd_lat() {
        return ride_end_lat;
    }

    public void setEnd_lat(String ride_end_lat) {
        this.ride_end_lat = ride_end_lat;
    }

    public String getEnd_long() {
        return ride_end_long;
    }

    public void setEnd_long(String ride_end_long) {
        this.ride_end_long = ride_end_long;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getOngoing_test_drive_flg() {
        return ongoing_test_drive_flg;
    }

    public void setOngoing_test_drive_flg(String ongoing_test_drive_flg) {
        this.ongoing_test_drive_flg = ongoing_test_drive_flg;
    }

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
