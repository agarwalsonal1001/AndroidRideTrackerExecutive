package com.wondercars.executiveridetracker.Utils;

import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsertTestDriveDTOs.UpsertTestDriveRequestObj;
import java.util.Map;
import java.io.File;

/**
 * Created by user on 1/31/2018.
 */

public class UploadImage{


    public static void upload(final File imageToUpload, final UpsertTestDriveRequestObj upsertTestDriveRequestObj){
        new Thread(new Runnable() {
            public void run() {
                String license_image_url=uploadImageWithCloudinary(imageToUpload);
                if(license_image_url!=null && !license_image_url.trim().isEmpty()) {
                    Log.i("Image Upload Path",license_image_url);
                    //update url to database

                }
            }
        }).start();
        System.out.println("Image is being uploaded "+imageToUpload.getAbsolutePath());
    }

    private static String getJSON(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    private static String uploadImageWithCloudinary(File imageToUpload){
        String url=null;
        try{
            Cloudinary cloudinary=getCloudinary();
            Map uploadResult = cloudinary.uploader().upload(imageToUpload, ObjectUtils.emptyMap());
            Object urlObject=uploadResult.get("url");
            url=urlObject.toString();
            System.out.println(url);
        }catch (Exception e){
            System.out.println("Image upload failed");
            e.printStackTrace();
        }
        return url;
    }



    private static Cloudinary getCloudinary(){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dy2cci0z9",
                "api_key", "296831987373375",
                "api_secret", "uN4cwxnsbhLDUcyIl7FpG-TgJDE"));
        return cloudinary;
    }

    public static void main(String args[]){
        File toUpload = new File("/home/acer/Desktop/Screenshot from 2018-01-07 14-47-35.png");
        UpsertTestDriveRequestObj upsertTestDriveRequestObj=new UpsertTestDriveRequestObj();
        upsertTestDriveRequestObj.setCarId("567899d5-6cf8-4620-a82c-65d1239c043f");
        upsertTestDriveRequestObj.setAdmin_uid("6f33355e-e27c-4389-bc55-d1e0506ce035");
        upload(toUpload,upsertTestDriveRequestObj);
    }



}

