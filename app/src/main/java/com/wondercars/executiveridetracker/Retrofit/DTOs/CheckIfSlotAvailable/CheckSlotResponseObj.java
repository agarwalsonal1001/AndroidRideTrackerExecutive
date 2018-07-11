package com.wondercars.executiveridetracker.Retrofit.DTOs.CheckIfSlotAvailable;

import com.wondercars.executiveridetracker.Retrofit.DTOs.Status;

/**
 * Created by acer on 9/6/18.
 */

public class CheckSlotResponseObj {


    Status status;
    boolean isSlotAvailable;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isSlotAvailable() {
        return isSlotAvailable;
    }

    public void setSlotAvailable(boolean slotAvailable) {
        isSlotAvailable = slotAvailable;
    }
}
