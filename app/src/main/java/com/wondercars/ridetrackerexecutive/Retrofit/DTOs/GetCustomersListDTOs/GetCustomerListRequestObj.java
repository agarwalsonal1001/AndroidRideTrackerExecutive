package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCustomersListDTOs;

/**
 * Created by acer on 17/3/18.
 */

public class GetCustomerListRequestObj {

    String booking_id;
    String admin_uid;
    String uid;

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
