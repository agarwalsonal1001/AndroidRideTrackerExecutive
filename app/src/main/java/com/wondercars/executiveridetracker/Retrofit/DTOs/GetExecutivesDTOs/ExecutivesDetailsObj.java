package com.wondercars.executiveridetracker.Retrofit.DTOs.GetExecutivesDTOs;

import java.io.Serializable;

/**
 * Created by acer on 18/11/17.
 */

public class ExecutivesDetailsObj implements Serializable {

    private String uid;
    private String title;
    private String designation;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String createDate;
    private String lastUpdateDate;
    private String activeFlg;
    private String admin_uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getActiveFlg() {
        return activeFlg;
    }

    public void setActiveFlg(String activeFlg) {
        this.activeFlg = activeFlg;
    }

    public String getAdminUid() {
        return admin_uid;
    }

    public void setAdminUid(String adminUid) {
        this.admin_uid = adminUid;
    }

}
