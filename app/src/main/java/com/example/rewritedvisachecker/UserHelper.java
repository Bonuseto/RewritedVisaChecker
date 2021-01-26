package com.example.rewritedvisachecker;

public class UserHelper {

    String appNum, appNumFak, type, year, status;

    public UserHelper() {
    }

    public UserHelper(String appNum, String appNumFak, String type, String year, String status) {
        this.appNum = appNum;
        this.appNumFak = appNumFak;
        this.type = type;
        this.year = year;
        this.status = status;
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
