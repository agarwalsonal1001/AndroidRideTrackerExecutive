package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetAvailableSlots;

/**
 * Created by acer on 28/1/18.
 */

public class GetAvailableSlotsRequestObj {

    String carId;
    String uid;
    String bookingDate;
    String admin_uid;

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
