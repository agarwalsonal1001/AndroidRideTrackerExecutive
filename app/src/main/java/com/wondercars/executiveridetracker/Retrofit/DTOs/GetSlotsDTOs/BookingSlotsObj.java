package com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs;

import com.wondercars.executiveridetracker.Retrofit.DTOs.GetCarsDTOs.CarDetailObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetExecutivesDTOs.ExecutivesDetailsObj;

import java.io.Serializable;

/**
 * Created by acer on 21/1/18.
 */

public class BookingSlotsObj implements Serializable {

    String id;
    String carId;
    String uid;
    String admin_uid;
    String fromTime;
    String toTime;
    String bookingDate;
    String activeFlg;
    boolean available = true;
    boolean selected = false;
    CarsDetailObj car;
    ExecutivesDetailsObj rideTrackerUser;

    public ExecutivesDetailsObj getRideTrackerUser() {
        return rideTrackerUser;
    }

    public void setRideTrackerUser(ExecutivesDetailsObj rideTrackerUser) {
        this.rideTrackerUser = rideTrackerUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CarsDetailObj getCar() {
        return car;
    }

    public void setCar(CarsDetailObj car) {
        this.car = car;
    }

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
