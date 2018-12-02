package com.wondercars.ridetrackerexecutive.Retrofit.DTOs;

/**
 * Created by acer on 17/11/17.
 */

public class Status {

        private int statusCode;

    public int getStatusCode() { return this.statusCode; }

    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    private String errorDescription;

    public String getErrorDescription() { return this.errorDescription; }

    public void setErrorDescription(String errorDescription) { this.errorDescription = errorDescription; }

    private String status;

    public String getStatus() { return this.status; }

    public void setStatus(String status) { this.status = status; }

}
