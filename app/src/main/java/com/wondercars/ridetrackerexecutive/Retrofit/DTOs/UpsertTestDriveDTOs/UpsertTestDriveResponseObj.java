package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertTestDriveDTOs;

import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.Status;

/**
 * Created by acer on 27/1/18.
 */

public class UpsertTestDriveResponseObj {

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
