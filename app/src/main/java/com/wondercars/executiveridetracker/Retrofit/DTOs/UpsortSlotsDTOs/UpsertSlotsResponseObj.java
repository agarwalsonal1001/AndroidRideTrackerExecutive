package com.wondercars.executiveridetracker.Retrofit.DTOs.UpsortSlotsDTOs;

import com.wondercars.executiveridetracker.Retrofit.DTOs.Status;

/**
 * Created by acer on 22/1/18.
 */

public class UpsertSlotsResponseObj {

   Status status;
   String id;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}