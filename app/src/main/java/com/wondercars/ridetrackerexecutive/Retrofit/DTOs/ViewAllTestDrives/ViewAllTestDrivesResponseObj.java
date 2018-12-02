package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.ViewAllTestDrives;




import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.Status;

import java.util.ArrayList;

/**
 * Created by acer on 29/1/18.
 */

public class ViewAllTestDrivesResponseObj {

    Status status;

    ArrayList<RidesDetails> testDrives;

    public ArrayList<RidesDetails> getTestDrives() {
        return testDrives;
    }

    public void setTestDrives(ArrayList<RidesDetails> testDrives) {
        this.testDrives = testDrives;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
