package com.wondercars.executiveridetracker.Utils;

/**
 * Created by umer on 11/11/17.
 */

public interface AppConstants {

    String PLEASE_ENTER_USERNAME = "Please enter username";
    String PLEASE_ENTER_EMAIL = "Please enter email id";
    String PLEASE_ENTER_PASSWORD = "Please enter password";
    String LOGIN_SUCCESSFULLY = "You have login successfully";
    String EXECUTIVES_CREATED_SUCCESSFULLY = "Created Executive successfully";

    public interface ToastMessages {
        String SOMETHING_WENT_WRONG = "Connection error. Please try again...";
    }

    public interface ResponseObjectType {

        String VERIANT_DETAILS = "VERIANT_DETAILS";
        String CAR_MODELS = "CAR_MODELS";
        String CAR_REGISTRATION_NUMBER = "CAR_REGISTRATION_NUMBER";
        String GET_ALL_SRM = "GET_ALL_SRM";
        String GET_DESIGNATION_LIST = "GET_DESIGNATION_LIST";
        String TYPES_OF_RIDES = "TYPES_OF_RIDES";
        String TYPE_OF_VEHICLE = "TYPE_OF_VEHICLE";
        String AVAILABLE_SLOTS_FROM_TIME = "AVAILABLE_SLOTS_FROM_TIME";
        String AVAILABLE_SLOTS_TO_TIME = "AVAILABLE_SLOTS_TO_TIME";
    }

    interface ColorStrings {

        String colorPrimary = "#3F51B5";
        String colorPrimaryDark = "#303F9F";
        String colorAccent = "#FF4081";
        String lightestBlue = "#C0DDFF";
        String lighterBlue = "#9CC7F2";
        String lightBlue = "#72A9D6";
        String darkBlue = "#4079BF";
        String blackColor = "#000000";
        String white = "#ffffff";
        String layout_background = "#80dedede";
        String light_gray = "#D3D3D3";
    }
}
