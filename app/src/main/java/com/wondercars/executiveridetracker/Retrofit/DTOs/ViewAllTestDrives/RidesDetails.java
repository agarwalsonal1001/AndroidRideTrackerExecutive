package com.wondercars.executiveridetracker.Retrofit.DTOs.ViewAllTestDrives;


import com.wondercars.executiveridetracker.Retrofit.DTOs.GetExecutivesDTOs.ExecutivesDetailsObj;

/**
 * Created by acer on 29/1/18.
 */

public class RidesDetails {

    private String id;
    private String carId;
    private String uid;
    private String admin_uid;
    private String license_image_url;
    private String otp;
    private String distance_travelled;
    private String time_travelled;
    private String customer_enquiry_no;
    private String customer_name;
    private String customer_mobile;
    private String activeFlg;
    private String expected_duration_of_travel;
    private String customer_id;
    private String ride_completed_flg;
    private String ongoing_test_drive_flg;
    private String createDate;
    private ExecutivesDetailsObj rideTrackerUser;


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLicense_image_url() {
        return license_image_url;
    }

    public void setLicense_image_url(String license_image_url) {
        this.license_image_url = license_image_url;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
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

    public String getCustomer_enquiry_no() {
        return customer_enquiry_no;
    }

    public void setCustomer_enquiry_no(String customer_enquiry_no) {
        this.customer_enquiry_no = customer_enquiry_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getActiveFlg() {
        return activeFlg;
    }

    public void setActiveFlg(String activeFlg) {
        this.activeFlg = activeFlg;
    }

    public String getExpected_duration_of_travel() {
        return expected_duration_of_travel;
    }

    public void setExpected_duration_of_travel(String expected_duration_of_travel) {
        this.expected_duration_of_travel = expected_duration_of_travel;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getRide_completed_flg() {
        return ride_completed_flg;
    }

    public void setRide_completed_flg(String ride_completed_flg) {
        this.ride_completed_flg = ride_completed_flg;
    }

    public String getOngoing_test_drive_flg() {
        return ongoing_test_drive_flg;
    }

    public void setOngoing_test_drive_flg(String ongoing_test_drive_flg) {
        this.ongoing_test_drive_flg = ongoing_test_drive_flg;
    }

    public ExecutivesDetailsObj getRideTrackerUser() {
        return rideTrackerUser;
    }

    public void setRideTrackerUser(ExecutivesDetailsObj rideTrackerUser) {
        this.rideTrackerUser = rideTrackerUser;
    }

}
