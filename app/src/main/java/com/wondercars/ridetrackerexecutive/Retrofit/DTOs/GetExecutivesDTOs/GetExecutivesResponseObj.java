package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetExecutivesDTOs;



import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.Status;

/**
 * Created by acer on 18/11/17.
 */

public class GetExecutivesResponseObj {

    Status status;
    ExecutivesDetailsObj rideTrackerUser;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ExecutivesDetailsObj getExecutivesDetailsObj() {
        return rideTrackerUser;
    }

    public void setExecutivesDetailsObj(ExecutivesDetailsObj executivesDetailsObjArrayList) {
        this.rideTrackerUser = executivesDetailsObjArrayList;
    }
}
