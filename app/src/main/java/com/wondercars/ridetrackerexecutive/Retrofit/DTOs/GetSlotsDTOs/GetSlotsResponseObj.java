package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetSlotsDTOs;

import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.Status;

import java.util.ArrayList;

/**
 * Created by acer on 21/1/18.
 */

public class GetSlotsResponseObj {

  Status status;
  ArrayList<BookingSlotsObj> bookingSlots;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<BookingSlotsObj> getBookingSlots() {
        return bookingSlots;
    }

    public void setBookingSlots(ArrayList<BookingSlotsObj> bookingSlots) {
        this.bookingSlots = bookingSlots;
    }
}
