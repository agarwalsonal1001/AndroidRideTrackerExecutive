package com.wondercars.executiveridetracker.Retrofit.DTOs.UpsortSlotsDTOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 22/1/18.
 */

public class UpsertSlotsRequestObj implements Serializable {

    private String carId;
    private String uid;
    private String fromTime;
    private String toTime;
    private String bookingDate;
    private String admin_uid;
    private List<String> customerIDs = new ArrayList<String>();

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

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }

    public List<String> getCustomerIDs() {
        return customerIDs;
    }

    public void setCustomerIDs(List<String> customerIDs) {
        this.customerIDs = customerIDs;
    }
}
