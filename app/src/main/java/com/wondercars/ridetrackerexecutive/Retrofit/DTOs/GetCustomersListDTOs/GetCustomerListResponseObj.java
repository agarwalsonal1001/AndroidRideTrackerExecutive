package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCustomersListDTOs;

import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.Status;

import java.util.List;

/**
 * Created by acer on 17/3/18.
 */

public class GetCustomerListResponseObj {

    Status status;
    private List<Customer> customers; // = new ArrayList<Customer>();

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
