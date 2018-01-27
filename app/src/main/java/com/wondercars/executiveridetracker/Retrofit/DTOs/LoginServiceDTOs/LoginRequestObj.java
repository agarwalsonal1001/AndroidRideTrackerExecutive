package com.wondercars.executiveridetracker.Retrofit.DTOs.LoginServiceDTOs;

/**
 * Created by umer on 8/11/17.
 */

public class LoginRequestObj {
    String email;
      String password;

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
}
