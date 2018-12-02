package com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetSlotsDTOs;

import java.util.ArrayList;

/**
 * Created by acer on 28/1/18.
 */

public class CarsDetailObj {

    ArrayList<CarDetails> cars;

    public ArrayList<CarDetails> getCars() {
        return cars;
    }

    public void setCars(ArrayList<CarDetails> cars) {
        this.cars = cars;
    }

   public class CarDetails {

        String carId;
        String variantId;
        String mode;
        String fuelType;
        String registrationNumber;
        String admin_uid;
        String variantName;
        String carModelName;

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getVariantId() {
            return variantId;
        }

        public void setVariantId(String variantId) {
            this.variantId = variantId;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getFuelType() {
            return fuelType;
        }

        public void setFuelType(String fuelType) {
            this.fuelType = fuelType;
        }

        public String getRegistrationNumber() {
            return registrationNumber;
        }

        public void setRegistrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
        }

        public String getAdmin_uid() {
            return admin_uid;
        }

        public void setAdmin_uid(String admin_uid) {
            this.admin_uid = admin_uid;
        }

        public String getVariantName() {
            return variantName;
        }

        public void setVariantName(String variantName) {
            this.variantName = variantName;
        }

        public String getCarModelName() {
            return carModelName;
        }

        public void setCarModelName(String carModelName) {
            this.carModelName = carModelName;
        }
    }
}

