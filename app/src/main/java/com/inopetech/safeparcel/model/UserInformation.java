package com.inopetech.safeparcel.model;


import java.util.Date;

public class UserInformation {
    public String name;
    public String plot;
    public String phone;
    public String code;
    public String ownerId;
    public String email;
    public String kra;
    public String address;
    public String timestamp;


    public String business;

    //public @ServerTimestamp Date timestamp;
    public UserInformation() {

    }

    public UserInformation(String name, String plot, String phone, String business, String code, String ownerId, String email, String kra, String address, String timestamp) {
        this.name = name;
        this.plot = plot;
        this.phone = phone;
        this.code = code;
        this.ownerId = ownerId;
        this.email = email;
        this.kra = kra;
        this.address = address;
        this.timestamp = timestamp;
        this.business = business;
    }

    public String getBusiness() {
        return business;
    }

    public String getName() {
        return name;
    }

    public String getPlot() {
        return plot;
    }

    public String getPhone() {
        return phone;
    }

    public String getCode() {
        return code;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getEmail() {
        return email;
    }

    public String getKra() {
        return kra;
    }

    public String getAddress() {
        return address;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
