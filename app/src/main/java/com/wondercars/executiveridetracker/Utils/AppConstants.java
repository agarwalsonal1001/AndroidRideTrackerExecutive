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
        String SOMETHING_WENT_WRONG="Something went wrong...";
    }

    public interface ResponseObjectType {

        String VERIANT_DETAILS= "VERIANT_DETAILS";
        String CAR_MODELS ="CAR_MODELS";
        String CAR_REGISTRATION_NUMBER = "CAR_REGISTRATION_NUMBER";
        String GET_ALL_SRM = "GET_ALL_SRM";
        String  GET_DESIGNATION_LIST = "GET_DESIGNATION_LIST";
        String TYPES_OF_RIDES = "TYPES_OF_RIDES";
    }
}
