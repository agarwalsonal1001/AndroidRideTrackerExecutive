package com.wondercars.executiveridetracker.Retrofit.DTOs.ViewAllRidesDTOs;

/**
 * Created by acer on 29/1/18.
 */

public class RidesDetails {

    private String id;
    private String createDate;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    private String customer_enquiry_no;

    public String getCustomerEnquiryNo() { return this.customer_enquiry_no; }

    public void setCustomerEnquiryNo(String customer_enquiry_no) { this.customer_enquiry_no = customer_enquiry_no; }

    private String customer_name;

    public String getCustomerName() { return this.customer_name; }

    public void setCustomerName(String customer_name) { this.customer_name = customer_name; }

    private String customer_mobile;

    public String getCustomerMobile() { return this.customer_mobile; }

    public void setCustomerMobile(String customer_mobile) { this.customer_mobile = customer_mobile; }

    private String ride_type;

    public String getRideType() { return this.ride_type; }

    public void setRideType(String ride_type) { this.ride_type = ride_type; }
}
