package com.wondercars.executiveridetracker.Utils;

/**
 * Created by umer on 8/11/17.
 */

public interface APIConstants {

    String baseurl = "https://ridetracker-login.herokuapp.com";

    interface RetrofitMethodConstants{

        String LOGIN_API ="/login";
        String ADMIN_LOGIN_API ="/adminlogin";
        String CREATE_EXECUTIVES_API="/upsertUser";
        String GET_EXECUTIVES= "/getExecutives";
        String GET_VARIANTS="/getVariants";
        String CREATE_CAR_API = "/createCar";
        String GET_CARS_API ="/getCars";
        String GET_CAR_MODELS_API = "/getCarModels";
        String GET_All_SRM_API = "/getAllSRMs";
        String GET_SLOTS_API = "/getSlots";
        String UPSERT_SLOTS_API = "/upsertSlot";
        String UPSERT_RIDE_API ="/upsertRide";
    }


    interface RetrofitConstants{

        int SUCCESS =1;
        int FAILURE = 0;
    }
}


