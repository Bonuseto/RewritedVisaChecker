package com.example.rewritedvisachecker;

import model.VisaStatus;

public class UserHelper {

    String appNum, appNumFak, type, year, visaStatus, uniqueID;
    // private VisaStatus visaStatus;

    public UserHelper() {
    }

    public UserHelper(String appNum, String appNumFak, String type, String year, String status, String uniqueID) {
        this.appNum = appNum;
        this.appNumFak = appNumFak;
        this.type = type;
        this.year = year;
        this.visaStatus = status;
        this.uniqueID = uniqueID;
    }

    public String getAppNum() {
        return appNum;
    }

    public void setAppNum(String appNum) {
        this.appNum = appNum;
    }

    public String getAppNumFak() {
        return appNumFak;
    }

    public void setAppNumFak(String appNumFak) {
        this.appNumFak = appNumFak;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStatus() {
        return visaStatus;
    }

    public void setStatus(String status) {
        this.visaStatus = status;
    }

    public String getUniqueID() { return uniqueID; }

    public void setUniqueID(String uniqueID) { this.uniqueID = uniqueID; }
}
