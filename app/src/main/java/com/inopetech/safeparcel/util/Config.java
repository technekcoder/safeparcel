package com.inopetech.safeparcel.util;

import android.content.ContentProvider;
import android.content.Context;

import com.inopetech.safeparcel.model.registerinfo;

public class Config {
    public static String FETCH_UPDATE;
    //sending order
    public static String TR_JSON_CODE = "JSON_TRANSACTIONS";
    //CLIENT SIGNUP
    public static String SU_EMAIL = "EMAIL";
    public static String SU_PASSWORD = "PASSWORD";
    public static String SU_USER_NAME = "USER_NAME";
    public static String SU_PHONE_NUMBER = "PHONE_NUMBER";
    public static String SU_BUSINESS_NAME = "BUSINESS_NAME";
    public static String SU_BUSINESS_LOCATION = "BUSINESS_LOCATION";
    public static registerinfo loggedInUser;
    private static Config config = null;

    private Config() {
    }

    public static Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public void refreshInstance(Context ctx) {
        FETCH_UPDATE = "https://drive.google.com/file/d/1vzaHkTnYElYJO4zyQaL8YswmgHgi9p8N/view?usp=sharing/posorders-V-1.1.apk";
    }
}
