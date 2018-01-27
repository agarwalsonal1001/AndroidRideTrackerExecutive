package com.wondercars.executiveridetracker.Retrofit.DTOs.UpsortSlotsDTOs;

/**
 * Created by acer on 22/1/18.
 */

public class UpsertSlotsRequestObj {

    private String carId;
    private String uid;
    private String fromTime;
    private String toTime;
    private String bookingDate;
    private String admin_uid;


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

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getAdminUid() {
        return admin_uid;
    }

    public void setAdminUid(String adminUid) {
        this.admin_uid = adminUid;
    }
}
