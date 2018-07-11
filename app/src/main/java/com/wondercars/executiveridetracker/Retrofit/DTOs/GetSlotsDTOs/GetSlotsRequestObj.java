package com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs;

/**
 * Created by acer on 21/1/18.
 */

public class GetSlotsRequestObj {

    String carId;
    String bookingDate;
    String admin_uid;
    String uid;
    private String ride_completed_flg;

    public String getRide_completed_flg() {
        return ride_completed_flg;
    }

    public void setRide_completed_flg(String ride_completed_flg) {
        this.ride_completed_flg = ride_completed_flg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }
}
