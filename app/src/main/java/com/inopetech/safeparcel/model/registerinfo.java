package com.inopetech.safeparcel.model;

public class registerinfo {
    public String firstname;
    public String surname;
    public String phonenumber;
    public String phonenumber2;
    public String emailaddress;
    public String physicaladdress;
    public String defaultusage;
    public String county;
    public String country;

    public registerinfo() {

    }

    public registerinfo(String firstname, String surname, String phonenumber, String phonenumber2, String emailaddress, String physicaladdress, String defaultusage, String county, String country) {
        this.firstname = firstname;
        this.surname = surname;
        this.phonenumber = phonenumber;
        this.phonenumber2 = phonenumber2;
        this.emailaddress = emailaddress;
        this.physicaladdress = physicaladdress;
        this.defaultusage = defaultusage;
        this.county = county;
        this.country = country;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber2() {
        return phonenumber2;
    }

    public void setPhonenumber2(String phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPhysicaladdress() {
        return physicaladdress;
    }

    public void setPhysicaladdress(String physicaladdress) {
        this.physicaladdress = physicaladdress;
    }

    public String getDefaultusage() {
        return defaultusage;
    }

    public void setDefaultusage(String defaultusage) {
        this.defaultusage = defaultusage;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "registerinfo{" +
                "firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", phonenumber2='" + phonenumber2 + '\'' +
                ", emailaddress='" + emailaddress + '\'' +
                ", physicaladdress='" + physicaladdress + '\'' +
                ", defaultusage='" + defaultusage + '\'' +
                ", county='" + county + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
