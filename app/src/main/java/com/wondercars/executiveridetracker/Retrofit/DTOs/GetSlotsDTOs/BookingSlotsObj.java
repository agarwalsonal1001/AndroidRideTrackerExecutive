package com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs;

/**
 * Created by acer on 21/1/18.
 */

public class BookingSlotsObj {

    String carId;
    String uid;
    String admin_uid;
    String fromTime;
    String toTime;
    String bookingDate;
    String activeFlg;
    boolean available = true;
    boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    public String getActiveFlg() {
        return activeFlg;
    }

    public void setActiveFlg(String activeFlg) {
        this.activeFlg = activeFlg;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
