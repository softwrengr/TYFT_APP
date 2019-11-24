package com.squaresdevelopers.tyft.dataModels.tyftUserDataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableUserModel {

    public String strID,
            date,
            startTime,
            endTime,
            strLatitude,
            strLongitude,
            strImageOne,
            strImageTwo,
            strName;


    public String getStrID() {
        return strID;
    }

    public void setStrID(String strID) {
        this.strID = strID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStrLatitude() {
        return strLatitude;
    }

    public void setStrLatitude(String strLatitude) {
        this.strLatitude = strLatitude;
    }

    public String getStrLongitude() {
        return strLongitude;
    }

    public void setStrLongitude(String strLongitude) {
        this.strLongitude = strLongitude;
    }

    public String getStrImageOne() {
        return strImageOne;
    }

    public void setStrImageOne(String strImageOne) {
        this.strImageOne = strImageOne;
    }

    public String getStrImageTwo() {
        return strImageTwo;
    }

    public void setStrImageTwo(String strImageTwo) {
        this.strImageTwo = strImageTwo;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }
}
