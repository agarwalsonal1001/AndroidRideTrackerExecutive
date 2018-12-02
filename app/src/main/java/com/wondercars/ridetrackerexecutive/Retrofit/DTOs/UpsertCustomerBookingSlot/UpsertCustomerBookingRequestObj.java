package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertCustomerBookingSlot;

/**
 * Created by acer on 27/5/18.
 */

public class UpsertCustomerBookingRequestObj {

    String customer_id;
    String ride_completed_flg;
    String booking_id;

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

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }
}
