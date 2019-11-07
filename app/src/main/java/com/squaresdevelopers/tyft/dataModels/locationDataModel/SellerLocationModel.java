package com.squaresdevelopers.tyft.dataModels.locationDataModel;

public class SellerLocationModel {

    public String strID,date,startTime,endTime,strLatitude,strLongitude;

    public SellerLocationModel(String strID,String date, String startTime, String endTime, String strLatitude, String strLongitude) {
        this.strID = strID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.strLatitude = strLatitude;
        this.strLongitude = strLongitude;
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
}
