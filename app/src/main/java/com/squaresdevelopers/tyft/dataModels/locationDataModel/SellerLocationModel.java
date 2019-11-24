package com.squaresdevelopers.tyft.dataModels.locationDataModel;

public class SellerLocationModel {

    public String strID,date,startTime,endTime,strLatitude,strLongitude,strImageOne,strImageTwo,strName;

    public SellerLocationModel(String strID, String date, String startTime, String endTime, String strLatitude, String strLongitude, String strImageOne, String strImageTwo, String strName) {
        this.strID = strID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.strLatitude = strLatitude;
        this.strLongitude = strLongitude;
        this.strImageOne = strImageOne;
        this.strImageTwo = strImageTwo;
        this.strName = strName;
    }

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
